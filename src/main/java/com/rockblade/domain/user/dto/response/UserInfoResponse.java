/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:27:38
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 10:27:43
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/response/UserInfoResponse.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息响应")
public class UserInfoResponse {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
