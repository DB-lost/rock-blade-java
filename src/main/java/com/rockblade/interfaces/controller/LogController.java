/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-23 10:44:32
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-23 11:01:19
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/LogController.java
 * @Description: 日志管理接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.interfaces.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rockblade.domain.system.dto.request.LogSearchRequest;
import com.rockblade.domain.system.dto.response.LogFileInfoResponse;
import com.rockblade.domain.system.service.LogService;
import com.rockblade.framework.core.base.entity.R;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/logs")
public class LogController {

  @Autowired private LogService logService;

  /**
   * 获取日志文件列表
   *
   * @param logType 日志类型（可选）
   * @return 日志文件列表
   */
  @GetMapping
  @SaCheckPermission("system:log:list")
  public R<List<LogFileInfoResponse>> getLogFiles(
      @Parameter(description = "日志类型（可选）", example = "\"INFO\", \"ERROR\", \"SQL\", \"ALL\"")
          @RequestParam(required = false)
          String logType) {
    return R.ok(logService.getLogFiles(logType));
  }

  /**
   * 下载日志文件
   *
   * @param fileName 文件名
   * @return 日志文件
   */
  @GetMapping("/download/{fileName}")
  @SaCheckPermission("system:log:download")
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
  @PostMapping("/search")
  @SaCheckPermission("system:log:search")
  public ResponseEntity<String> searchLogContent(@RequestBody LogSearchRequest request) {
    return ResponseEntity.ok(logService.searchLogContent(request));
  }

  /**
   * 清理过期日志
   *
   * @param days 保留天数
   * @return 清理结果
   */
  @DeleteMapping("/cleanup/{days}")
  @SaCheckPermission("system:log:cleanup")
  public ResponseEntity<Boolean> cleanupLogs(@PathVariable int days) {
    return ResponseEntity.ok(logService.cleanupLogs(days));
  }
}
