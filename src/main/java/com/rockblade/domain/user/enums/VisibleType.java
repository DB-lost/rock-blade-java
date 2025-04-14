/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-14 12:45:59
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-14 12:46:05
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/enums/VisibleType.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.enums;

public enum VisibleType {
    SHOW("1", "显示"),
    HIDE("0", "隐藏");

    private final String code;
    private final String desc;

    VisibleType(String code, String desc) {
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
