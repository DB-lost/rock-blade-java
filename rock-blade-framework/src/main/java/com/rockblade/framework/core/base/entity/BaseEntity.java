/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:29:08
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/core/base/entity/BaseEntity.java
 * @Description: 基础实体类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.base.entity;

import java.time.LocalDateTime;
import java.util.Map;

import com.mybatisflex.annotation.Column;

import lombok.Data;

@Data
public class BaseEntity {

  /** 创建时间 */
  private LocalDateTime createdAt;

  /** 创建人 */
  private String createdBy;

  /** 更新时间 */
  private LocalDateTime updatedAt;

  /** 更新人 */
  private String updatedBy;

  /** 请求参数 */
  @Column(ignore = true)
  private Map<String, Object> params;
}
