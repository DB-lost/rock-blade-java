package com.rockblade.api.controller.system;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rockblade.system.entity.ExportTask;
import com.rockblade.system.service.ExportTaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 数据导出任务表 控制层。
 *
 * @author
 * @since 2025-05-21
 */
@RestController
@Tag(name = "数据导出任务表接口")
@RequestMapping("/exportTask")
public class ExportTaskController {

  @Autowired private ExportTaskService exportTaskService;

  /**
   * 开始导出任务。
   *
   * @param params 导出参数
   * @return 导出任务信息
   */
  @PostMapping("/start")
  @Operation(summary = "开始导出任务")
  public ExportTask startExport(
      @RequestBody @Parameter(description = "导出参数") Map<String, Object> params) {
    String exportType = (String) params.get("exportType");
    return exportTaskService.startExport(exportType, params);
  }

  /**
   * 下载导出文件。
   *
   * @param taskId 任务ID
   * @return 导出文件
   */
  @GetMapping("/download/{taskId}")
  @Operation(summary = "下载导出文件")
  public ResponseEntity<Resource> downloadExport(
      @PathVariable @Parameter(description = "任务ID") String taskId) {
    ExportTask task = exportTaskService.getById(taskId);
    if (task == null) {
      return ResponseEntity.notFound().build();
    }

    Resource resource = exportTaskService.getExportFile(taskId);
    String filename = task.getName() + ".csv";

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
        .body(resource);
  }
}
