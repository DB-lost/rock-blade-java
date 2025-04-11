package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.RoleMenu;
import com.rockblade.infrastructure.mapper.RoleMenuMapper;
import com.rockblade.domain.user.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表 服务层实现。
 *
 * @author 
 * @since 2025-04-11
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
