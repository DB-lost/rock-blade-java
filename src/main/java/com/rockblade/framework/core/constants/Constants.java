/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:26:39
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-15 16:34:46
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/constants/Constants.java
 * @Description: 实体类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.constants;

public interface Constants {

  /** UTF-8 字符集 */
  String UTF8 = "UTF-8";

  /** contentType json/utf-8 */
  String CONTENT_TYPE_JSON_UTF8 = "application/json;charset=utf-8";

  /** GBK 字符集 */
  String GBK = "GBK";

  /** http请求 */
  String HTTP = "http://";

  /** https请求 */
  String HTTPS = "https://";

  /** 登录成功 */
  String LOGIN_SUCCESS = "Success";

  /** 注销 */
  String LOGOUT = "Logout";

  /** 注册 */
  String REGISTER = "Register";

  /** 登录失败 */
  String LOGIN_FAIL = "Error";

  /** 验证码 redis key */
  String CAPTCHA_CODE_KEY = "captcha_codes:";

  /** 登录用户 redis key */
  String LOGIN_TOKEN_KEY = "login_tokens:";

  /** 防重提交 redis key */
  String REPEAT_SUBMIT_KEY = "repeat_submit:";

  /** 限流 redis key */
  String RATE_LIMIT_KEY = "rate_limit:";

  /** 验证码有效期（分钟） */
  Integer CAPTCHA_EXPIRATION = 2;

  /** 令牌 */
  String TOKEN = "token";

  /** 令牌前缀 */
  String TOKEN_PREFIX = "Bearer ";

  /** 用户名称 */
  // String JWT_USERNAME = Claims.SUBJECT;

  /** 用户头像 */
  String JWT_AVATAR = "avatar";

  /** 用户权限 */
  String JWT_AUTHORITIES = "authorities";

  /** 参数管理 cache key */
  String CONFIG_KEY = "config:";

  /** 字典管理 cache key */
  String SYS_DICT_KEY = "sys_dict:";

  /** 资源映射路径 前缀 */
  String RESOURCE_PREFIX = "/profile";

  /** RMI 远程方法调用 */
  String LOOKUP_RMI = "rmi://";

  /** LDAP 远程方法调用 */
  String LOOKUP_LDAP = "ldap://";

  /** 定时任务违规的字符 */
  String[] JOB_ERROR_STR = {
    "java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml", "org.springframework.jndi"
  };

  /** 超级管理员 */
  String SUPER_ADMIN = "RockBlade";

  /** 超级管理员ID */
  String SUPER_ADMIN_ID = "100";

  /** 超级身份id */
  String SUPER_IDENTITY_AUTH_ID = "100";

  /** 所有权限 */
  String ALL_PERMISSION = "*";

  /** 隐藏菜单 */
  String HIDE_MENU = "System,Menu,Dict,Role";

  /** 用户类型 */
  String USER_TYPE = "UserType";

  /** 登陆查询用户字段 */
  String USERNAME = "Username";

  /** 二维码Base64头 */
  String QRCODE_HEADER = "data:image/png;base64,";

  /** 默认文件名长度 */
  Integer DEFAULT_FILE_NAME_LENGTH = 100;

  /** 默认大小 50M */
  Long DEFAULT_MAX_SIZE = 50L * 1024 * 1024;

  /** png图像 */
  String IMAGE_PNG = "image/png";

  /** jpg图像 */
  String IMAGE_JPG = "image/jpg";

  /** jpeg图像 */
  String IMAGE_JPEG = "image/jpeg";

  /** bmp图像 */
  String IMAGE_BMP = "image/bmp";

  /** gif图像 */
  String IMAGE_GIF = "image/gif";

  /** 映像扩展 */
  String[] IMAGE_EXTENSION = {"bmp", "gif", "jpg", "jpeg", "png"};

  /** flash扩展 */
  String[] FLASH_EXTENSION = {"swf", "flv"};

  /** 媒体扩展 */
  String[] MEDIA_EXTENSION = {
    "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg", "asf", "rm", "rmvb"
  };

  /** 视频扩展 */
  String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};

  /** 默认允许扩展 */
  String[] DEFAULT_ALLOWED_EXTENSION = {
    // 图片
    "bmp",
    "gif",
    "jpg",
    "jpeg",
    "png",
    // word excel powerpoint
    "doc",
    "docx",
    "xls",
    "xlsx",
    "ppt",
    "pptx",
    "html",
    "htm",
    "txt",
    // 压缩文件
    "rar",
    "zip",
    "gz",
    "bz2",
    // 视频格式
    "mp4",
    "avi",
    "rmvb",
    // pdf
    "pdf"
  };
}
