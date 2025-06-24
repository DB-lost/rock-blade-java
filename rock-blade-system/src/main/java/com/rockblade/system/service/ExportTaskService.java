/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-21 14:57:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:17:45
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/ExportTaskService.java
 * @Description: 数据导出任务表 服务层。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service;

import java.util.Map;

import org.springframework.core.io.Resource;

import com.mybatisflex.core.service.IService;
import com.rockblade.system.entity.ExportTask;

public interface ExportTaskService extends IService<ExportTask> {

  /**
   * 启动导出任务
   *
   * @param exportType 导出类型
   * @param params 导出参数
   * @return 导出任务
   */
  ExportTask startExport(String exportType, Map<String, Object> params);

  /**
   * 执行导出任务
   *
   * @param taskId 任务ID
   */
  void executeExport(String taskId);

  /**
   * 获取导出文件
   *
   * @param taskId 任务ID
   * @return 导出文件资源
   */
  Resource getExportFile(String taskId);
}
