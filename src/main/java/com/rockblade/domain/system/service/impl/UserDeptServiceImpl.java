package com.rockblade.domain.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.UserDept;
import com.rockblade.infrastructure.mapper.UserDeptMapper;
import com.rockblade.domain.system.service.UserDeptService;
import org.springframework.stereotype.Service;

/**
 * 用户和部门关联表 服务层实现。
 *
 * @author 
 * @since 2025-04-16
 */
@Service("userDeptService")
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService {

}
