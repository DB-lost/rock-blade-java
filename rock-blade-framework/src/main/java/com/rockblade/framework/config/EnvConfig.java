/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-02-22 23:18:25
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-07-02 17:46:40
 * @FilePath: /rock-blade-java-feishu/home/db/WorkSpace/Template-WorkSpace/rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/config/EnvConfig.java
 * @Description: 读取.env文件的配置
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import java.io.File;
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
      String currentDir = System.getProperty("user.dir");
      File envFile = findEnvFileUpwards(currentDir);

      if (envFile != null) {
        // 加载.env文件
        Dotenv dotenv = Dotenv.configure()
                .directory(envFile.getParent())
                .filename(".env")
                .load();

        // 将.env的配置转换为Properties
        Properties properties = new Properties();
        dotenv.entries().forEach(entry -> properties.put(entry.getKey(), entry.getValue()));

        // 将Properties添加到Spring环境中，优先级设置为最高
        PropertiesPropertySource propertySource = new PropertiesPropertySource("dotenv", properties);
        environment.getPropertySources().addFirst(propertySource);
      } else {
        System.err.println("Could not find .env file in any parent directory");
      }
    } catch (Exception e) {
      // 记录错误但允许应用继续启动
      System.err.println("Failed to load .env file: " + e.getMessage());
    }
  }

  private File findEnvFileUpwards(String startDir) {
    File dir = new File(startDir);
    while (dir != null) {
      File envFile = new File(dir, ".env");
      if (envFile.exists()) {
        return envFile;
      }
      dir = dir.getParentFile();
    }
    return null;
  }
}
