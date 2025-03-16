package com.rockblade.framework.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;

/** IP地址工具类 */
public class IpUtils {

  private static final String UNKNOWN = "unknown";
  private static final String LOCALHOST = "127.0.0.1";
  private static final String SEPARATOR = ",";

  public static String getIpAddr() {
    HttpServletRequest request = getRequest();
    if (request == null) {
      return UNKNOWN;
    }

    String ip = request.getHeader("x-forwarded-for");
    if (isEmptyIP(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (isEmptyIP(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (isEmptyIP(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (isEmptyIP(ip)) {
      ip = request.getHeader("X-Real-IP");
    }
    if (isEmptyIP(ip)) {
      ip = request.getRemoteAddr();
    }

    return StrUtil.equals(LOCALHOST, ip) ? LOCALHOST : getMultistageReverseProxyIp(ip);
  }

  /** 获取当前请求对象 */
  private static HttpServletRequest getRequest() {
    try {
      RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
      if (requestAttributes == null) {
        return null;
      }
      return ((ServletRequestAttributes) requestAttributes).getRequest();
    } catch (Exception e) {
      return null;
    }
  }

  /** 检查是否为空IP */
  private static boolean isEmptyIP(String ip) {
    return StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip);
  }

  /** 从多级反向代理中获取第一个非unknown IP地址 */
  private static String getMultistageReverseProxyIp(String ip) {
    if (ip != null && ip.indexOf(SEPARATOR) > 0) {
      String[] ips = ip.trim().split(SEPARATOR);
      for (String subIp : ips) {
        if (!isEmptyIP(subIp)) {
          ip = subIp;
          break;
        }
      }
    }
    return ip;
  }
}
