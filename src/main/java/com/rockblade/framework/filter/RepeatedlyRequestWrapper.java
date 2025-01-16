/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 19:49:34
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 23:08:01
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/filter/RepeatedlyRequestWrapper.java
 * @Description: 重复请求包装器
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.filter;

import java.io.*;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepeatedlyRequestWrapper extends HttpServletRequestWrapper {
  /** 请求体 */
  private final byte[] body;

  /**
   * 重复请求包装器
   *
   * @param request 请求
   * @param response 响应
   * @throws IOException ioexception
   * @author DB
   * @since 2024/05/24
   */
  public RepeatedlyRequestWrapper(HttpServletRequest request, ServletResponse response)
      throws IOException {
    super(request);
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    body = getBodyString(request).getBytes(StandardCharsets.UTF_8);
  }

  /**
   * 获取主体字符串
   *
   * @param request 请求
   * @return {@link String }
   * @author DB
   * @since 2024/05/24
   */
  private String getBodyString(ServletRequest request) {
    StringBuilder sb = new StringBuilder();
    BufferedReader reader = null;
    try (InputStream inputStream = request.getInputStream()) {
      reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
      String line = "";
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      log.warn("getBodyString出现问题！");
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          log.error("关闭流失败: {}", e.getMessage());
        }
      }
    }
    return sb.toString();
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream()));
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    final ByteArrayInputStream bais = new ByteArrayInputStream(body);
    return new ServletInputStream() {
      @Override
      public int read() throws IOException {
        return bais.read();
      }

      @Override
      public int available() throws IOException {
        return body.length;
      }

      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener readListener) {}
    };
  }
}
