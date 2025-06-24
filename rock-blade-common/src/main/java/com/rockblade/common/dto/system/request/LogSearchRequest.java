/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-23 10:43:14
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-23 10:50:43
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/request/LogSearchRequest.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "日志搜索请求参数")
public class LogSearchRequest {

  /** 日志类型 */
  @Schema(description = "日志类型", example = "\"INFO\", \"ERROR\", \"SQL\", \"ALL\"")
  private String logType;

  /** 关键字 */
  @Schema(description = "关键字", example = "登录")
  private String keyword;

  /** 开始时间 */
  @Schema(description = "开始时间", example = "2025-05-01 00:00:00")
  private String startTime;

  /** 结束时间 */
  @Schema(description = "结束时间", example = "2025-05-31 23:59:59")
  private String endTime;
}
