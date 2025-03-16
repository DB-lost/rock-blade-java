/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-03-15 16:55:21
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-03-16 21:31:07
 * @FilePath: /rock-blade-admin-java/home/db/Workspace/Template-Workspace/rock-blade-java/src/main/java/com/rockblade/framework/config/DataSourceConfig.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

  @Value("${DEV_DATABASE_URL}")
  private String databaseUrl;

  @Value("${DEV_DATABASE_USERNAME}")
  private String databaseUsername;

  @Value("${DEV_DATABASE_PASSWORD}")
  private String databasePassword;

  @Bean
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(databaseUrl);
    dataSource.setUsername(databaseUsername);
    dataSource.setPassword(databasePassword);
    return dataSource;
  }
}
