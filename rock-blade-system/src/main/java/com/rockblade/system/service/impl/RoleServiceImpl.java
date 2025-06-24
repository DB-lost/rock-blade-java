/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:19:30
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/impl/RoleServiceImpl.java
 * @Description: 角色信息表 服务层实现。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service.impl;

import static com.rockblade.system.entity.table.RoleMenuTableDef.ROLE_MENU;
import static com.rockblade.system.entity.table.RoleTableDef.ROLE;
import static com.rockblade.system.entity.table.UserRoleTableDef.USER_ROLE;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.common.dto.system.request.RolePageRequest;
import com.rockblade.common.dto.system.request.RoleRequest;
import com.rockblade.common.dto.system.response.RoleResponse;
import com.rockblade.system.entity.Role;
import com.rockblade.system.entity.RoleMenu;
import com.rockblade.system.entity.UserRole;
import com.rockblade.system.service.RoleMenuService;
import com.rockblade.system.service.RoleService;
import com.rockblade.system.service.UserRoleService;
import com.rockblade.framework.core.base.entity.PageDomain;
import com.rockblade.common.exception.ServiceException;
import com.rockblade.framework.handler.SqlHandler;
import com.rockblade.system.mapper.RoleMapper;

import cn.hutool.extra.spring.SpringUtil;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

  @Autowired
  private SqlHandler sqlHandler;

  @Override
  public Role getRoleByKey(String roleKey) {
    return queryChain().where(ROLE.ROLE_KEY.eq(roleKey)).one();
  }

  @Override
  public Page<RoleResponse> getRolePage(RolePageRequest request) {
    // 执行分页查询
    PageDomain pageDomain = sqlHandler.getPageDomain();
    return this.mapper.paginateWithRelationsAs(
        Page.of(pageDomain.getPage(), pageDomain.getPageSize()),
        queryChain()
            .select(ROLE.ALL_COLUMNS)
            .and(ROLE.ROLE_NAME.eq(request.getRoleName()))
            .and(ROLE.STATUS.eq(request.getStatus()))
            .and(ROLE.REMARK.eq(request.getRemark()))
            .and(ROLE.CREATED_AT.between(request.getStartTime(), request.getEndTime()))
            .orderBy(ROLE.UPDATED_AT.desc()),
        RoleResponse.class,
        permissions -> permissions
            .field(RoleResponse::getPermissions)
            .queryWrapper(
                roleResponse -> QueryWrapper.create()
                    .select(ROLE_MENU.MENU_ID)
                    .from(ROLE_MENU)
                    .where(ROLE_MENU.ROLE_ID.eq(roleResponse.getId()))));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void createRole(RoleRequest request) {
    // 检查角色标识是否已存在
    Role existingRole = getRoleByKey(request.getRoleKey());
    if (existingRole != null) {
      throw new ServiceException("角色标识已存在");
    }

    // 创建角色
    Role role = new Role();
    BeanUtils.copyProperties(request, role);
    role.setDeleted(false);
    save(role);

    // 保存权限关系
    saveRoleMenus(role.getId(), request.getPermissions());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateRole(RoleRequest request) {
    // 检查角色是否存在
    Role existingRole = getById(request.getId());
    if (existingRole == null) {
      throw new ServiceException("角色不存在");
    }

    // 检查角色标识是否被其他角色使用
    Role roleWithSameKey = getRoleByKey(request.getRoleKey());
    if (roleWithSameKey != null && !roleWithSameKey.getId().equals(request.getId())) {
      throw new ServiceException("角色标识已被其他角色使用");
    }

    // 更新角色
    BeanUtils.copyProperties(request, existingRole);
    updateById(existingRole);

    // 更新权限关系
    SpringUtil.getBean(RoleMenuService.class).remove(ROLE_MENU.ROLE_ID.eq(request.getId()));
    saveRoleMenus(request.getId(), request.getPermissions());
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteRole(String id) {
    // 检查角色是否存在
    Role existingRole = getById(id);
    if (existingRole == null) {
      throw new ServiceException("角色不存在");
    }

    // 软删除角色
    removeById(id);

    // 删除权限关系
    SpringUtil.getBean(RoleMenuService.class).remove(ROLE_MENU.ROLE_ID.eq(id));
  }

  @Override
  public RoleResponse getRoleDetail(String id) {
    // 获取角色信息
    Role role = getById(id);
    if (role == null || role.getDeleted()) {
      throw new ServiceException("角色不存在");
    }

    // 转换为响应对象
    RoleResponse response = new RoleResponse();
    BeanUtils.copyProperties(role, response);
    // 查询角色的权限ID列表
    response.setPermissions(getRoleMenuIds(id));
    return response;
  }

  /** 保存角色的权限关系 */
  private void saveRoleMenus(String roleId, String[] menuIds) {
    if (menuIds != null && menuIds.length > 0) {
      for (String menuId : menuIds) {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(menuId);
        SpringUtil.getBean(RoleMenuService.class).save(roleMenu);
      }
    }
  }

  /** 获取角色的权限ID列表 */
  private String[] getRoleMenuIds(String roleId) {
    return SpringUtil.getBean(RoleMenuService.class).list(ROLE_MENU.ROLE_ID.eq(roleId)).stream()
        .map(RoleMenu::getMenuId)
        .toArray(String[]::new);
  }

  @Override
  public List<Role> getRolesByUserId(String userId) {
    // 查询用户的角色关联
    List<UserRole> userRoles = SpringUtil.getBean(UserRoleService.class)
        .list(QueryWrapper.create().where(USER_ROLE.USER_ID.eq(userId)));

    if (userRoles.isEmpty()) {
      return new ArrayList<>();
    }

    // 提取角色ID列表
    List<String> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();

    // 查询角色信息
    return this.list(
        QueryWrapper.create()
            .where(ROLE.ID.in(roleIds))
            .and(ROLE.DELETED.eq(false))
            .orderBy(ROLE.ROLE_NAME.asc()));
  }
}
