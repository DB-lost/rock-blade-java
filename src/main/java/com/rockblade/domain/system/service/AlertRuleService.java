/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-20 18:57:20
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-21 11:12:56
 * @FilePath: /rock-blade-ITOM-Backstage/home/db/WorkSpace/Template-WorkSpace/rock-blade-java/src/main/java/com/rockblade/domain/system/service/AlertRuleService.java
 * @Description: 告警规则表 服务层。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.service;

import com.mybatisflex.core.service.IService;
import com.rockblade.domain.system.entity.AlertRule;

public interface AlertRuleService extends IService<AlertRule> {

  /**
   * 根据监控指标和启用状态查找告警规则
   *
   * @param metric 监控指标
   * @param enabled 是否启用
   * @return AlertRule
   */
  AlertRule findByMetricAndEnabled(String metric, boolean enabled);
}
