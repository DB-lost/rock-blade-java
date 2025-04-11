/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 20:49:05
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 09:39:19
 * @FilePath: /rock-blade-java/src/test/java/com/rockblade/RockBladeWebApplicationTests.java
 * @Description: 测试类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade;

import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.rockblade.framework.config.RockBladeConfig;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

@SpringBootTest
@Profile("dev")
class RockBladeWebApplicationTests {

  @Autowired
  private RockBladeConfig rockBladeConfig;

  /** 初始化密钥对 */
  @Test
  public void initKeyPair() {
    RSA loginRsa = rockBladeConfig.getLoginRsa();
    PublicKey publicKey = loginRsa.getPublicKey();
    PrivateKey privateKey = loginRsa.getPrivateKey();
    System.out.println("公钥：");
    System.out.println(Base64.encodeStr(publicKey.getEncoded(), true, false));
    System.out.println();
    System.out.println("私钥：");
    System.out.println(Base64.encodeStr(privateKey.getEncoded(), true, false));
    System.out.println();
    String source = "Admin@123";
    System.out.println("待加密字符串：" + source);
    System.out.println();
    String strEncrypt = loginRsa.encryptBase64(source, KeyType.PublicKey);
    System.out.println("加密后的字符串：");
    System.out.println(strEncrypt);
    System.out.println();
    String strDecrypt = loginRsa.decryptStr(strEncrypt, KeyType.PrivateKey);
    System.out.println("解密后的字符串：");
    System.out.println(strDecrypt);
  }

  /**
   * 密码加密
   *
   * @author DB
   * @since 2024/3/22
   */
  @Test
  public void getPassword() {
    RSA loginRsa = rockBladeConfig.getLoginRsa();
    String encrypt = loginRsa.encryptBase64("Admin@123", KeyType.PublicKey);
    System.out.println(encrypt);
  }
}
