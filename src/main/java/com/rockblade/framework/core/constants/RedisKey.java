/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:26:39
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-15 21:29:46
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/constants/RedisKey.java
 * @Description: Redis键
 * 
 * Copyright (c) 2025 by DB, All Rights Reserved. 
 */
package com.rockblade.framework.core.constants;

/**
 * Redis键
 *
 * @author DB
 * @version 1.0.0
 * @since 2024/05/23
 */
public interface RedisKey {

    /**
     * 验证码
     */
    String CAPTCHA_KEY = "sys:sys-login:captcha:";

    /**
     * redis分布式锁key
     */
    String DISTRIBUTED_LOCK = "sys:redis:distributed-lock:";

}
