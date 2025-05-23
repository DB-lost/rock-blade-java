/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-02-22 23:18:25
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-21 11:24:56
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/config/EnvConfig.java
 * @Description: 读取.env文件的配置
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class EnvConfig implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(
      ConfigurableEnvironment environment, SpringApplication application) {
    try {
      // 加载.env文件
      Dotenv dotenv =
          Dotenv.configure().directory(System.getProperty("user.dir")).filename(".env").load();

      // 将.env的配置转换为Properties
      Properties properties = new Properties();
      dotenv.entries().forEach(entry -> properties.put(entry.getKey(), entry.getValue()));

      // 将Properties添加到Spring环境中，优先级设置为最高
      PropertiesPropertySource propertySource = new PropertiesPropertySource("dotenv", properties);
      environment.getPropertySources().addFirst(propertySource);
    } catch (Exception e) {
      // 记录错误但允许应用继续启动
      System.err.println("Failed to load .env file: " + e.getMessage());
    }
  }
}
