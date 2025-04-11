package com.rockblade.domain.user.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 角色和菜单关联表 实体类。
 *
 * @author 
 * @since 2025-04-11
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(value = "sys_role_menu", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)

public class RoleMenu extends BaseEntity implements Serializable {

    /**
     * 角色ID
     */
    @Id
    private Long roleId;

    /**
     * 菜单ID
     */
    @Id
    private Long menuId;

}
