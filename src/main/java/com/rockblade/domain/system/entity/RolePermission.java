/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-12 18:53:47
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-12 18:54:00
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/entity/RolePermission.java
 * @Description: 角色权限码
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(value = "sys_role_permission", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)
public class RolePermission extends BaseEntity implements Serializable {

    /**
     * 角色ID
     */
    @Id
    private String roleId;

    /**
     * 权限码
     */
    private String permission;

}
