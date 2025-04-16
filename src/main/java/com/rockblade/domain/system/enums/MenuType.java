/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-14 12:45:46
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 10:09:23
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/enums/MenuType.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.system.enums;

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
