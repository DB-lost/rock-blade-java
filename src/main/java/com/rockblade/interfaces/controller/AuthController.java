/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:04:57
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-13 20:26:28
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/AuthController.java
 * @Description: 认证接口
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rockblade.domain.user.dto.request.EmailCodeRequest;
import com.rockblade.domain.user.dto.request.EmailLoginRequest;
import com.rockblade.domain.user.dto.request.GetPublicKeyRequest;
import com.rockblade.domain.user.dto.request.LoginRequest;
import com.rockblade.domain.user.dto.request.RegisterRequest;
import com.rockblade.domain.user.dto.request.ResetPasswordRequest;
import com.rockblade.domain.user.dto.request.VerifyEmailCodeRequest;
import com.rockblade.domain.user.dto.response.PublicKeyResponse;
import com.rockblade.domain.user.service.UserService;
import com.rockblade.framework.core.base.entity.R;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "认证接口")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/getPublicKey")
    @Operation(summary = "获取公钥")
    public R<PublicKeyResponse> getPublicKey(@RequestBody GetPublicKeyRequest request) {
        return R.ok(userService.getPublicKey(request));
    }

    @PostMapping("/sendEmailCode")
    @Operation(summary = "发送邮箱验证码")
    public R<Void> sendEmailCode(@Validated @RequestBody EmailCodeRequest request) {
        userService.sendEmailCode(request);
        return R.ok();
    }

    @PostMapping("/verifyEmailCode")
    @Operation(summary = "校验邮箱验证码")
    public R<Void> verifyEmailCode(@Validated @RequestBody VerifyEmailCodeRequest request) {
        userService.verifyEmailCode(request);
        return R.ok();
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public R<Void> register(@Validated @RequestBody RegisterRequest request) {
        userService.register(request);
        return R.ok();
    }

    @PostMapping("/resetPassword")
    @Operation(summary = "重置密码")
    public R<Void> resetPassword(@Validated @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return R.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public R<String> login(@Validated @RequestBody LoginRequest request) {
        return R.ok(userService.login(request));
    }

    @GetMapping("/logout")
    @Operation(summary = "退出登录")
    public R<Void> logout() {
        StpUtil.logout();
        return R.ok();
    }

    @PostMapping("/emailLogin")
    @Operation(summary = "邮箱登录")
    public R<String> login(@Validated @RequestBody EmailLoginRequest request) {
        return R.ok(userService.emailLogin(request));
    }

}
