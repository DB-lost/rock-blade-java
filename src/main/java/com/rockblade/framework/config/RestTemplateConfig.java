/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 08:43:34
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 09:02:34
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/config/RestTemplateConfig.java
 * @Description: Rest模板
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */

package com.rockblade.framework.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  /**
   * Rest模板
   *
   * @param restTemplateBuilder Rest模板生成器
   * @return {@link RestTemplate }
   * @author DB
   * @since 2024/05/23
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
        .requestFactory(
            () -> {
              SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
              factory.setConnectTimeout(5000);
              factory.setReadTimeout(10000);
              return factory;
            })
        .build();
  }
}
