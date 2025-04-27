/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 09:35:56
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/UserLoginLogController.java
 * @Description: 用户登录日志接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.interfaces.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "用户登录日志表接口")
@RequestMapping("/userLoginLog")
public class UserLoginLogController {}
