/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:26:39
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:15:01
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/constants/RedisKey.java
 * @Description: Redis键
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.constants;

/**
 * Redis键
 *
 * @author DB
 * @version 1.0.0
 * @since 2024/05/23
 */
public interface RedisKey {

  /** 验证码 */
  String CAPTCHA_KEY = "sys:sys-login:captcha:";

  /** redis分布式锁key */
  String DISTRIBUTED_LOCK = "sys:redis:distributed-lock:";

  /** 验证码过期时间（分钟） */
  int CAPTCHA_EXPIRE_MINUTES = 5;

  /** 邮箱注册验证码 */
  String EMAIL_REGISTER_CODE = "sys:email:register-code:";

  /** 邮箱重置密码验证码 */
  String EMAIL_RESET_CODE = "sys:email:reset-code:";

  /** RSA私钥 */
  String RSA_PRIVATE_KEY = "sys:rsa:private-key:";
}
