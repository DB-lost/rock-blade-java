/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-12 15:49:10
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/MenuRequest.java
 * @Description: 菜单请求DTO
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "菜单请求参数")
public class MenuRequest {

    /** 菜单ID */
    @Schema(description = "菜单ID")
    private Long id;

    /** 菜单名称 */
    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String menuName;

    /** 父菜单ID */
    @Schema(description = "父菜单ID")
    private Long parentId;

    /** 显示顺序 */
    @NotNull(message = "显示顺序不能为空")
    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer orderNum;

    /** 路由地址 */
    @Schema(description = "路由地址")
    private String path;

    /** 组件路径 */
    @Schema(description = "组件路径")
    private String component;

    /** 路由参数 */
    @Schema(description = "路由参数")
    private String queryParam;

    /** 是否为外链 */
    @Schema(description = "是否为外链")
    private Boolean isFrame;

    /** 是否缓存 */
    @Schema(description = "是否缓存")
    private Boolean isCache;

    /** 菜单类型 (M目录 C菜单 F按钮) */
    @NotBlank(message = "菜单类型不能为空")
    @Schema(description = "菜单类型 (M目录 C菜单 F按钮)", requiredMode = Schema.RequiredMode.REQUIRED)
    private String menuType;

    /** 显示状态 (0显示 1隐藏) */
    @Schema(description = "显示状态 (1显示 0隐藏)")
    private String visible;

    /** 菜单状态 (0正常 1停用) */
    @Schema(description = "菜单状态 (1正常 0停用)")
    private String status;

    /** 权限标识 */
    @Schema(description = "权限标识")
    private String perms;

    /** 菜单图标 */
    @Schema(description = "菜单图标")
    private String icon;

    /** 备注 */
    @Schema(description = "备注")
    private String remark;
}
