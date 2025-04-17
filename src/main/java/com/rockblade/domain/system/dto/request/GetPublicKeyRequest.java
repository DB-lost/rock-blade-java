/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-15 17:16:11
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 10:09:45
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/request/GetPublicKeyRequest.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:26:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 10:42:41
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/request/GetPublicKeyRequest.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取公钥请求")
public class GetPublicKeyRequest {

    /** 随机字符串 */
    @Schema(description = "随机字符串")
    private String nonce;
}
