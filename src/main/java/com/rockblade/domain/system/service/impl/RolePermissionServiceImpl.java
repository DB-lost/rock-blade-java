package com.rockblade.domain.system.service.impl;

import org.springframework.stereotype.Service;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.RolePermission;
import com.rockblade.domain.system.service.RolePermissionService;
import com.rockblade.infrastructure.mapper.RolePermissionMapper;

/**
 * 角色和菜单关联表 服务层实现。
 *
 * @author
 * @since 2025-05-12
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService {}
