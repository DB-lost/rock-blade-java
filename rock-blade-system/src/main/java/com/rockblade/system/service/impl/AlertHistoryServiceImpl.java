/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-06-24 13:00:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:50:33
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/AlertHistoryServiceImpl.java
 * @Description: 告警历史记录表 服务层实现。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.system.entity.AlertHistory;
import com.rockblade.system.service.AlertHistoryService;
import com.rockblade.infrastructure.system.mapper.AlertHistoryMapper;

@Service("alertHistoryService")
public class AlertHistoryServiceImpl extends ServiceImpl<AlertHistoryMapper, AlertHistory>
        implements AlertHistoryService {
}
