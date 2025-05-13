/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-12 17:37:36
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-12 17:39:19
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/response/PermissionsResponse.java
 * @Description: 角色和权限码响应
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色和权限码响应")
public class PermissionsResponse {

  /** 角色 */
  @Schema(description = "角色")
  private List<String> roles;

  /** 权限码 */
  @Schema(description = "权限码")
  private List<String> permissions;
}
