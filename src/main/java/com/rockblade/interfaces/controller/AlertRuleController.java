package com.rockblade.interfaces.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybatisflex.core.paginate.Page;
import com.rockblade.domain.system.entity.AlertRule;
import com.rockblade.domain.system.service.AlertRuleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 告警规则表 控制层。
 *
 * @author
 * @since 2025-05-21
 */
@RestController
@Tag(name = "告警规则表接口")
@RequestMapping("/alertRule")
public class AlertRuleController {

  @Autowired private AlertRuleService alertRuleService;

  /**
   * 分页查询告警规则表。
   *
   * @param page 分页对象
   * @return 分页对象
   */
  @GetMapping("/page")
  @Operation(summary = "分页查询告警规则表")
  public Page<AlertRule> page(@Parameter(description = "分页信息") Page<AlertRule> page) {
    return alertRuleService.page(page);
  }

  /**
   * 添加告警规则表。
   *
   * @param alertRule 告警规则表
   * @return {@code true} 添加成功，{@code false} 添加失败
   */
  @PostMapping("/save")
  @Operation(summary = "保存告警规则表")
  public boolean save(@RequestBody @Parameter(description = "告警规则表") AlertRule alertRule) {
    return alertRuleService.save(alertRule);
  }

  /**
   * 根据主键删除告警规则表。
   *
   * @param id 主键
   * @return {@code true} 删除成功，{@code false} 删除失败
   */
  @DeleteMapping("/remove/{id}")
  @Operation(summary = "根据主键告警规则表")
  public boolean remove(@PathVariable @Parameter(description = "告警规则表主键") Serializable id) {
    return alertRuleService.removeById(id);
  }

  /**
   * 根据主键更新告警规则表。
   *
   * @param alertRule 告警规则表
   * @return {@code true} 更新成功，{@code false} 更新失败
   */
  @PutMapping("/update")
  @Operation(summary = "根据主键更新告警规则表")
  public boolean update(@RequestBody @Parameter(description = "告警规则表主键") AlertRule alertRule) {
    return alertRuleService.updateById(alertRule);
  }

  /**
   * 查询所有告警规则表。
   *
   * @return 所有数据
   */
  @GetMapping("/list")
  @Operation(summary = "查询所有告警规则表")
  public List<AlertRule> list() {
    return alertRuleService.list();
  }

  /**
   * 根据告警规则表主键获取详细信息。
   *
   * @param id 告警规则表主键
   * @return 告警规则表详情
   */
  @GetMapping("/getInfo/{id}")
  @Operation(summary = "根据主键获取告警规则表")
  public AlertRule getInfo(@PathVariable Serializable id) {
    return alertRuleService.getById(id);
  }
}
