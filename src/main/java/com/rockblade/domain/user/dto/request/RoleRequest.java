/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-15 11:44:44
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-15 14:23:33
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/RoleRequest.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "角色响应数据")
public class RoleRequest {

    /** 角色ID */
    @Schema(description = "角色ID")
    private Long id;

    /** 角色名称 */
    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleName;

    /** 角色标识 */
    @NotBlank(message = "角色标识不能为空")
    @Schema(description = "角色标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private String roleKey;

    /** 状态：0 */
    @NotBlank(message = "状态不能为空")
    @Schema(description = "状态：0-禁用，1-启用", requiredMode = Schema.RequiredMode.REQUIRED)
    private String status;

    /** 权限列表 */
    @Schema(description = "权限列表")
    private Long[] permissions;

    /** 备注 */
    @Schema(description = "备注")
    private String remark;
}
