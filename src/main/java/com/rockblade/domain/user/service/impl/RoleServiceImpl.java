package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.Role;
import com.rockblade.infrastructure.mapper.RoleMapper;
import com.rockblade.domain.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务层实现。
 *
 * @author 
 * @since 2025-04-11
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
