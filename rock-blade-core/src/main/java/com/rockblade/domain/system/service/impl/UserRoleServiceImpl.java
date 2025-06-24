/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-24 22:52:28
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/service/impl/UserRoleServiceImpl.java
 * @Description: 用户和角色关联表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.UserRole;
import com.rockblade.domain.system.service.UserRoleService;
import com.rockblade.infrastructure.system.mapper.UserRoleMapper;

@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {}
