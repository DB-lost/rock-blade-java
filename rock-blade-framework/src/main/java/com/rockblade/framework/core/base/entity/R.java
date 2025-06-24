/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:33:50
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/core/base/entity/R.java
 * @Description: 响应信息主体
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.base.entity;

import java.io.Serial;
import java.io.Serializable;

import com.rockblade.common.constants.HttpStatus;

import lombok.Getter;

@Getter
public class R<T> implements Serializable {

  @Serial private static final long serialVersionUID = 1L;

  /** 成功 */
  public static final int SUCCESS = HttpStatus.SUCCESS;

  /** 失败 */
  public static final int FAIL = HttpStatus.ERROR;

  /** 代码 */
  private int code;

  /** 消息 */
  private String message;

  /** 结果 */
  private T data;

  /**
   * 成功
   *
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> ok() {
    return restData(null, SUCCESS, "成功");
  }

  /**
   * 成功
   *
   * @param data 结果
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> ok(T data) {
    return restData(data, SUCCESS, "成功");
  }

  /**
   * 成功
   *
   * @param data 结果
   * @param message 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> ok(T data, String message) {
    return restData(data, SUCCESS, message);
  }

  /**
   * 失败
   *
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail() {
    return restData(null, FAIL, "失败");
  }

  /**
   * 失败
   *
   * @param message 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(String message) {
    return restData(null, FAIL, message);
  }

  /**
   * 失败
   *
   * @param data 结果
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(T data) {
    return restData(data, FAIL, "失败");
  }

  /**
   * 失败
   *
   * @param data 结果
   * @param message 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(T data, String message) {
    return restData(data, FAIL, message);
  }

  /**
   * 失败
   *
   * @param code 代码
   * @param message 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(int code, String message) {
    return restData(null, code, message);
  }

  /**
   * 其他结果
   *
   * @param data 结果
   * @param code 代码
   * @param message 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  private static <T> R<T> restData(T data, int code, String message) {
    R<T> apiData = new R<>();
    apiData.setCode(code);
    apiData.setData(data);
    apiData.setMessage(message);
    return apiData;
  }

  /**
   * 设置代码
   *
   * @param code 代码
   * @author DB
   * @since 2024/05/23
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * 设置消息
   *
   * @param message 消息
   * @author DB
   * @since 2024/05/23
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * 设置结果
   *
   * @param data 结果
   * @author DB
   * @since 2024/05/23
   */
  public void setData(T data) {
    this.data = data;
  }
}
