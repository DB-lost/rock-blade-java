/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:38:50
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/UserLoginLogServiceImpl.java
 * @Description: 用户登录日志表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.system.entity.UserLoginLog;
import com.rockblade.system.mapper.UserLoginLogMapper;
import com.rockblade.system.service.UserLoginLogService;

@Service("userLoginLogService")
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
    implements UserLoginLogService {}
