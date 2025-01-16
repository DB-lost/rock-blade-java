/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:30:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 09:03:29
 * @Description: 工具类异常
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.framework.core.base.exception;

import java.io.Serial;

public class UtilException extends RuntimeException {

  /** 序列化uid */
  @Serial private static final long serialVersionUID = 8247610319171014183L;

  /**
   * 工具类异常
   *
   * @param e e
   * @author DB
   * @since 2024/05/23
   */
  public UtilException(Throwable e) {
    super(e.getMessage(), e);
  }

  /**
   * 工具类异常
   *
   * @param message 消息
   * @author DB
   * @since 2024/05/23
   */
  public UtilException(String message) {
    super(message);
  }

  /**
   * 工具类异常
   *
   * @param message 消息
   * @param throwable 异常
   * @author DB
   * @since 2024/05/23
   */
  public UtilException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
