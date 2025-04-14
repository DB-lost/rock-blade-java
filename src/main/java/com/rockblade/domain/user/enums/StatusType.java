/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-14 12:46:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-14 12:46:15
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/enums/StatusType.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.enums;

public enum StatusType {
    NORMAL("1", "正常"),
    DISABLED("0", "停用");

    private final String code;
    private final String desc;

    StatusType(String code, String desc) {
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
