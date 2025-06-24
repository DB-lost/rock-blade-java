/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:04:57
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:27:49
 * @FilePath: /rock-blade-java/rock-blade-api/src/main/java/com/rockblade/api/controller/system/AuthController.java
 * @Description: 认证接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.api.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rockblade.common.dto.system.request.EmailCodeRequest;
import com.rockblade.common.dto.system.request.EmailLoginRequest;
import com.rockblade.common.dto.system.request.LoginRequest;
import com.rockblade.common.dto.system.request.RegisterRequest;
import com.rockblade.common.dto.system.request.ResetPasswordRequest;
import com.rockblade.common.dto.system.request.VerifyEmailCodeRequest;
import com.rockblade.common.dto.system.response.PublicKeyResponse;
import com.rockblade.common.utils.IpUtils;
import com.rockblade.common.utils.ServletUtils;
import com.rockblade.framework.core.base.entity.R;
import com.rockblade.system.service.UserService;

import cn.dev33.satoken.stp.StpUtil;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "认证接口")
public class AuthController {

  @Autowired private UserService userService;
  @Autowired private MeterRegistry meterRegistry;

  @GetMapping("/getPublicKey")
  @Operation(summary = "获取公钥")
  public R<PublicKeyResponse> getPublicKey(
      @Parameter(description = "随机字符串") @RequestParam(name = "nonce", required = false)
          String nonce) {
    return R.ok(userService.getPublicKey(nonce));
  }

  @PostMapping("/sendEmailCode")
  @Operation(summary = "发送邮箱验证码")
  public R<Void> sendEmailCode(@Validated @RequestBody EmailCodeRequest request) {
    userService.sendEmailCode(request);
    meterRegistry.counter("auth.email.code.sent").increment();
    return R.ok();
  }

  @PostMapping("/verifyEmailCode")
  @Operation(summary = "校验邮箱验证码")
  public R<Void> verifyEmailCode(@Validated @RequestBody VerifyEmailCodeRequest request) {
    userService.verifyEmailCode(request);
    meterRegistry.counter("auth.email.code.verify", "result", "success").increment();
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
    meterRegistry.counter("security.password.reset").increment();
    return R.ok();
  }

  @PostMapping("/login")
  @Operation(summary = "用户登录")
  public R<String> login(@Validated @RequestBody LoginRequest request) {
    meterRegistry.counter("auth.login.total").increment();
    try {
      String token = userService.login(request);
      meterRegistry.counter("auth.login.success").increment();
      updateDeviceMetrics(request);
      return R.ok(token);
    } catch (Exception e) {
      meterRegistry.counter("auth.login.failure").increment();
      throw e;
    }
  }

  @GetMapping("/logout")
  @Operation(summary = "退出登录")
  public R<Void> logout() {
    StpUtil.logout();
    return R.ok();
  }

  /** 记录设备和浏览器指标 */
  private void updateDeviceMetrics(LoginRequest request) {
    // 从User-Agent获取设备类型和浏览器信息
    String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
    String deviceType = getDeviceType(userAgent);
    String browser = getBrowserType(userAgent);
    String location = IpUtils.getIpLocation(IpUtils.getIpAddr());

    meterRegistry.counter("client.device", "type", deviceType).increment();
    meterRegistry.counter("client.browser", "type", browser).increment();
    meterRegistry.counter("client.location", "location", location).increment();
  }

  private String getDeviceType(String userAgent) {
    if (userAgent == null) return "unknown";
    userAgent = userAgent.toLowerCase();
    if (userAgent.contains("mobile")
        || userAgent.contains("android")
        || userAgent.contains("iphone")) {
      return "mobile";
    } else if (userAgent.contains("tablet") || userAgent.contains("ipad")) {
      return "tablet";
    }
    return "desktop";
  }

  private String getBrowserType(String userAgent) {
    if (userAgent == null) return "unknown";
    userAgent = userAgent.toLowerCase();
    if (userAgent.contains("chrome")) {
      return "chrome";
    } else if (userAgent.contains("firefox")) {
      return "firefox";
    } else if (userAgent.contains("safari")) {
      return "safari";
    } else if (userAgent.contains("edge")) {
      return "edge";
    }
    return "other";
  }

  @PostMapping("/emailLogin")
  @Operation(summary = "邮箱登录")
  public R<String> login(@Validated @RequestBody EmailLoginRequest request) {
    return R.ok(userService.emailLogin(request));
  }
}
