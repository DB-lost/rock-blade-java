/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:43:06
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-13 17:18:16
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/service/impl/UserServiceImpl.java
 * @Description: 用户服务实现类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.service.impl;

import static com.rockblade.domain.system.entity.table.DeptTableDef.DEPT;
import static com.rockblade.domain.system.entity.table.RoleTableDef.ROLE;
import static com.rockblade.domain.system.entity.table.UserDeptTableDef.USER_DEPT;
import static com.rockblade.domain.system.entity.table.UserRoleTableDef.USER_ROLE;
import static com.rockblade.domain.system.entity.table.UserTableDef.USER;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.dto.request.EmailCodeRequest;
import com.rockblade.domain.system.dto.request.EmailLoginRequest;
import com.rockblade.domain.system.dto.request.LoginRequest;
import com.rockblade.domain.system.dto.request.RegisterRequest;
import com.rockblade.domain.system.dto.request.ResetPasswordRequest;
import com.rockblade.domain.system.dto.request.UserDetailsInfoRequest;
import com.rockblade.domain.system.dto.request.UserPageRequest;
import com.rockblade.domain.system.dto.request.UserRequest;
import com.rockblade.domain.system.dto.request.VerifyEmailCodeRequest;
import com.rockblade.domain.system.dto.response.PublicKeyResponse;
import com.rockblade.domain.system.dto.response.UserInfoResponse;
import com.rockblade.domain.system.dto.response.UserPageResponse;
import com.rockblade.domain.system.entity.User;
import com.rockblade.domain.system.entity.UserDept;
import com.rockblade.domain.system.entity.UserRole;
import com.rockblade.domain.system.enums.UserType;
import com.rockblade.domain.system.service.UserDeptService;
import com.rockblade.domain.system.service.UserRoleService;
import com.rockblade.domain.system.service.UserService;
import com.rockblade.framework.config.RockBladeConfig;
import com.rockblade.framework.config.RockBladeConfig.Gateway;
import com.rockblade.framework.core.base.entity.PageDomain;
import com.rockblade.framework.core.base.exception.ServiceException;
import com.rockblade.framework.core.constants.RedisKey;
import com.rockblade.framework.handler.EmailHandler;
import com.rockblade.framework.handler.RedisHandler;
import com.rockblade.framework.utils.MessageUtils;
import com.rockblade.framework.utils.SqlUtils;
import com.rockblade.infrastructure.mapper.UserMapper;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Autowired
  private EmailHandler emailHandler;

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private RockBladeConfig rockBladeConfig;

  @Override
  public PublicKeyResponse getPublicKey(String nonce) {
    if (StrUtil.isBlank(nonce)) {
      nonce = IdUtil.fastSimpleUUID();
    }
    Gateway gateway = rockBladeConfig.getGateway();
    // 生成RSA密钥对
    KeyPair rsa = SecureUtil.generateKeyPair(
        gateway.getRsaKeypair().getAlgorithm(), gateway.getRsaKeypair().getKeySize());
    PublicKey publicKey = rsa.getPublic();
    PrivateKey privateKey = rsa.getPrivate();

    // 将私钥Base64编码后存入Redis，用于后续解密
    String privateKeyStr = Base64.encodeStr(privateKey.getEncoded(), true, false);
    redisHandler.set(
        RedisKey.RSA_PRIVATE_KEY + nonce, privateKeyStr, RedisKey.CAPTCHA_EXPIRE_MINUTES * 60);

    return PublicKeyResponse.builder()
        .publicKey(Base64.encodeStr(publicKey.getEncoded(), true, false))
        .nonce(nonce)
        .build();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void register(RegisterRequest request) {
    // 解密密码
    String password = decryptPassword(request.getPassword(), request.getNonce(), redisHandler);
    request.setPassword(password);
    request.setNonce(null);

    // 校验邮箱是否已注册
    QueryWrapper queryWrapper = QueryWrapper.create().where(USER.EMAIL.eq(request.getEmail()));
    if (this.count(queryWrapper) > 0) {
      throw new ServiceException(MessageUtils.message("auth.email.registered"));
    }

    // 保存用户信息
    User user = User.builder()
        .email(request.getEmail())
        .password(BCrypt.hashpw(request.getPassword()))
        .username(request.getUsername())
        .phone(request.getPhone())
        .userType(UserType.USER) // 设置用户类型为user
        .build();
    this.save(user);
  }

  @Override
  public String login(LoginRequest request) {
    // 查询用户信息
    User user = this.queryChain().where(USER.USERNAME.eq(request.getUsername())).one();
    if (user == null) {
      throw new ServiceException(MessageUtils.message("auth.user.not.found"));
    }
    return login(user, request.getPassword(), request.getNonce());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void resetPassword(ResetPasswordRequest request) {
    // 查询用户信息
    QueryWrapper queryWrapper = QueryWrapper.create().where(User::getEmail).eq(request.getEmail());
    User user = this.getOne(queryWrapper);
    if (user == null) {
      throw new ServiceException(MessageUtils.message("auth.user.not.found"));
    }

    // 解密密码
    String password = decryptPassword(request.getPassword(), request.getNonce(), redisHandler);
    request.setPassword(password);
    request.setNonce(null);

    // 更新密码
    user.setPassword(BCrypt.hashpw(request.getPassword()));
    this.updateById(user);

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
        QueryWrapper queryWrapper = QueryWrapper.create().where(User::getEmail).eq(request.getEmail());
        if (this.count(queryWrapper) > 0) {
          throw new ServiceException(MessageUtils.message("auth.email.registered"));
        }
        key = RedisKey.EMAIL_REGISTER_CODE;
        break;
      case "reset":
        // 校验邮箱是否存在
        queryWrapper = QueryWrapper.create().where(User::getEmail).eq(request.getEmail());
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

  @Override
  public void verifyEmailCode(VerifyEmailCodeRequest request) {
    String key;
    switch (request.getType()) {
      case "register":
        key = RedisKey.EMAIL_REGISTER_CODE;
        break;
      case "reset":
        key = RedisKey.EMAIL_RESET_CODE;
        break;
      default:
        throw new ServiceException(MessageUtils.message("auth.business.type.not.supported"));
    }
    // 校验验证码
    String cacheCode = (String) redisHandler.get(key + request.getEmail());
    if (cacheCode == null) {
      throw new ServiceException(MessageUtils.message("auth.verification.code.expired"));
    }
    if (!cacheCode.equals(request.getCode())) {
      throw new ServiceException(MessageUtils.message("auth.verification.code.error"));
    }
    // 删除验证码缓存
    redisHandler.del(key + request.getEmail());
  }

  @Override
  public String emailLogin(EmailLoginRequest request) {
    // 查询用户信息
    User user = this.queryChain().where(USER.EMAIL.eq(request.getEmail())).one();
    if (user == null) {
      throw new ServiceException(MessageUtils.message("auth.user.not.found"));
    }
    return login(user, request.getPassword(), request.getNonce());
  }

  /**
   * @description: 登陆
   * @param {User}   user
   * @param {String} password
   * @param {String} nonce
   * @return {*}
   */
  private String login(User user, String password, String nonce) {
    // 解密密码
    password = decryptPassword(password, nonce, redisHandler);

    // 校验密码
    if (!BCrypt.checkpw(password, user.getPassword())) {
      throw new ServiceException(MessageUtils.message("auth.password.error"));
    }
    // 登录
    StpUtil.login(user.getId());
    // 返回用户信息
    UserInfoResponse userInfo = BeanUtil.toBean(user, UserInfoResponse.class);
    StpUtil.getSession().set("user", userInfo);
    return StpUtil.getTokenValue();
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
    // 获取Base64编码的私钥
    String privateKeyStr = (String) redisHandler.get(RedisKey.RSA_PRIVATE_KEY + nonce);
    if (privateKeyStr == null) {
      throw new ServiceException(MessageUtils.message("auth.private.key.expired"));
    }

    try {
      // 使用Base64编码的私钥创建RSA实例
      RSA rsa = SecureUtil.rsa(privateKeyStr, null);
      String decryptedPassword = rsa.decryptStr(password, KeyType.PrivateKey);
      if (decryptedPassword == null) {
        throw new ServiceException(MessageUtils.message("auth.password.decrypt.failed"));
      }
      return decryptedPassword;
    } finally {
      // 确保删除私钥缓存
      redisHandler.del(RedisKey.RSA_PRIVATE_KEY + nonce);
    }
  }

  @Override
  public Page<UserPageResponse> page(UserPageRequest request) {
    // 分页查询
    PageDomain pageDomain = SqlUtils.getInstance().getPageDomain();
    // 转换为详细信息响应对象
    return this.mapper.paginateWithRelationsAs(
        Page.of(pageDomain.getPage(), pageDomain.getPageSize()),
        QueryWrapper.create()
            .select(USER.ALL_COLUMNS)
            .from(USER)
            .leftJoin(USER_ROLE)
            .on(USER_ROLE.USER_ID.eq(USER.ID))
            .leftJoin(USER_DEPT)
            .on(USER_DEPT.USER_ID.eq(USER.ID))
            .and(USER.STATUS.eq(request.getStatus()))
            .and(USER.NICKNAME.likeLeft(request.getNickname()))
            .and(USER.EMAIL.eq(request.getEmail()))
            .and(USER_ROLE.ROLE_ID.eq(request.getRoleId()))
            .and(USER_DEPT.DEPT_ID.eq(request.getDeptId())),
        UserPageResponse.class,
        deptInfo -> deptInfo
            .field(UserPageResponse::getDeptInfo)
            .queryWrapper(
                UserPageResponse -> QueryWrapper.create()
                    .select(DEPT.ID, DEPT.NAME)
                    .from(USER_DEPT)
                    .leftJoin(DEPT)
                    .on(DEPT.ID.eq(USER_DEPT.DEPT_ID))
                    .where(USER_DEPT.USER_ID.eq(UserPageResponse.getId()))),
        roleInfo -> roleInfo
            .field(UserPageResponse::getRoleInfo)
            .queryWrapper(
                UserPageResponse -> QueryWrapper.create()
                    .select(ROLE.ID, ROLE.ROLE_NAME)
                    .from(USER_ROLE)
                    .leftJoin(ROLE)
                    .on(ROLE.ID.eq(USER_ROLE.ROLE_ID))
                    .where(USER_ROLE.USER_ID.eq(UserPageResponse.getId()))));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void add(UserRequest request) {
    // 校验用户名是否存在
    // long count =
    // this.count(QueryWrapper.create().where(USER.USERNAME.eq(request.getUsername())));
    // if (count > 0) {
    // throw new ServiceException(MessageUtils.message("user.username.exists"));
    // }

    // // 校验手机号是否存在
    // if (StrUtil.isNotBlank(request.getPhone())) {
    // count =
    // this.count(QueryWrapper.create().where(USER.PHONE.eq(request.getPhone())));
    // if (count > 0) {
    // throw new ServiceException(MessageUtils.message("user.phone.exists"));
    // }
    // }

    // // 校验邮箱是否存在
    // if (StrUtil.isNotBlank(request.getEmail())) {
    // count =
    // this.count(QueryWrapper.create().where(USER.EMAIL.eq(request.getEmail())));
    // if (count > 0) {
    // throw new ServiceException(MessageUtils.message("user.email.exists"));
    // }
    // }

    User user = new User();
    BeanUtils.copyProperties(request, user);
    user.setId(IdUtil.fastSimpleUUID());

    // 如果密码为空，设置默认密码
    if (StrUtil.isBlank(user.getPassword())) {
      user.setPassword(BCrypt.hashpw("123456"));
    }

    this.save(user);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void edit(UserRequest request) {
    // // 校验手机号是否存在
    // if (StrUtil.isNotBlank(request.getPhone())) {
    // user = this.getOne(QueryWrapper.create()
    // .where(USER.PHONE.eq(request.getPhone()))
    // .and(USER.ID.ne(request.getId())));
    // if (user != null) {
    // throw new ServiceException(MessageUtils.message("user.phone.exists"));
    // }
    // }

    // // 校验邮箱是否存在
    // if (StrUtil.isNotBlank(request.getEmail())) {
    // user = this.getOne(QueryWrapper.create()
    // .where(USER.EMAIL.eq(request.getEmail()))
    // .and(USER.ID.ne(request.getId())));
    // if (user != null) {
    // throw new ServiceException(MessageUtils.message("user.email.exists"));
    // }
    // }
    User user = BeanUtil.toBean(request, User.class);

    // 部门不为空
    if (CollectionUtil.isNotEmpty(request.getDeptIds())) {
      // 删除原有部门
      SpringUtil.getBean(UserDeptService.class)
          .remove(QueryWrapper.create().where(USER_DEPT.USER_ID.eq(user.getId())));
      // 添加新部门
      request
          .getDeptIds()
          .forEach(
              deptId -> {
                UserDept userDept = new UserDept();
                userDept.setUserId(user.getId());
                userDept.setDeptId(deptId);
                SpringUtil.getBean(UserDeptService.class).save(userDept);
              });
    }

    // 角色不为空
    if (CollectionUtil.isNotEmpty(request.getRoleIds())) {
      // 删除原有角色
      SpringUtil.getBean(UserRoleService.class)
          .remove(QueryWrapper.create().where(USER_ROLE.USER_ID.eq(user.getId())));
      // 添加新角色
      request
          .getRoleIds()
          .forEach(
              roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(roleId);
                SpringUtil.getBean(UserRoleService.class).save(userRole);
              });
    }

    this.updateById(user);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void remove(String[] ids) {
    // 批量逻辑删除
    List<User> users = new ArrayList<>();
    Arrays.asList(ids)
        .forEach(
            id -> {
              User user = new User();
              user.setId(id);
              users.add(user);
            });
    this.updateBatch(users);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignDept(String userId, String deptId, Boolean isPrimary) {
    // 校验用户是否存在
    User user = this.getById(userId);
    if (user == null) {
      throw new ServiceException(MessageUtils.message("user.not.exists"));
    }

    // 如果是设置主部门，先将其他部门设置为非主部门
    if (Boolean.TRUE.equals(isPrimary)) {
      SpringUtil.getBean(UserDeptService.class).resetPrimaryDept(userId);
    }

    // 保存用户部门关联
    UserDept userDept = new UserDept();
    userDept.setUserId(userId);
    userDept.setDeptId(deptId);
    userDept.setIsPrimary(isPrimary);
    SpringUtil.getBean(UserDeptService.class).save(userDept);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeDept(String userId, String deptId) {
    SpringUtil.getBean(UserDeptService.class)
        .remove(
            QueryWrapper.create()
                .where(UserDept::getUserId)
                .eq(userId)
                .and(UserDept::getDeptId)
                .eq(deptId));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assignRole(String userId, String roleId) {
    // 校验用户是否存在
    User user = this.getById(userId);
    if (user == null) {
      throw new ServiceException(MessageUtils.message("user.not.exists"));
    }

    // 保存用户角色关联
    UserRole userRole = new UserRole();
    userRole.setUserId(userId);
    userRole.setRoleId(roleId);
    SpringUtil.getBean(UserRoleService.class).save(userRole);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void removeRole(String userId, String roleId) {
    SpringUtil.getBean(UserRoleService.class)
        .remove(
            QueryWrapper.create()
                .where(UserRole::getUserId)
                .eq(userId)
                .and(UserRole::getRoleId)
                .eq(roleId));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateUserDetails(UserDetailsInfoRequest request) {
    User user = this.getById(StpUtil.getLoginIdAsString());
    user.setAvatar(request.getAvatar())
        .setEmail(request.getEmail())
        .setNickname(request.getNickname())
        .setPhone(request.getPhone());
    this.updateById(user);

    // 更新用户信息到Session
    UserInfoResponse userInfo = BeanUtil.toBean(user, UserInfoResponse.class);
    StpUtil.getSession().set("user", userInfo);
  }
}
