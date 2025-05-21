/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-20 15:13:41
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-21 11:12:06
 * @FilePath: /rock-blade-ITOM-Backstage/home/db/WorkSpace/Template-WorkSpace/rock-blade-java/src/main/java/com/rockblade/framework/monitor/AlertService.java
 * @Description: 监控告警服务 处理系统告警通知
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package main.java.com.rockblade.framework.monitor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rockblade.domain.system.entity.AlertHistory;
import com.rockblade.domain.system.entity.AlertRule;
import com.rockblade.domain.system.service.AlertHistoryService;
import com.rockblade.domain.system.service.AlertRuleService;
import com.rockblade.framework.utils.MessageUtils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlertService {

  @Autowired private JavaMailSender mailSender;

  @Autowired private AlertRuleService alertRuleService;

  @Autowired private AlertHistoryService alertHistoryService;

  private static final DateTimeFormatter DATE_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
      // 查找对应的告警规则
      AlertRule rule = alertRuleService.findByMetricAndEnabled(metric, true);
      if (rule == null) {
        log.warn("未找到metric={}的启用告警规则", metric);
        return;
      }

      // 记录告警历史
      AlertHistory history = new AlertHistory();
      history.setRuleId(rule.getId());
      history.setAlertName(alertName);
      history.setMetric(metric);
      history.setValue(value);
      history.setSeverity(severity);
      history.setFireTime(LocalDateTime.now());
      history.setStatus("firing");
      alertHistoryService.save(history);

      // 构建邮件内容
      String emailContent =
          buildAlertEmailContent(rule, alertName, severity, summary, description, value);

      // 发送告警邮件
      sendAlertEmail(rule, emailContent);

      log.info("告警通知发送成功: name={}, metric={}", alertName, metric);
    } catch (Exception e) {
      log.error("处理告警信息失败: " + e.getMessage(), e);
    }
  }

  /** 构建告警邮件内容 */
  private String buildAlertEmailContent(
      AlertRule rule,
      String alertName,
      String severity,
      String summary,
      String description,
      Double value) {
    if (StrUtil.isNotEmpty(rule.getTemplate())) {
      // 使用自定义模板
      return rule.getTemplate()
          .replace("${alertName}", alertName)
          .replace("${severity}", severity)
          .replace("${summary}", summary)
          .replace("${description}", description)
          .replace("${value}", String.valueOf(value))
          .replace("${time}", LocalDateTime.now().format(DATE_FORMATTER));
    }

    // 使用默认模板
    StringBuilder content = new StringBuilder();
    content.append("<h2>系统告警通知</h2>");
    content.append("<p>时间: ").append(LocalDateTime.now().format(DATE_FORMATTER)).append("</p>");
    content.append("<p>告警名称: ").append(alertName).append("</p>");
    content.append("<p>严重程度: ").append(severity).append("</p>");
    content.append("<p>触发值: ").append(value).append("</p>");
    content.append("<p>告警概要: ").append(summary).append("</p>");
    content.append("<p>详细描述: ").append(description).append("</p>");
    return content.toString();
  }

  /**
   * 发送告警邮件
   *
   * @param rule 告警规则
   * @param content 邮件内容
   */
  private void sendAlertEmail(AlertRule rule, String content) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setFrom(MessageUtils.message("alert.mail.from"));

      // 获取收件人列表
      List<String> receivers =
          rule.getReceivers() != null
              ? Arrays.asList(rule.getReceivers().split(","))
              : Arrays.asList(MessageUtils.message("alert.mail.to.default").split(","));

      if (CollUtil.isEmpty(receivers)) {
        log.warn("未配置告警接收人");
        return;
      }

      helper.setTo(receivers.toArray(new String[0]));
      helper.setSubject(MessageUtils.message("alert.mail.subject"));
      helper.setText(content, true);

      mailSender.send(message);
    } catch (Exception e) {
      log.error("发送告警邮件失败: " + e.getMessage(), e);
      throw new RuntimeException("发送告警邮件失败", e);
    }
  }
}
