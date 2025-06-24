/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:59:53
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/RoleMenuServiceImpl.java
 * @Description: 角色和菜单关联表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.system.entity.RoleMenu;
import com.rockblade.system.service.RoleMenuService;
import com.rockblade.system.mapper.RoleMenuMapper;

@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
                implements RoleMenuService {
}
