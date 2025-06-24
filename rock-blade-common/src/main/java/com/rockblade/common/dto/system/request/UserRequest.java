/*
 * @Author: DB
 * @Date: 2025-04-16 14:30:00
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:17:36
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/request/UserRequest.java
 * @Description: 用户请求实体
 */
package com.rockblade.common.dto.system.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户请求实体")
public class UserRequest {

  /** 用户id */
  @Schema(description = "用户ID")
  private String id;

  /** 角色id */
  @Schema(description = "角色id")
  private List<String> roleIds;

  /** 部门id */
  @Schema(description = "部门id")
  private List<String> deptIds;

  /** 状态 */
  @Schema(description = "状态（1正常 0停用）")
  private String status;
}
