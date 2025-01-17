/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-17 22:51:52
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-17 22:52:38
 * @FilePath: /rock-blade-java/src/test/java/com/rockblade/framework/CucumberRunner.java
 * @Description: 文件描述
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
/*
 * Copyright (c) 2025 RockBlade
 */
package com.rockblade.framework;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.*;
import org.springframework.boot.test.context.SpringBootTest;

/** Cucumber测试运行器 集成Spring Boot测试环境 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.rockblade.framework.steps")
@SpringBootTest
public class CucumberRunner {}
