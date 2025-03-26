/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-03-26 08:22:22
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-03-26 08:22:28
 * @FilePath: /rock-blade-java/src/test/java/com/rockblade/framework/BaseTest.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.framework;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 测试基类
 *
 * @author DB
 * @since 2025/3/26
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseTest {
    // 可以添加通用的测试工具方法
}
