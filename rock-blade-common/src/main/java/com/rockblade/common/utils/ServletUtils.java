/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:33:22
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:20:11
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/utils/ServletUtils.java
 * @Description: 客户端工具类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.utils;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ServletUtils {

  /**
   * 获取参数
   *
   * @param name 参数key
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public static String getParameter(String name) {
    return getRequest().getParameter(name);
  }

  /**
   * 获取参数
   *
   * @param name         参数key
   * @param defaultValue 默认值
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public static String getParameter(String name, String defaultValue) {
    return Convert.toStr(getRequest().getParameter(name), defaultValue);
  }

  /**
   * 获取Integer参数
   *
   * @param name 参数key
   * @return {@link Integer }
   * @author DB
   * @since 2024/05/23
   */
  public static Integer getParameterToInt(String name) {
    String parameter = getRequest().getParameter(name);
    if (StrUtil.isBlank(parameter)) {
      // 尝试从请求体中获取
      BufferedReader reader;
      try {
        reader = getRequest().getReader();
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          body.append(line);
        }
        String requestBody = body.toString();
        // 转json
        JSONObject json = JSONUtil.parseObj(requestBody);
        if (null == json) {
          return null;
        }
        parameter = json.getStr(name);
      } catch (IOException e) {
        throw new RuntimeException("获取分页参数失败!");
      }
    }
    return Convert.toInt(parameter);
  }

  /**
   * 获取Integer参数
   *
   * @param name         参数key
   * @param defaultValue 默认值
   * @return {@link Integer }
   * @author DB
   * @since 2024/05/23
   */
  public static Integer getParameterToInt(String name, Integer defaultValue) {
    return Convert.toInt(getRequest().getParameter(name), defaultValue);
  }

  /**
   * 获取Boolean参数
   *
   * @param name 参数key
   * @return {@link Boolean }
   * @author DB
   * @since 2024/05/23
   */
  public static Boolean getParameterToBool(String name) {
    return Convert.toBool(getRequest().getParameter(name));
  }

  /**
   * 获取Boolean参数
   *
   * @param name         参数key
   * @param defaultValue 默认值
   * @return {@link Boolean }
   * @author DB
   * @since 2024/05/23
   */
  public static Boolean getParameterToBool(String name, Boolean defaultValue) {
    return Convert.toBool(getRequest().getParameter(name), defaultValue);
  }

  /**
   * 获取request
   *
   * @return {@link HttpServletRequest }
   * @author DB
   * @since 2024/05/23
   */
  public static HttpServletRequest getRequest() {
    return getRequestAttributes().getRequest();
  }

  /**
   * 获取response
   *
   * @return {@link HttpServletResponse }
   * @author DB
   * @since 2024/05/23
   */
  public static HttpServletResponse getResponse() {
    return getRequestAttributes().getResponse();
  }

  /**
   * 获取session
   *
   * @return {@link HttpSession }
   * @author DB
   * @since 2024/05/23
   */
  public static HttpSession getSession() {
    return getRequest().getSession();
  }

  /**
   * 获取请求属性
   *
   * @return {@link ServletRequestAttributes }
   * @author DB
   * @since 2024/05/23
   */
  public static ServletRequestAttributes getRequestAttributes() {
    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
    return (ServletRequestAttributes) attributes;
  }

  /**
   * 将字符串渲染到客户端
   *
   * @param response 渲染对象
   * @param string   待渲染的字符串
   * @author DB
   * @since 2024/05/23
   */
  public static void renderString(HttpServletResponse response, String string) {
    try {
      response.setStatus(200);
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(string);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 是ajax请求
   *
   * @param request 请求
   * @return {@link Boolean }
   * @author DB
   * @since 2024/05/23
   */
  public static Boolean isAjaxRequest(HttpServletRequest request) {
    String accept = request.getHeader("accept");
    if (accept != null && accept.contains("application/json")) {
      return true;
    }

    String xRequestedWith = request.getHeader("X-Requested-With");
    if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
      return true;
    }

    String uri = request.getRequestURI();
    if (StrUtil.containsAnyIgnoreCase(uri, ".json", ".xml")) {
      return true;
    }

    String ajax = request.getParameter("__ajax");
    return StrUtil.containsAnyIgnoreCase(ajax, "json", "xml");
  }
}
