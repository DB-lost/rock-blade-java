/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 22:21:37
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:05:15
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/response/MenuResponse.java
 * @Description: 菜单响应DTO
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.response;

import java.util.List;
import java.util.Map;

import com.rockblade.common.enums.BadgeType;
import com.rockblade.common.enums.BadgeVariants;
import com.rockblade.common.enums.MenuType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "菜单响应DTO")
public class MenuResponse {
  /** 后端权限标识 */
  @Schema(description = "后端权限标识")
  private String authCode;

  /** 子级 */
  @Schema(description = "子级")
  private List<MenuResponse> children;

  /** 组件 */
  @Schema(description = "组件")
  private String component;

  /** 菜单ID */
  @Schema(description = "菜单ID")
  private String id;

  /** 菜单元数据 */
  @Schema(description = "菜单元数据")
  private MenuMeta meta;

  /** 菜单名称 */
  @Schema(description = "菜单名称")
  private String name;

  /** 路由路径 */
  @Schema(description = "路由路径")
  private String path;

  /** 父级ID */
  @Schema(description = "父级ID")
  private String pid;

  /** 重定向 */
  @Schema(description = "重定向")
  private String redirect;

  /** 菜单类型 */
  @Schema(description = "菜单类型")
  private MenuType type;

  /** 状态 */
  @Schema(description = "状态")
  private String status;

  @Data
  @Schema(description = "菜单元数据")
  public static class MenuMeta {
    /** 激活时显示的图标 */
    @Schema(description = "激活时显示的图标")
    private String activeIcon;

    /** 作为路由时，需要激活的菜单的Path */
    @Schema(description = "作为路由时，需要激活的菜单的Path")
    private String activePath;

    /** 固定在标签栏 */
    @Schema(description = "固定在标签栏")
    private Boolean affixTab;

    /** 在标签栏固定的顺序 */
    @Schema(description = "在标签栏固定的顺序")
    private Integer affixTabOrder;

    /** 徽标内容(当徽标类型为normal时有效) */
    @Schema(description = "徽标内容(当徽标类型为normal时有效)")
    private String badge;

    /** 徽标类型 */
    @Schema(description = "徽标类型")
    private BadgeType badgeType;

    /** 徽标颜色 */
    @Schema(description = "徽标颜色")
    private BadgeVariants badgeVariants;

    /** 在菜单中隐藏下级 */
    @Schema(description = "在菜单中隐藏下级")
    private Boolean hideChildrenInMenu;

    /** 在面包屑中隐藏 */
    @Schema(description = "在面包屑中隐藏")
    private Boolean hideInBreadcrumb;

    /** 在菜单中隐藏 */
    @Schema(description = "在菜单中隐藏")
    private Boolean hideInMenu;

    /** 在标签栏中隐藏 */
    @Schema(description = "在标签栏中隐藏")
    private Boolean hideInTab;

    /** 菜单图标 */
    @Schema(description = "菜单图标")
    private String icon;

    /** 内嵌Iframe的URL */
    @Schema(description = "内嵌Iframe的URL")
    private String iframeSrc;

    /** 是否缓存页面 */
    @Schema(description = "是否缓存页面")
    private Boolean keepAlive;

    /** 外链页面的URL */
    @Schema(description = "外链页面的URL")
    private String link;

    /** 同一个路由最大打开的标签数 */
    @Schema(description = "同一个路由最大打开的标签数")
    private Integer maxNumOfOpenTab;

    /** 无需基础布局 */
    @Schema(description = "无需基础布局")
    private Boolean noBasicLayout;

    /** 是否在新窗口打开 */
    @Schema(description = "是否在新窗口打开")
    private Boolean openInNewWindow;

    /** 菜单排序 */
    @Schema(description = "菜单排序")
    private Integer order;

    /** 额外的路由参数 */
    @Schema(description = "额外的路由参数")
    private Map<String, Object> query;

    /** 菜单标题 */
    @Schema(description = "菜单标题")
    private String title;
  }
}
