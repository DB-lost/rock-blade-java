/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 19:56:48
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:29:52
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/handler/GlobalExceptionHandler.java
 * @Description: 全局异常处理程序
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.handler;

import java.util.Objects;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rockblade.framework.core.base.entity.R;
import com.rockblade.framework.core.base.exception.ServiceException;

import cn.dev33.satoken.exception.SaTokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 无登录异常
   *
   * @param e e
   * @return {@link R }<{@link Void }>
   * @author DB
   * @since 2024/05/23
   */
  @ExceptionHandler(SaTokenException.class)
  public R<Void> handleNotLoginException(SaTokenException e) {
    log.error(e.getMessage(), e);
    return R.fail(e.getCode(), e.getMessage());
  }

  /**
   * 业务异常
   *
   * @param e e
   * @return {@link R }<{@link Void }>
   * @author DB
   * @since 2024/05/23
   */
  @ExceptionHandler(ServiceException.class)
  public R<Void> handleServiceException(ServiceException e) {
    log.error(e.getMessage(), e);
    return R.fail(e.getMessage());
  }

  /**
   * 拦截未知的运行时异常
   *
   * @param e       异常
   * @param request 请求
   * @return {@link R }<{@link Void }>
   * @author DB
   * @since 2024/05/23
   */
  @ExceptionHandler(RuntimeException.class)
  public R<Void> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
    String requestUrl = request.getRequestURI();
    log.error("请求地址'{}',发生未知异常.", requestUrl, e);
    return R.fail(e.getMessage());
  }

  /**
   * 系统异常
   *
   * @param e       异常
   * @param request 请求
   * @return {@link R }<{@link Void }>
   * @author DB
   * @since 2024/05/23
   */
  @ExceptionHandler(Exception.class)
  public R<Void> handleException(Exception e, HttpServletRequest request) {
    String requestUrl = request.getRequestURI();
    log.error("请求地址'{}',发生系统异常.", requestUrl, e);
    return R.fail(e.getMessage());
  }

  /**
   * 自定义验证异常
   *
   * @param e e
   * @return {@link R }<{@link Void }>
   * @author DB
   * @since 2024/05/23
   */
  @ExceptionHandler(BindException.class)
  public R<Void> handleBindException(BindException e) {
    log.error(e.getMessage(), e);
    String message = e.getAllErrors().get(0).getDefaultMessage();
    return R.fail(message);
  }

  /**
   * 自定义验证异常
   *
   * @param e e
   * @return {@link R }<{@link Void }>
   * @author DB
   * @since 2024/05/23
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public R<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error(e.getMessage(), e);
    String message = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
    return R.fail(message);
  }
}
