/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:51:34
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-03-24 10:50:04
 * @FilePath: /rock-blade-AI-java/home/db/Workspace/Template-Workspace/rock-blade-java/src/main/java/com/rockblade/framework/config/RockBladeConfig.java
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

      /** 公钥文件 */
      private String publicKeyFile;

      /** 私钥文件 */
      private String privateKeyFile;
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

  /**
   * 获取登录rsa
   *
   * @return {@link RSA }
   * @author DB
   * @since 2024/05/23
   */
  public RSA getLoginRsa() {
    // rsa解密
    RockBladeConfig.Gateway.RsaKeypair rsaKeypair = gateway.getRsaKeypair();
    Map<String, String> keyPair = getKeyPair(rsaKeypair.getPublicKeyFile(), rsaKeypair.getPrivateKeyFile());
    return SecureUtil.rsa(keyPair.get("privateKey"), keyPair.get("publicKey"));
  }

  /**
   * 获取密钥对
   *
   * @param publicKeyFile  公钥文件
   * @param privateKeyFile 私钥文件
   * @return {@link Map }<{@link String }, {@link String }>
   * @author DB
   * @since 2024/05/23
   */
  private Map<String, String> getKeyPair(String publicKeyFile, String privateKeyFile) {
    Map<String, String> keyPairMap = new HashMap<>(2);
    File publicFile = new File(publicKeyFile);
    File privateFile = new File(privateKeyFile);
    // 判断是否存在公钥和私钥文件
    if (!publicFile.exists() || !privateFile.exists()) {
      generateKeyPairToFiles(publicKeyFile, privateKeyFile);
    }
    ObjectInputStream oisPublic = null;
    ObjectInputStream oisPrivate = null;
    Key publicKey = null;
    Key privateKey = null;
    try {
      oisPublic = new ObjectInputStream(Files.newInputStream(Paths.get(publicKeyFile)));
      oisPrivate = new ObjectInputStream(Files.newInputStream(Paths.get(privateKeyFile)));
      publicKey = (Key) oisPublic.readObject();
      privateKey = (Key) oisPrivate.readObject();
      String publicKeyBase64 = Base64.encodeStr(publicKey.getEncoded(), true, false);
      String privateKeyBase64 = Base64.encodeStr(privateKey.getEncoded(), true, false);
      // 公钥字符串
      keyPairMap.put("publicKey", publicKeyBase64);
      // 私钥字符串
      keyPairMap.put("privateKey", privateKeyBase64);
    } catch (IOException | ClassNotFoundException e) {
      throw new UtilException(e.getMessage());
    } finally {
      try {
        assert oisPrivate != null;
        oisPrivate.close();
        oisPublic.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return keyPairMap;
  }

  /**
   * 生成文件密钥对
   *
   * @param publicKeyFile  公钥文件
   * @param privateKeyFile 私钥文件
   * @author DB
   * @since 2024/05/23
   */
  private void generateKeyPairToFiles(String publicKeyFile, String privateKeyFile) {
    ObjectOutputStream oosPublicKey = null;
    ObjectOutputStream oosPrivateKey = null;
    try {
      Map<String, Key> keyPairMap = generateKeyPair();
      Key publicKey = keyPairMap.get("publicKey");
      Key privateKey = keyPairMap.get("privateKey");
      oosPublicKey = new ObjectOutputStream(Files.newOutputStream(Paths.get(publicKeyFile)));
      oosPrivateKey = new ObjectOutputStream(Files.newOutputStream(Paths.get(privateKeyFile)));
      oosPublicKey.writeObject(publicKey);
      oosPrivateKey.writeObject(privateKey);
    } catch (NoSuchAlgorithmException | IOException e) {
      throw new UtilException(e.getMessage());
    } finally {
      try {
        // 清空缓存，关闭文件输出流
        assert oosPublicKey != null;
        oosPublicKey.close();
        assert oosPrivateKey != null;
        oosPrivateKey.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 生成密钥对
   *
   * @return {@link Map }<{@link String }, {@link Key }>
   * @throws NoSuchAlgorithmException 生成失败异常
   * @author DB
   * @since 2024/05/23
   */
  private Map<String, Key> generateKeyPair() throws NoSuchAlgorithmException {
    KeyPair keyPair = SecureUtil.generateKeyPair(
        gateway.getRsaKeypair().getAlgorithm(), gateway.getRsaKeypair().getKeySize());
    // 得到公钥
    Key publicKey = keyPair.getPublic();
    // 得到私钥
    Key privateKey = keyPair.getPrivate();
    Map<String, Key> keyPairMap = new HashMap<>(2);
    keyPairMap.put("publicKey", publicKey);
    keyPairMap.put("privateKey", privateKey);
    return keyPairMap;
  }
}
