/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 10:27:57
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:18:28
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/response/PublicKeyResponse.java
 * @Description: 公钥响应
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "公钥响应")
public class PublicKeyResponse {

  /** 公钥 */
  @Schema(description = "公钥")
  private String publicKey;

  /** 随机字符串 */
  @Schema(description = "随机字符串")
  private String nonce;
}
