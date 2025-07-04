/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:38:53
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/UserRoleServiceImpl.java
 * @Description: 用户和角色关联表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.system.entity.UserRole;
import com.rockblade.system.mapper.UserRoleMapper;
import com.rockblade.system.service.UserRoleService;

@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {}
