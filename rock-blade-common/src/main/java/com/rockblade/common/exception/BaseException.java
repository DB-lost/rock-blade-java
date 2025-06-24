/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:30:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:09:28
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/exception/BaseException.java
 * @Description: 基础异常 
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.exception;

import java.io.Serial;

import com.rockblade.common.utils.MessageUtils;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  /** 序列化uid */
  @Serial
  private static final long serialVersionUID = 1L;

  /** 所属模块 */
  private final String module;

  /** 错误码 */
  private final String code;

  /** 错误码对应的参数 */
  private final Object[] args;

  /** 错误消息 */
  private final String defaultMessage;

  /**
   * 基础异常构造器
   *
   * @param module         模块
   * @param code           代码
   * @param args           参数
   * @param defaultMessage 默认消息
   * @author DB
   * @since 2024/05/23
   */
  public BaseException(String module, String code, Object[] args, String defaultMessage) {
    this.module = module;
    this.code = code;
    this.args = args;
    this.defaultMessage = defaultMessage;
  }

  /**
   * 基础异常构造器
   *
   * @param module 模块
   * @param code   代码
   * @param args   参数
   * @author DB
   * @since 2024/05/23
   */
  public BaseException(String module, String code, Object[] args) {
    this(module, code, args, null);
  }

  /**
   * 基础异常构造器
   *
   * @param module         模块
   * @param defaultMessage 默认消息
   * @author DB
   * @since 2024/05/23
   */
  public BaseException(String module, String defaultMessage) {
    this(module, null, null, defaultMessage);
  }

  /**
   * 基础异常构造器
   *
   * @param code 代码
   * @param args 参数
   * @author DB
   * @since 2024/05/23
   */
  public BaseException(String code, Object[] args) {
    this(null, code, args, null);
  }

  /**
   * 基础异常构造器
   *
   * @param defaultMessage 默认消息
   * @author DB
   * @since 2024/05/23
   */
  public BaseException(String defaultMessage) {
    this(null, null, null, defaultMessage);
  }

  @Override
  public String getMessage() {
    String message = null;
    if (!StrUtil.isEmpty(code)) {
      message = MessageUtils.message(code, args);
    }
    if (message == null) {
      message = defaultMessage;
    }
    return message;
  }
}
