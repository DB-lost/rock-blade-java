/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:26:27
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 10:58:30
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/LoginRequest.java
 * @Description: 登录请求
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录请求")
public class LoginRequest {

    /** 用户名 */
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名不能为空")
    private String username;

    /** 密码 */
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    /** 随机字符串 */
    @NotBlank(message = "随机字符串不能为空")
    @Schema(description = "随机字符串", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nonce;
}
