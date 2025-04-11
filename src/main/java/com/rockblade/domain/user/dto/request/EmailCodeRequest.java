/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:25:48
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 11:02:28
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/EmailCodeRequest.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "邮箱验证码请求")
public class EmailCodeRequest {

    /** 邮箱 */
    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    /** 业务类型（register-注册 reset-重置密码） */
    @Schema(description = "业务类型（register-注册 reset-重置密码）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "业务类型不能为空")
    private String type;
}
