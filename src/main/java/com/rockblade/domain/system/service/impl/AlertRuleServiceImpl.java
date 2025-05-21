package com.rockblade.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.AlertRule;
import com.rockblade.domain.system.service.AlertRuleService;
import com.rockblade.infrastructure.mapper.AlertRuleMapper;

/**
 * 告警规则表 服务层实现。
 *
 * @author
 * @since 2025-05-21
 */
@Service("alertRuleService")
public class AlertRuleServiceImpl extends ServiceImpl<AlertRuleMapper, AlertRule>
    implements AlertRuleService {}
