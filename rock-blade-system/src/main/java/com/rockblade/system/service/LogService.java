/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-23 10:43:29
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:53:01
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/LogService.java
 * @Description: 日志服务接口。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service;

import java.util.List;

import org.springframework.core.io.Resource;

import com.rockblade.domain.system.dto.request.LogSearchRequest;
import com.rockblade.domain.system.dto.response.LogFileInfoResponse;

public interface LogService {

  /**
   * 获取日志文件列表
   *
   * @param logType 日志类型（可选）
   * @return 日志文件信息列表
   */
  List<LogFileInfoResponse> getLogFiles(String logType);

  /**
   * 下载日志文件
   *
   * @param fileName 文件名
   * @return 日志文件资源
   */
  Resource downloadLogFile(String fileName);

  /**
   * 查询日志内容
   *
   * @param request 查询参数
   * @return 日志内容（分页）
   */
  String searchLogContent(LogSearchRequest request);

  /**
   * 清理过期日志
   *
   * @param days 保留天数
   * @return 清理结果
   */
  boolean cleanupLogs(int days);
}
