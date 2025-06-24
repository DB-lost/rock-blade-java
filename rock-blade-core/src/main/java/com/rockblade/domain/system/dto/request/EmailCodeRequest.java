package com.rockblade.domain.system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "邮箱验证码请求")
public class EmailCodeRequest {

  /** 邮箱 */
  @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.email.not.blank}")
  @Email(message = "{validation.email.format}")
  private String email;

  /** 业务类型（register-注册 reset-重置密码） */
  @Schema(description = "业务类型（register-注册 reset-重置密码）", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "{validation.business.type.not.blank}")
  private String type;
}
