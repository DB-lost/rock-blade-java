/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-06-24 13:00:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:16:01
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/request/LoginRequest.java
 * @Description: 登录请求
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求")
public class LoginRequest {

  /** 用户名 */
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.username.not.blank}")
  private String username;

  /** 密码 */
  @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.password.not.blank}")
  private String password;

  /** 随机字符串 */
  @Schema(description = "随机字符串", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.nonce.not.blank}")
  private String nonce;
}
