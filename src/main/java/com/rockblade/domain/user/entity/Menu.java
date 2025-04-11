package com.rockblade.domain.user.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 菜单权限表 实体类。
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
@Table(value = "sys_menu", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)

public class Menu extends BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由参数
     */
    private String queryParam;

    /**
     * 是否为外链
     */
    private Boolean isFrame;

    /**
     * 是否缓存
     */
    private Boolean isCache;

    /**
     * 菜单类型
     */
    private String menuType;

    /**
     * 显示状态
     */
    private String visible;

    /**
     * 菜单状态
     */
    private String status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 备注
     */
    private String remark;

}
