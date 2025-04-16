package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.Dept;
import com.rockblade.infrastructure.mapper.DeptMapper;
import com.rockblade.domain.user.service.DeptService;
import org.springframework.stereotype.Service;

/**
 * 部门表 服务层实现。
 *
 * @author 
 * @since 2025-04-16
 */
@Service("deptService")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

}
