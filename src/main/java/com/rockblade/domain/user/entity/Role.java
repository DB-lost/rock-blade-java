/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-15 16:21:43
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/entity/Role.java
 * @Description: 角色信息表 实体类。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.entity;

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
@Table(value = "sys_role", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)
public class Role extends BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色标识符
     */
    private String roleKey;

    /**
     * 角色状态
     */
    private String status;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 备注
     */
    private String remark;

}
