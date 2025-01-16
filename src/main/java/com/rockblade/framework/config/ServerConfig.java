/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 19:48:49
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 19:49:08
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/config/ServerConfig.java
 * @Description: 服务相关配置
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.framework.config;

import com.rockblade.framework.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig {

    /**
     * 获取完整的请求路径，包括：域名，端口，上下文访问路径
     *
     * @return 服务地址
     * @author DB
     * @since 2024/05/23
     */
    public String getUrl() {
        HttpServletRequest request = ServletUtils.getRequest();
        return getDomain(request);
    }

    /**
     * 得到域
     *
     * @param request 请求
     * @return {@link String }
     * @author DB
     * @since 2024/05/23
     */
    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextPath = request.getServletContext().getContextPath();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
    }
}
