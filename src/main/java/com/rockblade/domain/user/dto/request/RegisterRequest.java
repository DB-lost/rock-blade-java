package com.rockblade.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "注册请求")
public class RegisterRequest {

    /** 用户名 */
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.username.not.blank}")
    private String username;

    /** 邮箱 */
    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.email.not.blank}")
    @Email(message = "{validation.email.format}")
    private String email;

    /** 密码 */
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.password.not.blank}")
    @Size(min = 6, max = 20, message = "{validation.password.size}")
    private String password;

    /** 验证码 */
    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.code.not.blank}")
    private String code;

    /** 随机字符串 */
    @Schema(description = "随机字符串", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.nonce.not.blank}")
    private String nonce;
}
