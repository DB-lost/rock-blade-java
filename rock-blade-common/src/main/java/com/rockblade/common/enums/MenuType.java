/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-14 12:45:46
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:04:36
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/enums/MenuType.java
 * @Description: 菜单类型枚举
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.enums;

public enum MenuType {
  CATALOG("M", "目录"),
  MENU("C", "菜单"),
  BUTTON("F", "按钮"),
  EMBEDDED("E", "嵌入式"),
  LINK("L", "外链");

  private final String code;
  private final String desc;

  MenuType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public String getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
