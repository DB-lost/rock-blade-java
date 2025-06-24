/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-06-24 13:00:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:47:32
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/entity/Menu.java
 * @Description: 菜单表 实体类。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.entity;

import java.io.Serializable;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.system.enums.BadgeType;
import com.rockblade.system.enums.BadgeVariants;
import com.rockblade.system.enums.MenuType;
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
@Table(value = "sys_menu", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)
public class Menu extends BaseEntity implements Serializable {

  /** 菜单ID */
  @Id
  private String id;

  /** 菜单名称 */
  private String name;

  /** 父级ID */
  private String pid;

  /** 路由路径 */
  private String path;

  /** 组件路径 */
  private String component;

  /** 重定向地址 */
  private String redirect;

  /** 菜单类型(catalog目录 menu菜单 embedded内嵌 link链接 button按钮) */
  private MenuType type;

  /** 后端权限标识 */
  private String authCode;

  /** 激活时显示的图标 */
  private String activeIcon;

  /** 作为路由时，需要激活的菜单的Path */
  private String activePath;

  /** 固定在标签栏 */
  private Boolean affixTab;

  /** 在标签栏固定的顺序 */
  private Integer affixTabOrder;

  /** 徽标内容(当徽标类型为normal时有效) */
  private String badge;

  /** 徽标类型(dot,normal) */
  private BadgeType badgeType;

  /** 徽标变体颜色(default,destructive,primary,success,warning) */
  private BadgeVariants badgeVariants;

  /** 在菜单中隐藏下级 */
  private Boolean hideChildrenInMenu;

  /** 在面包屑中隐藏 */
  private Boolean hideInBreadcrumb;

  /** 在菜单中隐藏 */
  private Boolean hideInMenu;

  /** 在标签栏中隐藏 */
  private Boolean hideInTab;

  /** 菜单图标 */
  private String icon;

  /** 内嵌Iframe的URL */
  private String iframeSrc;

  /** 是否缓存页面 */
  private Boolean keepAlive;

  /** 外链页面的URL */
  private String link;

  /** 同一个路由最大打开的标签数 */
  private Integer maxNumOfOpenTab;

  /** 无需基础布局 */
  private Boolean noBasicLayout;

  /** 是否在新窗口打开 */
  private Boolean openInNewWindow;

  /** 菜单排序 */
  private Integer order;

  /** 菜单标题 */
  private String title;

  /** 状态 */
  private String status;
}
