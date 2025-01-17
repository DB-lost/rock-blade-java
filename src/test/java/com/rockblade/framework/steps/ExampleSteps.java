/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 20:49:05
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-17 22:47:29
 * @FilePath: /rock-blade-java/src/test/java/com/rockblade/framework/steps/ExampleSteps.java
 * @Description: 示例步骤定义类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.steps;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@SpringBootTest
public class ExampleSteps {

  @Given("用户准备进行操作")
  public void userPreparesForAction() {
    // 实现步骤
  }

  @When("用户执行某个动作")
  public void userPerformsAction() {
    // 实现步骤
  }

  @Then("系统应该返回预期结果")
  public void systemShouldReturnExpectedResult() {
    // 实现步骤
  }
}
