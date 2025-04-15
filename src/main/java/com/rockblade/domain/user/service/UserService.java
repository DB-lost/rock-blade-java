/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:43:06
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-13 20:26:44
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/UserService.java
 * @Description: 用户服务接口
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service;

import com.mybatisflex.core.service.IService;
import com.rockblade.domain.user.dto.request.EmailCodeRequest;
import com.rockblade.domain.user.dto.request.EmailLoginRequest;
import com.rockblade.domain.user.dto.request.GetPublicKeyRequest;
import com.rockblade.domain.user.dto.request.LoginRequest;
import com.rockblade.domain.user.dto.request.RegisterRequest;
import com.rockblade.domain.user.dto.request.ResetPasswordRequest;
import com.rockblade.domain.user.dto.request.VerifyEmailCodeRequest;
import com.rockblade.domain.user.dto.response.PublicKeyResponse;
import com.rockblade.domain.user.entity.User;

public interface UserService extends IService<User> {

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
     * @return {@link LoginRespStringonse}
     */
    String login(LoginRequest request);

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

    /**
     * 校验邮箱验证码
     * 
     * @param request 请求
     */
    void verifyEmailCode(VerifyEmailCodeRequest request);

    /**
     * 邮箱登录
     * 
     * @param request 请求
     * @return {@link String}
     */
    String emailLogin(EmailLoginRequest request);
}
