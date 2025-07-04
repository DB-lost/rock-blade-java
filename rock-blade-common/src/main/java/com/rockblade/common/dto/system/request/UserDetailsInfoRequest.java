/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-17 09:50:33
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:17:18
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/request/UserDetailsInfoRequest.java
 * @Description: 用户详情请求实体
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户详情请求实体")
public class UserDetailsInfoRequest {

  /** 邮箱 */
  @Schema(description = "邮箱")
  private String email;

  /** 头像 */
  @Schema(description = "头像")
  private String avatar;

  /** 昵称 */
  @Schema(description = "昵称")
  private String nickname;

  /** 手机号 */
  @Schema(description = "手机号")
  private String phone;
}
