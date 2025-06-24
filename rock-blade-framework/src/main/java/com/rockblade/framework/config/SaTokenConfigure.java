/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 19:46:28
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:32:01
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/config/SaTokenConfigure.java
 * @Description: SaToken配置中心
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rockblade.common.constants.Constants;
import com.rockblade.framework.filter.RepeatableFilter;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SaTokenConfigure implements WebMvcConfigurer {

  @Autowired
  private RockBladeConfig rockBladeConfig;

  /**
   * 获取servlet过滤器
   *
   * @return {@link SaServletFilter }
   * @author DB
   * @since 2024/05/23
   */
  @Bean
  public SaServletFilter getSaServletFilter() {
    return new RepeatableFilter()
        // 指定 拦截路由 与 放行路由
        // .addInclude("/**").addExclude(rockBladeConfig.getJwt().getWhiteList().split(","))
        // 认证函数: 每次请求执行
        // .setAuth(obj -> {
        // 登录认证 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
        // SaRouter.match("/**", "/user/doLogin", StpUtil::checkLogin);
        // 更多拦截处理方式，请参考“路由拦截式鉴权”章节 */
        // })
        // 异常处理函数：每次认证函数发生异常时执行此函数
        // .setError(e -> SaResult.error(e.getMessage()))
        // 前置函数：在每次认证函数之前执行（BeforeAuth 不受 includeList 与 excludeList 的限制，所有请求都会进入）
        .setBeforeAuth(
            r -> {
              // ---------- 设置一些安全响应头 ----------
              SaHolder.getResponse()
                  // 服务器名称
                  .setServer("RockBlade")
                  // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                  .setHeader("X-Frame-Options", "SAMEORIGIN")
                  // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                  .setHeader("X-XSS-Protection", "1; mode=block")
                  // 禁用浏览器内容嗅探
                  .setHeader("X-Content-Type-Options", "nosniff");
            });
  }

  /**
   * 注册 Sa-Token 拦截器，打开注解式鉴权功能
   *
   * @param registry 拦截器注册
   * @author DB
   * @since 2024/3/20
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    registry
        .addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
        .addPathPatterns("/**")
        .excludePathPatterns(Arrays.asList(rockBladeConfig.getJwt().getWhiteList().split(",")));
  }

  /**
   * Sa-Token 整合 jwt (Simple 简单模式)
   *
   * @return StpLogic
   * @author DB
   * @since 2024/3/20
   */
  @Bean
  public StpLogic getStpLogicJwt() {
    return new StpLogicJwtForSimple();
  }

  /**
   * 配置跨源访问(CORS)
   *
   * @return {@link CorsConfigurationSource }
   * @author DB
   * @since 2024/05/23
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Collections.singletonList("*"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    configuration.applyPermitDefaultValues();
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * 资源映射
   *
   * @param registry 注册
   * @author DB
   * @since 2023/5/27 13:57
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 本地文件上传路径
    registry
        .addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
        .addResourceLocations("file:" + rockBladeConfig.getProfile() + "/");
  }
}
