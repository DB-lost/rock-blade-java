/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-14 13:00:14
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/MenuRequest.java
 * @Description: 菜单请求DTO
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.request;

import com.rockblade.domain.user.enums.MenuType;
import com.rockblade.domain.user.enums.StatusType;
import com.rockblade.domain.user.enums.VisibleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Data;

@Data
public class MenuRequest {
    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
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
    private Map<String, Object> query;

    /**
     * 激活图标
     */
    private String activeIcon;

    /**
     * 当前激活的菜单路径
     */
    private String activePath;

    /**
     * 是否固定标签页
     */
    private Boolean affixTab;

    /**
     * 固定标签页的顺序
     */
    private Integer affixTabOrder;

    /**
     * 需要特定的角色标识才可以访问
     */
    private String[] authority;

    /**
     * 徽标
     */
    private String badge;

    /**
     * 徽标类型
     */
    private String badgeType;

    /**
     * 徽标颜色
     */
    private String badgeVariants;

    /**
     * 当前路由的子级在菜单中不展现
     */
    private Boolean hideChildrenInMenu;

    /**
     * 当前路由在面包屑中不展现
     */
    private Boolean hideInBreadcrumb;

    /**
     * 当前路由在菜单中不展现
     */
    private Boolean hideInMenu;

    /**
     * 当前路由在标签页不展现
     */
    private Boolean hideInTab;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * iframe 地址
     */
    private String iframeSrc;

    /**
     * 忽略权限，直接可以访问
     */
    private Boolean ignoreAccess;

    /**
     * 是否为外链
     */
    private Boolean isFrame;

    /**
     * 是否缓存
     */
    private Boolean isCache;

    /**
     * 外链-跳转路径
     */
    private String link;

    /**
     * 路由是否已经加载过
     */
    private Boolean loaded;

    /**
     * 标签页最大打开数量
     */
    private Integer maxOpenTab;

    /**
     * 菜单可以看到，但是访问会被重定向到403
     */
    private Boolean menuForbidden;

    /**
     * 不使用基础布局
     */
    private Boolean noBasicLayout;

    /**
     * 在新窗口打开
     */
    private Boolean openInNewWindow;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    private MenuType menuType;

    /**
     * 显示状态（1显示 0隐藏）
     */
    @NotNull(message = "显示状态不能为空")
    private VisibleType visible;

    /**
     * 菜单状态（1正常 0停用）
     */
    @NotNull(message = "菜单状态不能为空")
    private StatusType status;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 备注
     */
    private String remark;
}
