/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 22:21:37
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:17:16
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/MenuService.java
 * @Description:
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.rockblade.common.dto.system.request.MenuRequest;
import com.rockblade.common.dto.system.response.MenuResponse;
import com.rockblade.system.entity.Menu;

public interface MenuService extends IService<Menu> {

  /**
   * 获取菜单列表
   *
   * @param request 查询参数
   * @return 菜单列表
   */
  List<MenuResponse> getMenuList();

  /**
   * 获取菜单详情
   *
   * @param menuId 菜单ID
   * @return 菜单详情
   */
  MenuResponse getMenuInfo(String menuId);

  /**
   * 获取菜单树
   *
   * @return 菜单树
   */
  List<MenuResponse> getMenuTree();

  /**
   * 获取用户的菜单树
   *
   * @param userId 用户ID
   * @return 用户的菜单树
   */
  List<MenuResponse> getMenuTreeByUserId(String userId);

  /**
   * 获取角色的菜单树
   *
   * @param roleId 角色ID
   * @return 角色的菜单树
   */
  List<MenuResponse> getMenuTreeByRoleId(String roleId);

  /**
   * 创建菜单
   *
   * @param request 菜单信息
   * @return 是否成功
   */
  Boolean createMenu(MenuRequest request);

  /**
   * 更新菜单
   *
   * @param request 菜单信息
   * @return 是否成功
   */
  Boolean updateMenu(MenuRequest request);

  /**
   * 删除菜单
   *
   * @param menuId 菜单ID
   * @return 是否成功
   */
  Boolean deleteMenu(String menuId);

  /**
   * 校验菜单名称是否唯一
   *
   * @param menuName 菜单名称
   * @param parentId 父菜单ID
   * @param menuId 菜单ID（更新时传入）
   * @return true: 唯一, false: 不唯一
   */
  Boolean checkMenuNameUnique(String menuName, String parentId, String menuId);
}
