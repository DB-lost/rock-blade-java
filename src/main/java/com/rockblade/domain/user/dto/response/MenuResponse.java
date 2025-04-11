/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 16:40:09
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/response/MenuResponse.java
 * @Description: 菜单响应DTO
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "菜单响应参数")
public class MenuResponse {

    /** 菜单ID */
    @Schema(description = "菜单ID")
    private Long id;

    /** 菜单名称 */
    @Schema(description = "菜单名称")
    private String menuName;

    /** 菜单名称（用于前端展示） */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /** 父菜单名称（用于前端展示） */
    @Schema(description = "显示顺序")
    private Integer orderNum;

    /** 显示顺序 */
    @Schema(description = "路由地址")
    private String path;

    /** 路由地址 */
    @Schema(description = "组件路径")
    private String component;

    /** 组件路径 */
    @Schema(description = "路由参数")
    private String queryParam;

    /** 路由参数 */
    @Schema(description = "是否为外链")
    private Boolean isFrame;

    /** 是否为外链 */
    @Schema(description = "是否缓存")
    private Boolean isCache;

    /** 是否缓存 */
    @Schema(description = "菜单类型 (M目录 C菜单 F按钮)")
    private String menuType;

    /** 菜单类型 (M目录 C菜单 F按钮) */
    @Schema(description = "显示状态 (0显示 1隐藏)")
    private String visible;

    /** 显示状态 (0显示 1隐藏) */
    @Schema(description = "菜单状态 (0正常 1停用)")
    private String status;

    /** 菜单状态 (0正常 1停用) */
    @Schema(description = "权限标识")
    private String perms;

    /** 权限标识 */
    @Schema(description = "菜单图标")
    private String icon;

    /** 菜单图标 */
    @Schema(description = "备注")
    private String remark;

    /** 备注 */
    @Schema(description = "子菜单")
    private List<MenuResponse> children;

    /** 子菜单 */
    @Schema(description = "是否含有子菜单")
    private Boolean hasChildren;
}
