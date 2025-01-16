/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:30:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 09:03:26
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/base/exception/ServiceException.java
 * @Description: 业务异常
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.base.exception;

import java.io.Serial;

public final class ServiceException extends RuntimeException {

  /** 序列化uid */
  @Serial private static final long serialVersionUID = 1L;

  /** 错误码 */
  private Integer code;

  /** 错误提示 */
  private String message;

  /** 错误明细，内部调试错误 */
  private String detailMessage;

  /** 空构造方法，避免反序列化问题 */
  public ServiceException() {}

  /**
   * 业务异常
   *
   * @param message 消息
   * @author DB
   * @since 2024/05/23
   */
  public ServiceException(String message) {
    this.message = message;
  }

  /**
   * 业务异常
   *
   * @param message 消息
   * @param code 代码
   * @author DB
   * @since 2024/05/23
   */
  public ServiceException(String message, Integer code) {
    this.message = message;
    this.code = code;
  }

  public String getDetailMessage() {
    return detailMessage;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public Integer getCode() {
    return code;
  }

  public ServiceException setMessage(String message) {
    this.message = message;
    return this;
  }

  public ServiceException setDetailMessage(String detailMessage) {
    this.detailMessage = detailMessage;
    return this;
  }
}
