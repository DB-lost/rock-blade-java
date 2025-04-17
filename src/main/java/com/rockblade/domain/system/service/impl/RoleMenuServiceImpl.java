/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 10:10:02
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/service/impl/RoleMenuServiceImpl.java
 * @Description: 角色和菜单关联表 服务层实现。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.RoleMenu;
import com.rockblade.domain.system.service.RoleMenuService;
import com.rockblade.infrastructure.mapper.RoleMenuMapper;

import org.springframework.stereotype.Service;

@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
