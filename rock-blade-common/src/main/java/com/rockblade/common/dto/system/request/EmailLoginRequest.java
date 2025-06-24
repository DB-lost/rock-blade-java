/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-13 20:25:22
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:15:46
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/request/EmailLoginRequest.java
 * @Description: 邮箱登录请求
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求")
public class EmailLoginRequest {
  /** 邮箱 */
  @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.email.not.blank}")
  private String email;

  /** 密码 */
  @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.password.not.blank}")
  private String password;

  /** 随机字符串 */
  @Schema(description = "随机字符串", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.nonce.not.blank}")
  private String nonce;
}
