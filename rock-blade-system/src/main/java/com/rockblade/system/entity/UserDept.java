/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-06-24 13:00:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:49:08
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/entity/UserDept.java
 * @Description: 用户和部门关联表 实体类。
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
@Table(
    value = "sys_user_dept",
    onInsert = BaseInsertListener.class,
    onUpdate = BaseUpdateListener.class,
    mapperGenerateEnable = false)
public class UserDept extends BaseEntity implements Serializable {

  /** 用户ID */
  @Id private String userId;

  /** 部门ID */
  @Id private String deptId;

  /** 是否为主部门 */
  private Boolean isPrimary;
}
