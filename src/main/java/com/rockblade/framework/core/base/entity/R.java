/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 09:03:17
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/base/entity/R.java
 * @Description: 响应信息主体
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.base.entity;

import java.io.Serial;
import java.io.Serializable;

import com.rockblade.framework.core.constants.HttpStatus;

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
  private String msg;

  /** 结果 */
  private T result;

  /**
   * 成功
   *
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> ok() {
    return restResult(null, SUCCESS, "成功");
  }

  /**
   * 成功
   *
   * @param result 结果
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> ok(T result) {
    return restResult(result, SUCCESS, "成功");
  }

  /**
   * 成功
   *
   * @param result 结果
   * @param msg 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> ok(T result, String msg) {
    return restResult(result, SUCCESS, msg);
  }

  /**
   * 失败
   *
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail() {
    return restResult(null, FAIL, "失败");
  }

  /**
   * 失败
   *
   * @param msg 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(String msg) {
    return restResult(null, FAIL, msg);
  }

  /**
   * 失败
   *
   * @param result 结果
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(T result) {
    return restResult(result, FAIL, "失败");
  }

  /**
   * 失败
   *
   * @param result 结果
   * @param msg 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(T result, String msg) {
    return restResult(result, FAIL, msg);
  }

  /**
   * 失败
   *
   * @param code 代码
   * @param msg 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  public static <T> R<T> fail(int code, String msg) {
    return restResult(null, code, msg);
  }

  /**
   * 其他结果
   *
   * @param result 结果
   * @param code 代码
   * @param msg 消息
   * @return {@link R }<{@link T }>
   * @author DB
   * @since 2024/05/23
   */
  private static <T> R<T> restResult(T result, int code, String msg) {
    R<T> apiResult = new R<>();
    apiResult.setCode(code);
    apiResult.setResult(result);
    apiResult.setMsg(msg);
    return apiResult;
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
   * @param msg 消息
   * @author DB
   * @since 2024/05/23
   */
  public void setMsg(String msg) {
    this.msg = msg;
  }

  /**
   * 设置结果
   *
   * @param result 结果
   * @author DB
   * @since 2024/05/23
   */
  public void setResult(T result) {
    this.result = result;
  }
}
