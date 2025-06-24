/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-21 11:06:51
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:47:07
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/entity/AlertHistory.java
 * @Description: 告警历史记录表 实体类。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(
    value = "sys_alert_history",
    onInsert = BaseInsertListener.class,
    onUpdate = BaseUpdateListener.class,
    mapperGenerateEnable = false)
public class AlertHistory extends BaseEntity implements Serializable {

  /** 历史记录ID */
  @Id private String id;

  /** 告警名称 */
  private String alertName;

  /** 监控指标 */
  private String metric;

  /** 触发值 */
  private Double value;

  /** 严重程度 */
  private String severity;

  /** 触发时间 */
  private LocalDateTime fireTime;

  /** 恢复时间 */
  private LocalDateTime resolveTime;

  /** 状态 */
  private String status;
}
