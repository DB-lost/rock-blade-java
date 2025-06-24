/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 15:12:12
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:16:43
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/request/ResetPasswordRequest.java
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "重置密码请求")
public class ResetPasswordRequest {

  /** 邮箱 */
  @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.email.not.blank}")
  @Email(
      regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
      message = "{validation.email.format}",
      flags = Pattern.Flag.CASE_INSENSITIVE)
  private String email;

  /** 新密码 */
  @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.new.password.not.blank}")
  private String password;

  /** 随机字符串 */
  @Schema(description = "随机字符串", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.nonce.not.blank}")
  private String nonce;
}
