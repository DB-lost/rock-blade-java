/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-21 14:06:00
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:27:40
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/aspect/ApiMonitorAspect.java
 * @Description: API接口监控切面 用于收集接口调用的响应时间、请求量、错误率等指标
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ApiMonitorAspect {

  private final MeterRegistry meterRegistry;

  public ApiMonitorAspect(MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
      + "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
  public Object monitorApiEndpoint(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String endpoint = request.getRequestURI();
    String method = request.getMethod();

    Timer.Sample sample = Timer.start(meterRegistry);
    boolean success = true;

    try {
      Object result = joinPoint.proceed();
      // 记录请求成功次数
      meterRegistry
          .counter("http.server.requests.success", "endpoint", endpoint, "method", method)
          .increment();
      return result;
    } catch (Exception e) {
      success = false;
      // 记录请求失败次数
      meterRegistry
          .counter(
              "http.server.requests.errors",
              "endpoint",
              endpoint,
              "method",
              method,
              "exception",
              e.getClass().getSimpleName())
          .increment();
      throw e;
    } finally {
      // 记录响应时间
      sample.stop(
          Timer.builder("http.server.requests.duration")
              .tags("endpoint", endpoint, "method", method, "status", success ? "success" : "error")
              .description("HTTP请求响应时间")
              .publishPercentiles(0.5, 0.95, 0.99) // 添加50%、95%、99%的响应时间统计
              .register(meterRegistry));

      // 记录总请求量
      meterRegistry
          .counter("http.server.requests.total", "endpoint", endpoint, "method", method)
          .increment();
    }
  }
}
