/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-03-24 15:54:19
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:39:30
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/handler/EmailHandler.java
 * @Description: 邮件服务
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.rockblade.common.exception.UtilException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailHandler {

  @Autowired private JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String fromEmail;

  /**
   * 发送告警邮件
   *
   * @param to 收件人列表
   * @param subject 邮件主题
   * @param content HTML格式的邮件内容
   */
  public void sendAlertEmail(List<String> to, String subject, String content) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to.toArray(new String[0]));
      helper.setFrom(fromEmail);
      helper.setSubject(subject);
      helper.setText(content, true);
      mailSender.send(message);
      log.info("告警邮件发送成功，接收者：{}", String.join(",", to));
    } catch (MessagingException e) {
      log.error("告警邮件发送失败，接收者：{}，错误信息：{}", String.join(",", to), e.getMessage());
      throw new UtilException("邮件发送失败");
    }
  }

  /**
   * 发送验证码邮件
   *
   * @param to 收件人
   * @param code 验证码
   * @param expireMinutes 过期时间（分钟）
   * @param subject 邮件主题
   */
  private void sendVerificationEmail(String to, String code, int expireMinutes, String subject) {
    String htmlContent =
        String.format(
            """
            <div style="text-align: center;">
                <h2>%s</h2>
                <p>您的验证码是：</p>
                <p style="font-size: 24px; color: #007bff; margin: 20px 0;">%s</p>
                <p>验证码有效期为%d分钟，请尽快完成验证。</p>
                <p>如果这不是您的操作，请忽略此邮件。</p>
            </div>
            """,
            subject, code, expireMinutes);

    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setFrom(fromEmail);
      helper.setSubject(subject);
      helper.setText(htmlContent, true);
      mailSender.send(message);
      log.info("{}邮件发送成功，接收者：{}", subject, to);
    } catch (MessagingException e) {
      log.error("{}邮件发送失败，接收者：{}，错误信息：{}", subject, to, e.getMessage());
      throw new UtilException("邮件发送失败");
    }
  }

  /**
   * 发送重置密码邮件
   *
   * @param to 收件人
   * @param code 验证码
   * @param expireMinutes 过期时间（分钟）
   */
  public void sendResetPasswordEmail(String to, String code, int expireMinutes) {
    sendVerificationEmail(to, code, expireMinutes, "重置密码验证码");
  }

  /**
   * 发送邮箱验证码
   *
   * @param to 收件人
   * @param code 验证码
   * @param expireMinutes 过期时间（分钟）
   */
  public void sendEmailCode(String to, String code, int expireMinutes) {
    sendVerificationEmail(to, code, expireMinutes, "邮箱验证码");
  }
}
