package com.rockblade.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.AlertHistory;
import com.rockblade.domain.system.service.AlertHistoryService;
import com.rockblade.infrastructure.system.mapper.AlertHistoryMapper;

/**
 * 告警历史记录表 服务层实现。
 *
 * @author
 * @since 2025-05-21
 */
@Service("alertHistoryService")
public class AlertHistoryServiceImpl extends ServiceImpl<AlertHistoryMapper, AlertHistory>
    implements AlertHistoryService {}
