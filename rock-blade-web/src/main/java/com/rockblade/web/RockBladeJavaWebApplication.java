/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 20:49:05
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:49:42
 * @FilePath: /rock-blade-java/rock-blade-web/src/main/java/com/rockblade/web/RockBladeJavaWebApplication.java
 * @Description: 启动类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */

package com.rockblade.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.rockblade.**"})
@MapperScan("com.rockblade.**.mapper")
@EnableAsync
@EnableScheduling
public class RockBladeJavaWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(RockBladeJavaWebApplication.class, args);
  }
}
