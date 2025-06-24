/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 19:44:59
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:28:13
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/config/MybatisFlexConfig.java
 * @Description: Mybatis flex配置
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import org.springframework.context.annotation.Configuration;

import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;

@Configuration
public class MybatisFlexConfig implements ConfigurationCustomizer {

  @Override
  public void customize(FlexConfiguration configuration) {
    // 在这里为 configuration 进行配置
  }
}
