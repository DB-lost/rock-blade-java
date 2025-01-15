/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-15 21:17:37
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/base/entity/BaseEntity.java
 * @Description: 基础实体类
 * 
 * Copyright (c) 2025 by DB, All Rights Reserved. 
 */
package com.rockblade.framework.core.base.entity;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class BaseEntity {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateUserId;

    /**
     * 请求参数
     */
    @Column(ignore = true)
    private Map<String, Object> params;
}
