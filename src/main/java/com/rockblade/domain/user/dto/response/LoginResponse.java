/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:27:19
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 10:59:23
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/response/LoginResponse.java
 * @Description: 登录响应
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录响应")
public class LoginResponse {

    /** 用户令牌 */
    @Schema(description = "用户令牌")
    private String tokenValue;

    /** 用户信息 */
    @Schema(description = "用户信息")
    private UserInfoResponse userInfo;
}
