/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:51:34
 * @LastEditors: error: error: git config user.name & please set dead value or install git && error: git config user.email & please set dead value or install git & please set dead value or install git
 * @LastEditTime: 2025-04-23 23:12:52
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/config/RockBladeConfig.java
 * @Description: rock-blade-java配置
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.DependsOn;

import com.rockblade.framework.core.base.exception.UtilException;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.Data;
import lombok.Getter;

@Getter
@Component
@DependsOn("envConfig")
@ConfigurationProperties(prefix = "rock-blade")
public class RockBladeConfig {

  /** 项目名称 */
  private String name;

  /** 版本 */
  private String version;

  /** 上传路径 */
  private static String profile;

  /** 验证码类型 */
  @Getter
  private static String captchaType;

  /** jwt */
  private Jwt jwt;

  /**
   * jwt设置
   *
   * @param jwt jwt
   * @author DB
   * @since 2024/05/23
   */
  public void setJwt(Jwt jwt) {
    this.jwt = jwt;
  }

  /**
   * jwt
   *
   * @author DB
   * @version 1.0.0
   * @since 2024/05/23
   */
  @Data
  public static class Jwt {

    /** 白名单 */
    private String whiteList;
  }

  /** 网关配置 */
  @Getter
  private Gateway gateway;

  /**
   * 设置网关
   *
   * @param gateway 网关
   * @author DB
   * @since 2024/05/23
   */
  public void setGateway(Gateway gateway) {
    this.gateway = gateway;
  }

  /**
   * 网关
   *
   * @author DB
   * @version 1.0.0
   * @since 2024/05/23
   */
  @Data
  public static class Gateway {

    /** rsa密钥对 */
    private RsaKeypair rsaKeypair;

    /**
     * rsa密钥对
     *
     * @author DB
     * @version 1.0.0
     * @since 2024/05/23
     */
    @Data
    public static class RsaKeypair {

      /** 算法 */
      private String algorithm;

      /** 关键尺寸 */
      private Integer keySize;

    }
  }

  /**
   * 设置名称
   *
   * @param name 名字
   * @author DB
   * @since 2024/05/23
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 设置版本
   *
   * @param version 版本
   * @author DB
   * @since 2024/05/23
   */
  public void setVersion(String version) {
    this.version = version;
  }

  /**
   * 获取配置文件
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String getProfile() {
    return profile;
  }

  /**
   * 设置配置文件
   *
   * @param profile 配置文件
   * @author DB
   * @since 2024/05/23
   */
  public void setProfile(String profile) {
    RockBladeConfig.profile = profile;
  }

  /**
   * 设置验证码类型
   *
   * @param captchaType 验证码类型
   * @author DB
   * @since 2024/05/23
   */
  public void setCaptchaType(String captchaType) {
    RockBladeConfig.captchaType = captchaType;
  }

  /**
   * 获取导入上传路径
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String getImportPath() {
    return getProfile() + "/import";
  }

  /**
   * 获取头像上传路径
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String getAvatarPath() {
    return getProfile() + "/avatar";
  }

  /**
   * 获取下载路径
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String getDownloadPath() {
    return getProfile() + "/download/";
  }

  /**
   * 获取上传路径
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String getUploadPath() {
    return getProfile() + "/upload";
  }

}
