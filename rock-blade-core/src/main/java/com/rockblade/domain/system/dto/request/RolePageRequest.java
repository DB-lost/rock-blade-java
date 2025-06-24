/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-15 17:16:11
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 10:09:42
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/request/RolePageRequest.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-15 11:44:34
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-15 12:16:14
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/RolePageRequest.java
 * @Description: 角色请求DTO
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色分页请求参数")
public class RolePageRequest {
  /** 角色名称 */
  @Schema(description = "角色名称")
  private String roleName;

  /** 状态：0-禁用，1-启用 */
  @Schema(description = "状态：0-禁用，1-启用")
  private String status;

  /** 备注 */
  @Schema(description = "备注")
  private String remark;

  /** 创建时间 */
  @Schema(description = "创建时间")
  private LocalDate startTime;

  /** 创建时间 */
  @Schema(description = "创建时间")
  private LocalDate endTime;
}
