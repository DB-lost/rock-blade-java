/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:53:28
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 09:03:00
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/config/TaskThreadPoolConfig.java
 * @Description: 任务线程池配置
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "task.pool")
public class TaskThreadPoolConfig {

  /** 设置核心线程数 */
  private Integer corePoolSize;

  /** 设置最大线程数 */
  private Integer maxPoolSize;

  /** 设置空闲线程存活时间（秒） */
  private Integer keepAliveSeconds;

  /** 设置队列容量 */
  private Integer queueCapacity;

  /** 设置线程名称前缀 */
  private Integer awaitTerminationSeconds;

  /** 设置线程池等待终止时间(秒) */
  private String threadNamePrefix;
}
