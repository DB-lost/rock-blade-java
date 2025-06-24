/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:27:38
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:18:40
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/response/UserInfoResponse.java
 * @Description: 用户信息响应
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息响应")
public class UserInfoResponse {

  /** 用户id */
  @Schema(description = "用户ID")
  private String id;

  /** 邮箱 */
  @Schema(description = "邮箱")
  private String email;

  /** 用户名 */
  @Schema(description = "用户名")
  private String username;

  /** 头像 */
  @Schema(description = "头像")
  private String avatar;

  /** 角色 */
  @Schema(description = "角色")
  private String role;

  /** 昵称 */
  @Schema(description = "昵称")
  private String nickname;

  /** 手机号 */
  @Schema(description = "手机号")
  private String phone;
}
