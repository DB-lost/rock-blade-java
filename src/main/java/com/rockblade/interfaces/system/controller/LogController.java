/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-23 10:44:32
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-24 22:51:06
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/system/controller/LogController.java
 * @Description: 日志管理接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.interfaces.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rockblade.domain.system.dto.request.LogSearchRequest;
import com.rockblade.domain.system.dto.response.LogFileInfoResponse;
import com.rockblade.domain.system.service.LogService;
import com.rockblade.framework.core.base.entity.R;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "日志接口")
@RequestMapping("/logs")
public class LogController {

  @Autowired
  private LogService logService;

  /**
   * 获取日志文件列表
   *
   * @param logType 日志类型（可选）
   * @return 日志文件列表
   */
  @GetMapping
  @Operation(summary = "获取日志文件列表")
  // @SaCheckPermission("system:log:list")
  public R<List<LogFileInfoResponse>> getLogFiles(
      @Parameter(description = "日志类型（可选）", example = "\"INFO\", \"ERROR\", \"SQL\", \"ALL\"") @RequestParam(required = false) String logType) {
    return R.ok(logService.getLogFiles(logType));
  }

  /**
   * 下载日志文件
   *
   * @param fileName 文件名
   * @return 日志文件
   */
  @GetMapping("/download/{fileName}")
  @Operation(summary = "下载日志文件")
  // @SaCheckPermission("system:log:download")
  public ResponseEntity<Resource> downloadLogFile(@PathVariable String fileName) {
    Resource file = logService.downloadLogFile(fileName);
    if (file == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(file);
  }

  /**
   * 搜索日志内容
   *
   * @param request 搜索请求
   * @return 日志内容
   */
  @GetMapping("/search")
  @Operation(summary = "搜索日志内容")
  // @SaCheckPermission("system:log:search")
  public R<String> searchLogContent(LogSearchRequest request) {
    return R.ok(logService.searchLogContent(request));
  }

  /**
   * 清理过期日志
   *
   * @param days 保留天数
   * @return 清理结果
   */
  @Operation(summary = "清理过期日志")
  @DeleteMapping("/cleanup/{days}")
  // @SaCheckPermission("system:log:cleanup")
  public R<Boolean> cleanupLogs(@PathVariable int days) {
    return R.ok(logService.cleanupLogs(days));
  }
}
