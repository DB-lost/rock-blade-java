/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-20 18:57:20
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-21 11:13:06
 * @FilePath: /rock-blade-ITOM-Backstage/home/db/WorkSpace/Template-WorkSpace/rock-blade-java/src/main/java/com/rockblade/domain/system/service/impl/AlertRuleServiceImpl.java
 * @Description: 告警规则表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.domain.system.service.impl;

import static com.rockblade.domain.system.entity.table.AlertRuleTableDef.ALERT_RULE;

import org.springframework.stereotype.Service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.AlertRule;
import com.rockblade.domain.system.service.AlertRuleService;
import com.rockblade.infrastructure.mapper.AlertRuleMapper;

@Service("alertRuleService")
public class AlertRuleServiceImpl extends ServiceImpl<AlertRuleMapper, AlertRule>
    implements AlertRuleService {

  @Override
  public AlertRule findByMetricAndEnabled(String metric, boolean enabled) {
    QueryWrapper queryWrapper =
        QueryWrapper.create()
            .where(ALERT_RULE.METRIC.eq(metric))
            .and(ALERT_RULE.ENABLED.eq(enabled))
            .and(ALERT_RULE.DELETED.eq(false));

    return this.getOne(queryWrapper);
  }
}
