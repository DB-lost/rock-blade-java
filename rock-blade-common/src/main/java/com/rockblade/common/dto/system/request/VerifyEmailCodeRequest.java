/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-06-24 13:00:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:17:41
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/request/VerifyEmailCodeRequest.java
 * @Description: 校验邮箱验证码请求
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "校验邮箱验证码请求")
public class VerifyEmailCodeRequest {
  /** 邮箱 */
  @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.email.not.blank}")
  @Email(message = "{validation.email.format}")
  private String email;

  /** 验证码 */
  @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.business.type.not.blank}")
  private String code;

  /** 业务类型（register-注册 reset-重置密码） */
  @Schema(description = "业务类型（register-注册 reset-重置密码）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.business.type.not.blank}")
  private String type;
}
