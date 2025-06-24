/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-12 09:34:36
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:13:23
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/config/properties/RbacProperties.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.config.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "rock-blade.rbac")
public class RbacProperties {

  // 权限模式
  private String mode = "simple";

  // 简单模式配置
  private SimpleMode simple = new SimpleMode();

  @Data
  public static class SimpleMode {
    // 默认角色Key
    private String defaultRoleKey = "user";

    // 基础角色配置
    private List<RoleConfig> roles = new ArrayList<>();

    // 功能特性开关
    private Features features = new Features();
  }

  @Data
  public static class RoleConfig {
    private String key;
    private String name;
    private String description;
  }

  @Data
  public static class Features {
    private boolean menuPermission = false;
    private boolean dataPermission = false;
    private boolean dynamicPermission = false;

    public boolean isMenuPermission() {
      return menuPermission;
    }

    public boolean isDataPermission() {
      return dataPermission;
    }

    public boolean isDynamicPermission() {
      return dynamicPermission;
    }

    public void setMenuPermission(boolean menuPermission) {
      this.menuPermission = menuPermission;
    }

    public void setDataPermission(boolean dataPermission) {
      this.dataPermission = dataPermission;
    }

    public void setDynamicPermission(boolean dynamicPermission) {
      this.dynamicPermission = dynamicPermission;
    }

    // Kebab case setters
    public void setMenuPermission(String value) {
      this.menuPermission = Boolean.parseBoolean(value);
    }

    public void setDataPermission(String value) {
      this.dataPermission = Boolean.parseBoolean(value);
    }

    public void setDynamicPermission(String value) {
      this.dynamicPermission = Boolean.parseBoolean(value);
    }

    // For menu-permission
    public void setMenu_permission(boolean value) {
      this.menuPermission = value;
    }

    // For data-permission
    public void setData_permission(boolean value) {
      this.dataPermission = value;
    }

    // For dynamic-permission
    public void setDynamic_permission(boolean value) {
      this.dynamicPermission = value;
    }
  }
}
