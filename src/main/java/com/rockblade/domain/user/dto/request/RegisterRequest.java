/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:26:38
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 10:48:33
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/RegisterRequest.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
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
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 邮箱 */
    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /** 密码 */
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    private String password;

    /** 验证码 */
    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "验证码不能为空")
    private String code;

    /** 随机字符串 */
    @NotBlank(message = "随机字符串不能为空")
    @Schema(description = "随机字符串", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nonce;
}
