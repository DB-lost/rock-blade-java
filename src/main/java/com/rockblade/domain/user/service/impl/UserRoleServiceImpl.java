/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 09:33:11
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/impl/UserRoleServiceImpl.java
 * @Description: 用户和角色关联表 服务层实现。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.UserRole;
import com.rockblade.infrastructure.mapper.UserRoleMapper;
import com.rockblade.domain.user.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
