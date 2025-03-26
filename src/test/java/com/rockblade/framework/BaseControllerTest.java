/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-03-26 08:22:34
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-03-26 08:22:57
 * @FilePath: /rock-blade-java/src/test/java/com/rockblade/framework/BaseControllerTest.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.framework;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * Controller测试基类，提供MockMvc和RestAssured支持
 *
 * @author DB
 * @since 2025/3/26
 */
public abstract class BaseControllerTest extends BaseTest {

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();
        RestAssuredMockMvc.webAppContextSetup(context);

        // 配置REST Assured
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/rock-blade";
    }
}
