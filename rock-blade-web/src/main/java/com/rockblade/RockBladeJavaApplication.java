/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 20:49:05
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 09:56:19
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/RockBladeJavaApplication.java
 * @Description: 启动类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */

package com.rockblade;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.rockblade.**"})
@MapperScan("com.rockblade.infrastructure.**.mapper")
@EnableAsync
@EnableScheduling
public class RockBladeJavaApplication {

  public static void main(String[] args) {
    SpringApplication.run(RockBladeJavaApplication.class, args);
  }
}