package com.rockblade.domain.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.dto.request.EmailCodeRequest;
import com.rockblade.domain.user.dto.request.GetPublicKeyRequest;
import com.rockblade.domain.user.dto.request.LoginRequest;
import com.rockblade.domain.user.dto.request.RegisterRequest;
import com.rockblade.domain.user.dto.request.ResetPasswordRequest;
import com.rockblade.domain.user.dto.response.LoginResponse;
import com.rockblade.domain.user.dto.response.PublicKeyResponse;
import com.rockblade.domain.user.dto.response.UserInfoResponse;
import com.rockblade.domain.user.entity.User;
import com.rockblade.domain.user.service.UserService;
import com.rockblade.framework.core.base.exception.ServiceException;
import com.rockblade.framework.core.constants.RedisKey;
import com.rockblade.framework.handler.EmailHandler;
import com.rockblade.framework.handler.RedisHandler;
import com.rockblade.framework.utils.MessageUtils;
import com.rockblade.infrastructure.mapper.UserMapper;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

import static com.rockblade.domain.user.entity.table.UserTableDef.USER;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private EmailHandler emailHandler;

    @Autowired
    private RedisHandler redisHandler;

    @Override
    public PublicKeyResponse getPublicKey(GetPublicKeyRequest request) {
        String nonce = request.getNonce();
        if (nonce == null) {
            nonce = IdUtil.fastSimpleUUID();
        }

        // 生成RSA密钥对
        RSA rsa = new RSA();
        String publicKey = rsa.getPublicKeyBase64();
        String privateKey = rsa.getPrivateKeyBase64();

        // 将私钥存入Redis，用于后续解密
        redisHandler.set(RedisKey.RSA_PRIVATE_KEY + nonce, privateKey, RedisKey.CAPTCHA_EXPIRE_MINUTES * 60);

        return PublicKeyResponse.builder()
                .publicKey(publicKey)
                .nonce(nonce)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterRequest request) {
        // 校验验证码
        String cacheCode = (String) redisHandler.get(RedisKey.EMAIL_REGISTER_CODE + request.getEmail());
        if (cacheCode == null) {
            throw new ServiceException(MessageUtils.message("auth.verification.code.expired"));
        }
        if (!cacheCode.equals(request.getCode())) {
            throw new ServiceException(MessageUtils.message("auth.verification.code.error"));
        }

        // 解密密码
        String password = decryptPassword(request.getPassword(), request.getNonce(), redisHandler);
        request.setPassword(password);
        request.setNonce(null);

        // 校验邮箱是否已注册
        QueryWrapper queryWrapper = QueryWrapper.create().where(USER.EMAIL.eq(request));
        if (this.count(queryWrapper) > 0) {
            throw new ServiceException(MessageUtils.message("auth.email.registered"));
        }

        // 保存用户信息
        User user = User.builder()
                .email(request.getEmail())
                .password(BCrypt.hashpw(request.getPassword()))
                .username(request.getUsername())
                .build();
        this.save(user);

        // 删除验证码缓存
        redisHandler.del(RedisKey.EMAIL_REGISTER_CODE + request.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        // 查询用户信息
        User user = this.queryChain().where(USER.USERNAME.eq(request.getUsername())).one();
        if (user == null) {
            throw new ServiceException(MessageUtils.message("auth.user.not.found"));
        }

        // 解密密码
        String password = decryptPassword(request.getPassword(), request.getNonce(), redisHandler);
        request.setPassword(password);
        request.setNonce(null);

        // 校验密码
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new ServiceException(MessageUtils.message("auth.password.error"));
        }

        // 登录
        StpUtil.login(user.getId());

        // 返回用户信息
        UserInfoResponse userInfo = new UserInfoResponse();
        BeanUtil.copyProperties(user, userInfo);
        userInfo.setUserId(user.getId());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setTokenValue(StpUtil.getTokenValue());
        loginResponse.setUserInfo(userInfo);
        return loginResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(ResetPasswordRequest request) {
        // 校验验证码
        String cacheCode = (String) redisHandler.get(RedisKey.EMAIL_RESET_CODE + request.getEmail());
        if (cacheCode == null) {
            throw new ServiceException(MessageUtils.message("auth.verification.code.expired"));
        }
        if (!cacheCode.equals(request.getCode())) {
            throw new ServiceException(MessageUtils.message("auth.verification.code.error"));
        }

        // 查询用户信息
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where(User::getEmail).eq(request.getEmail());
        User user = this.getOne(queryWrapper);
        if (user == null) {
            throw new ServiceException(MessageUtils.message("auth.user.not.found"));
        }

        // 解密密码
        String password = decryptPassword(request.getNewPassword(), request.getNonce(), redisHandler);
        request.setNewPassword(password);
        request.setNonce(null);

        // 更新密码
        user.setPassword(BCrypt.hashpw(request.getNewPassword()));
        this.updateById(user);

        // 删除验证码缓存
        redisHandler.del(RedisKey.EMAIL_RESET_CODE + request.getEmail());

        // 退出登录
        StpUtil.logout(user.getId());
    }

    @Override
    public void sendEmailCode(EmailCodeRequest request) {
        // 生成6位验证码
        String code = RandomUtil.randomNumbers(6);
        String key;

        // 根据业务类型设置不同的缓存key
        switch (request.getType()) {
            case "register":
                // 校验邮箱是否已注册
                QueryWrapper queryWrapper = QueryWrapper.create()
                        .where(User::getEmail).eq(request.getEmail());
                if (this.count(queryWrapper) > 0) {
                    throw new ServiceException(MessageUtils.message("auth.email.registered"));
                }
                key = RedisKey.EMAIL_REGISTER_CODE;
                break;
            case "reset":
                // 校验邮箱是否存在
                queryWrapper = QueryWrapper.create()
                        .where(User::getEmail).eq(request.getEmail());
                if (this.count(queryWrapper) == 0) {
                    throw new ServiceException(MessageUtils.message("auth.email.not.registered"));
                }
                key = RedisKey.EMAIL_RESET_CODE;
                break;
            default:
                throw new ServiceException(MessageUtils.message("auth.business.type.not.supported"));
        }

        // 发送验证码邮件
        emailHandler.sendEmailCode(request.getEmail(), code, RedisKey.CAPTCHA_EXPIRE_MINUTES);

        // 将验证码存入Redis
        redisHandler.set(key + request.getEmail(), code, RedisKey.CAPTCHA_EXPIRE_MINUTES * 60);
    }

    /**
     * 解密密码并删除私钥缓存
     * 
     * @param password     加密的密码
     * @param nonce        随机字符串
     * @param redisHandler Redis工具类
     * @return 解密后的密码
     */
    private String decryptPassword(String password, String nonce, RedisHandler redisHandler) {
        // 获取私钥
        String privateKey = (String) redisHandler.get(RedisKey.RSA_PRIVATE_KEY + nonce);
        if (privateKey == null) {
            throw new ServiceException(MessageUtils.message("auth.private.key.expired"));
        }

        // 解密密码
        RSA rsa = new RSA(privateKey, null);
        String decryptedPassword = rsa.decryptStr(password, KeyType.PrivateKey);
        if (decryptedPassword == null) {
            throw new ServiceException(MessageUtils.message("auth.password.decrypt.failed"));
        }

        // 删除私钥缓存
        redisHandler.del(RedisKey.RSA_PRIVATE_KEY + nonce);

        return decryptedPassword;
    }
}
