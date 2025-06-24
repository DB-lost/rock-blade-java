/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-23 00:19:34
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:13:32
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/config/properties/AlertProperties.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "alert")
public class AlertProperties {

  /** 接收人 */
  private String receivers;

  /** 发件人显示名称 */
  private String from;

  /** 邮件主题 */
  private String subject;
}
