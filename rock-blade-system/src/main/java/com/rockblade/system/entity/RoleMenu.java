/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 10:10:55
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/entity/RoleMenu.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.entity;

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
    value = "sys_role_menu",
    onInsert = BaseInsertListener.class,
    onUpdate = BaseUpdateListener.class,
    mapperGenerateEnable = false)
public class RoleMenu extends BaseEntity implements Serializable {

  /** 角色ID */
  @Id private String roleId;

  /** 菜单ID */
  @Id private String menuId;
}
