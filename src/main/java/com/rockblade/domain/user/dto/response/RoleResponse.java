/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-15 11:44:54
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-15 16:23:37
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/response/RoleResponse.java
 * @Description: 角色响应DTO
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色响应数据")
public class RoleResponse {

    /** 角色ID */
    @Schema(description = "角色ID")
    private String id;

    /** 角色名称 */
    @Schema(description = "角色名称")
    private String roleName;

    /** 角色标识 */
    @Schema(description = "角色标识")
    private String roleKey;

    /** 状态：0 */
    @Schema(description = "状态：0-禁用，1-启用")
    private String status;

    /** 权限列表 */
    @Schema(description = "权限列表")
    private String[] permissions;

    /** 备注 */
    @Schema(description = "备注")
    private String remark;
}
