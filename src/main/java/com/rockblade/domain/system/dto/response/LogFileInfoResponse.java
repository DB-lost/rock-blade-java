/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-23 10:42:59
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-23 10:52:32
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/response/LogFileInfoResponse.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "日志文件信息响应参数")
public class LogFileInfoResponse {

  /** 文件名称 */
  @Schema(description = "文件名称", example = "application.log")
  private String fileName;

  /** 文件路径 */
  @Schema(description = "文件路径", example = "/var/logs/application.log")
  private String filePath;

  /** 文件大小 */
  @Schema(description = "文件大小", example = "102400")
  private Long fileSize;

  /** 文件修改时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "文件修改时间", example = "2025-05-01 10:00:00")
  private LocalDateTime lastModified;

  /** 日志类型 */
  @Schema(description = "日志类型", example = "\"INFO\", \"ERROR\", \"SQL\", \"ALL\"")
  private String logType;
}
