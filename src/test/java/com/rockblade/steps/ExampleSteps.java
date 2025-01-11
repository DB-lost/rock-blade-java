/*
 * Copyright (c) 2025 RockBlade
 */
package com.rockblade.steps;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/** 示例步骤定义类 */
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
