/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-24 17:44:17
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:52:44
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/ExportTaskServiceImpl.java
 * @Description: 数据导出任务表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.system.entity.AlertHistory;
import com.rockblade.system.entity.ExportTask;
import com.rockblade.system.mapper.ExportTaskMapper;
import com.rockblade.system.service.AlertHistoryService;
import com.rockblade.system.service.ExportTaskService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvWriter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("exportTaskService")
public class ExportTaskServiceImpl extends ServiceImpl<ExportTaskMapper, ExportTask>
    implements ExportTaskService {

  @Autowired private MeterRegistry meterRegistry;

  @Autowired private AlertHistoryService alertHistoryService;

  private static final String EXPORT_DIR = "exports";
  private static final DateTimeFormatter FILE_DATE_FORMAT =
      DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  @Override
  public ExportTask startExport(String exportType, Map<String, Object> params) {
    // 创建导出目录
    try {
      Files.createDirectories(Path.of(EXPORT_DIR));
    } catch (Exception e) {
      log.error("创建导出目录失败", e);
      throw new RuntimeException("创建导出目录失败", e);
    }

    // 创建导出任务
    ExportTask task = new ExportTask();
    task.setName("Export_" + exportType + "_" + LocalDateTime.now().format(FILE_DATE_FORMAT));
    task.setExportType(exportType);
    task.setStatus("PROCESSING");
    task.setProgress(0);
    save(task);

    // 异步执行导出
    executeExport(task.getId());

    return task;
  }

  @Async
  @Override
  public void executeExport(String taskId) {
    ExportTask task = getById(taskId);
    if (task == null) {
      log.error("导出任务不存在: {}", taskId);
      return;
    }

    try {
      String filePath =
          switch (task.getExportType()) {
            case "METRICS" -> exportMetrics(task);
            case "ALERTS" -> exportAlerts(task);
            default ->
                throw new IllegalArgumentException(
                    "Unsupported export type: " + task.getExportType());
          };

      task.setFilePath(filePath);
      task.setStatus("COMPLETED");
      task.setProgress(100);
    } catch (Exception e) {
      log.error("导出任务执行失败: {}", e.getMessage(), e);
      task.setStatus("FAILED");
      task.setErrorMessage(e.getMessage());
    }

    updateById(task);
  }

  @Override
  public Resource getExportFile(String taskId) {
    ExportTask task = getById(taskId);
    if (task == null || task.getFilePath() == null) {
      throw new RuntimeException("Export file not found");
    }
    return new FileSystemResource(task.getFilePath());
  }

  private String exportMetrics(ExportTask task) throws Exception {
    String fileName = EXPORT_DIR + "/" + task.getName() + "_metrics.csv";
    CsvWriter writer = new CsvWriter(FileUtil.getWriter(fileName, StandardCharsets.UTF_8, false));

    // 写入表头
    writer.writeLine("Metric", "Value", "Tags", "Timestamp");

    // 获取所有指标
    meterRegistry
        .getMeters()
        .forEach(
            meter -> {
              try {
                meter
                    .measure()
                    .forEach(
                        measurement -> {
                          try {
                            writer.writeLine(
                                meter.getId().getName(),
                                String.valueOf(measurement.getValue()),
                                meter.getId().getTags().toString(),
                                LocalDateTime.now().toString());
                          } catch (Exception e) {
                            log.error("写入指标数据失败", e);
                          }
                        });
              } catch (Exception e) {
                log.error("处理指标数据失败", e);
              }
            });

    writer.close();
    return fileName;
  }

  private String exportAlerts(ExportTask task) throws Exception {
    String fileName = EXPORT_DIR + "/" + task.getName() + "_alerts.csv";
    List<AlertHistory> alerts = alertHistoryService.list();

    CsvWriter writer = new CsvWriter(FileUtil.getWriter(fileName, StandardCharsets.UTF_8, false));

    // 写入表头
    writer.writeLine("Alert Name", "Metric", "Value", "Severity", "Fire Time", "Status");

    // 写入数据
    for (AlertHistory alert : alerts) {
      writer.writeLine(
          alert.getAlertName(),
          alert.getMetric(),
          String.valueOf(alert.getValue()),
          alert.getSeverity(),
          alert.getFireTime().toString(),
          alert.getStatus());
    }

    writer.close();
    return fileName;
  }
}
