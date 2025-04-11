package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.UserRole;
import com.rockblade.infrastructure.mapper.UserRoleMapper;
import com.rockblade.domain.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表 服务层实现。
 *
 * @author 
 * @since 2025-04-11
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
