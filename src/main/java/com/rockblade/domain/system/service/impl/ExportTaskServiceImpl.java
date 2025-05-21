package com.rockblade.domain.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.ExportTask;
import com.rockblade.infrastructure.mapper.ExportTaskMapper;
import com.rockblade.domain.system.service.ExportTaskService;
import org.springframework.stereotype.Service;

/**
 * 数据导出任务表 服务层实现。
 *
 * @author 
 * @since 2025-05-21
 */
@Service("exportTaskService")
public class ExportTaskServiceImpl extends ServiceImpl<ExportTaskMapper, ExportTask> implements ExportTaskService {

}
