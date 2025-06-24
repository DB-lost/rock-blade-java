/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-20 15:13:41
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-23 00:26:19
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/monitor/AlertService.java
 * @Description: 监控告警服务 处理系统告警通知
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.monitor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rockblade.domain.system.entity.AlertHistory;
import com.rockblade.domain.system.service.AlertHistoryService;
import com.rockblade.framework.config.properties.AlertProperties;
import com.rockblade.framework.core.base.exception.UtilException;
import com.rockblade.framework.handler.EmailHandler;
import com.rockblade.framework.utils.MessageUtils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableConfigurationProperties(AlertProperties.class)
public class AlertService {

  private AlertProperties alertProperties;

  @Autowired
  private AlertHistoryService alertHistoryService;

  @Autowired
  private EmailHandler emailHandler;

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  /**
   * 处理告警信息
   *
   * @param alertInfo 告警信息
   */
  @Async
  public void handleAlert(Map<String, Object> alertInfo) {
    String alertName = (String) alertInfo.get("alertname");
    String metric = (String) alertInfo.get("metric");
    String severity = (String) alertInfo.get("severity");
    String summary = (String) alertInfo.get("summary");
    String description = (String) alertInfo.get("description");
    Double value = Double.valueOf(alertInfo.get("value").toString());

    log.info("收到告警信息: name={}, metric={}, severity={}", alertName, metric, severity);

    try {
      // 记录告警历史
      AlertHistory history = new AlertHistory();
      history.setAlertName(alertName);
      history.setMetric(metric);
      history.setValue(value);
      history.setSeverity(severity);
      history.setFireTime(LocalDateTime.now());
      history.setStatus("firing");
      alertHistoryService.save(history);

      // 构建告警邮件内容
      StringBuilder content = new StringBuilder();
      content.append("<h2>系统告警通知</h2>");
      content.append("<p>时间: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("</p>");
      content.append("<p>告警名称: ").append(alertName).append("</p>");
      content.append("<p>严重程度: ").append(severity).append("</p>");
      content.append("<p>触发值: ").append(value).append("</p>");
      content.append("<p>告警概要: ").append(summary).append("</p>");
      content.append("<p>详细描述: ").append(description).append("</p>");

      if (StrUtil.isBlank(alertProperties.getReceivers())) {
        throw new UtilException("接收人不能为空");
      }

      // 获取接收人列表
      List<String> receivers = Arrays.asList(alertProperties.getReceivers().split(","));

      // 发送告警邮件
      emailHandler.sendAlertEmail(
          receivers, MessageUtils.message("alert.subject"), content.toString());

      log.info("告警通知发送成功: name={}, metric={}", alertName, metric);
    } catch (Exception e) {
      log.error("处理告警信息失败: " + e.getMessage(), e);
    }
  }
}
