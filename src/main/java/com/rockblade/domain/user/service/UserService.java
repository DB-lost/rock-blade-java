package com.rockblade.domain.user.service;

import com.rockblade.domain.user.dto.request.EmailCodeRequest;
import com.rockblade.domain.user.dto.request.GetPublicKeyRequest;
import com.rockblade.domain.user.dto.request.LoginRequest;
import com.rockblade.domain.user.dto.request.RegisterRequest;
import com.rockblade.domain.user.dto.request.ResetPasswordRequest;
import com.rockblade.domain.user.dto.response.LoginResponse;
import com.rockblade.domain.user.dto.response.PublicKeyResponse;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 获取公钥
     * 
     * @param request 请求
     * @return {@link PublicKeyResponse}
     */
    PublicKeyResponse getPublicKey(GetPublicKeyRequest request);

    /**
     * 注册
     * 
     * @param request 请求
     */
    void register(RegisterRequest request);

    /**
     * 登录
     * 
     * @param request 请求
     * @return {@link LoginResponse}
     */
    LoginResponse login(LoginRequest request);

    /**
     * 重置密码
     * 
     * @param request 请求
     */
    void resetPassword(ResetPasswordRequest request);

    /**
     * 发送邮箱验证码
     * 
     * @param request 请求
     */
    void sendEmailCode(EmailCodeRequest request);
}
