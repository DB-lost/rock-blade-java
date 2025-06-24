/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-12 09:34:55
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:28:18
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/config/RbacConfig.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.rockblade.domain.system.entity.Role;
import com.rockblade.domain.system.service.RoleService;
import com.rockblade.framework.config.properties.RbacProperties;
import com.rockblade.framework.config.properties.RbacProperties.RoleConfig;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties(RbacProperties.class)
public class RbacConfig {

  @Autowired
  private RbacProperties rbacProperties;

  @Autowired
  private RoleService roleService;

  @PostConstruct
  public void init() {
    if ("simple".equals(rbacProperties.getMode())) {
      // 初始化基础角色
      initBaseRoles();
    }
  }

  private void initBaseRoles() {
    // 检查并创建基础角色
    for (RoleConfig roleConfig : rbacProperties.getSimple().getRoles()) {
      Role role = roleService.getRoleByKey(roleConfig.getKey());
      if (role == null) {
        role = new Role();
        role.setRoleKey(roleConfig.getKey());
        role.setRoleName(roleConfig.getName());
        role.setRemark(roleConfig.getDescription());
        role.setStatus("0"); // 0-正常
        roleService.save(role);
      }
    }
  }
}
