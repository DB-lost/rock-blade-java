/*
 * Copyright (c) 2025 RockBlade
 */
package com.rockblade;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.*;
import org.springframework.boot.test.context.SpringBootTest;

/** Cucumber测试运行器 集成Spring Boot测试环境 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.rockblade.steps")
@SpringBootTest
public class CucumberRunner {}
