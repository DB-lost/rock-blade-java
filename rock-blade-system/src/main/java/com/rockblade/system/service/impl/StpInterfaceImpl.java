/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-12 11:36:57
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:52:16
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/StpInterfaceImpl.java
 * @Description: 自定义权限加载接口实现类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service.impl;

import static com.rockblade.domain.system.entity.table.RolePermissionTableDef.ROLE_PERMISSION;
import static com.rockblade.domain.system.entity.table.RoleTableDef.ROLE;
import static com.rockblade.domain.system.entity.table.UserRoleTableDef.USER_ROLE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rockblade.domain.system.entity.Role;
import com.rockblade.domain.system.entity.RolePermission;
import com.rockblade.domain.system.entity.User;
import com.rockblade.domain.system.service.RolePermissionService;
import com.rockblade.domain.system.service.RoleService;
import com.rockblade.domain.system.service.UserService;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.extra.spring.SpringUtil;

@Component // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

  /** 返回一个账号所拥有的权限码集合 */
  @Override
  public List<String> getPermissionList(Object loginId, String loginType) {
    RoleService roleService = SpringUtil.getBean(RoleService.class);
    List<Role> roleList = roleService
        .queryChain()
        .from(ROLE)
        .leftJoin(USER_ROLE)
        .on(USER_ROLE.ROLE_ID.eq(ROLE.ID))
        .where(USER_ROLE.USER_ID.eq(loginId))
        .list();
    List<String> list;
    if (!roleList.isEmpty()) {
      List<RolePermission> rolePermissionsList = SpringUtil.getBean(RolePermissionService.class)
          .queryChain()
          .where(
              ROLE_PERMISSION.ROLE_ID.in(roleList.stream().map(item -> item.getId()).toList()))
          .list();
      if (!rolePermissionsList.isEmpty()) {
        list = rolePermissionsList.stream().map(item -> item.getPermission()).distinct().toList();
      } else {
        list = new ArrayList<String>();
      }
    } else {
      list = new ArrayList<String>();
    }
    return list;
  }

  /** 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验) */
  @Override
  public List<String> getRoleList(Object loginId, String loginType) {
    // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
    List<String> list = new ArrayList<String>();
    User user = SpringUtil.getBean(UserService.class).getById((String) loginId);
    list.add(user.getUserType().toString());
    return list;
  }
}
