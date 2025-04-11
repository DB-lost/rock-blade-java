/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 16:53:52
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/MenuService.java
 * @Description: 菜单权限表 服务层。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.rockblade.domain.user.dto.request.MenuRequest;
import com.rockblade.domain.user.dto.response.MenuResponse;
import com.rockblade.domain.user.entity.Menu;
import java.util.List;

public interface MenuService extends IService<Menu> {

    /**
     * 查询菜单列表
     *
     * @param request 菜单请求参数
     * @return 菜单列表
     */
    List<MenuResponse> getMenuList(MenuRequest request);

    /**
     * 获取菜单详情
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    MenuResponse getMenuInfo(Long menuId);

    /**
     * 获取菜单树结构
     *
     * @return 菜单树列表
     */
    List<MenuResponse> getMenuTree();

    /**
     * 根据用户ID查询菜单树结构
     *
     * @param userId 用户ID
     * @return 菜单树列表
     */
    List<MenuResponse> getMenuTreeByUserId(Long userId);

    /**
     * 根据角色ID查询菜单树结构
     *
     * @param roleId 角色ID
     * @return 菜单树列表
     */
    List<MenuResponse> getMenuTreeByRoleId(Long roleId);

    /**
     * 新增菜单
     *
     * @param request 菜单信息
     * @return 结果
     */
    Boolean createMenu(MenuRequest request);

    /**
     * 修改菜单
     *
     * @param request 菜单信息
     * @return 结果
     */
    Boolean updateMenu(MenuRequest request);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    Boolean deleteMenu(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单名称
     * @param parentId 父菜单ID
     * @param menuId   菜单ID
     * @return 结果
     */
    Boolean checkMenuNameUnique(String menuName, Long parentId, Long menuId);
}
