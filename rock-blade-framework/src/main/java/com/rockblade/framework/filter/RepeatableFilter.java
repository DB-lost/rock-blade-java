/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 19:49:34
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:29:32
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/filter/RepeatableFilter.java
 * @Description: 可重复过滤器
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RepeatableFilter extends SaServletFilter {

  @Override
  public void init(FilterConfig filterConfig) {
  }

  /**
   * 做过滤器
   *
   * @param request  请求
   * @param response 响应
   * @param chain    链
   * @throws IOException ioexception
   * @author LOST
   * @since 2024/05/24
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      this.beforeAuth.run((Object) null);
      SaRouter.match(this.includeList)
          .notMatch(this.excludeList)
          .check(
              (r) -> {
                this.auth.run((Object) null);
              });
    } catch (StopMatchException ignored) {
    } catch (Throwable var7) {
      String result = var7 instanceof BackResultException
          ? var7.getMessage()
          : String.valueOf(this.error.run(var7));
      if (response.getContentType() == null) {
        response.setContentType("text/plain; charset=utf-8");
      }
      response.getWriter().print(result);
    }
    // 流可重复
    ServletRequest requestWrapper = null;
    if (request instanceof HttpServletRequest
        && StrUtil.startWithIgnoreCase(
            request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
      requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
    }
    if (null == requestWrapper) {
      chain.doFilter(request, response);
    } else {
      chain.doFilter(requestWrapper, response);
    }
  }

  @Override
  public void destroy() {
  }
}
