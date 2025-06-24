/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-06-24 13:00:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:47:32
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/entity/ExportTask.java
 * @Description: 导出任务表 实体类。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.entity;

import java.io.Serializable;

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
@Table(value = "sys_export_task", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)
public class ExportTask extends BaseEntity implements Serializable {

  /** 任务ID */
  @Id
  private String id;

  /** 任务名称 */
  private String name;

  /** 导出类型 */
  private String exportType;

  /** 状态 */
  private String status;

  /** 导出文件路径 */
  private String filePath;

  /** 导出进度 */
  private Integer progress;

  /** 错误信息 */
  private String errorMessage;

  private String userId;
}
