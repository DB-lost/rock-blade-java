/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:15:26
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/UserRoleServiceImpl.java
 * @Description: 用户和角色关联表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.system.entity.UserRole;
import com.rockblade.system.service.UserRoleService;
import com.rockblade.system.mapper.UserRoleMapper;

@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
                implements UserRoleService {
}
