package com.rockblade.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "重置密码请求")
public class ResetPasswordRequest {

    /** 邮箱 */
    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.email.not.blank}")
    @Email(message = "{validation.email.format}")
    private String email;

    /** 验证码 */
    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.code.not.blank}")
    private String code;

    /** 新密码 */
    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.new.password.not.blank}")
    @Size(min = 6, max = 20, message = "{validation.new.password.size}")
    private String newPassword;

    /** 随机字符串 */
    @Schema(description = "随机字符串", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.nonce.not.blank}")
    private String nonce;
}
