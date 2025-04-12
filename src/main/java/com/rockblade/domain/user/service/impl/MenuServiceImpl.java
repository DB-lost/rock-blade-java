/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-12 15:48:50
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/impl/MenuServiceImpl.java
 * @Description: 菜单权限表 服务实现层。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.dto.request.MenuRequest;
import com.rockblade.domain.user.dto.response.MenuResponse;
import com.rockblade.domain.user.entity.Menu;
import com.rockblade.domain.user.service.MenuService;
import com.rockblade.domain.user.service.RoleMenuService;
import com.rockblade.domain.user.service.UserRoleService;
import com.rockblade.infrastructure.mapper.MenuMapper;

import cn.hutool.extra.spring.SpringUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rockblade.domain.user.entity.table.MenuTableDef.MENU;
import static com.rockblade.domain.user.entity.table.RoleMenuTableDef.ROLE_MENU;
import static com.rockblade.domain.user.entity.table.UserRoleTableDef.USER_ROLE;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<MenuResponse> getMenuList(MenuRequest request) {
        QueryWrapper query = QueryWrapper.create()
                .and(MENU.MENU_NAME.like(request.getMenuName()))
                .and(MENU.STATUS.eq(request.getStatus()))
                .orderBy(MENU.ORDER_NUM.asc());

        return this.list(query).stream()
                .map(menu -> {
                    MenuResponse response = new MenuResponse();
                    BeanUtils.copyProperties(menu, response);
                    return response;
                })
                .toList();
    }

    @Override
    public MenuResponse getMenuInfo(Long menuId) {
        Menu menu = this.getById(menuId);
        if (menu == null) {
            return null;
        }
        MenuResponse response = new MenuResponse();
        BeanUtils.copyProperties(menu, response);
        return response;
    }

    @Override
    public List<MenuResponse> getMenuTree() {
        List<Menu> menus = this.list(QueryWrapper.create().orderBy(MENU.ORDER_NUM.asc()));
        return buildMenuTree(menus, 0L);
    }

    @Override
    public List<MenuResponse> getMenuTreeByUserId(Long userId) {
        // TODO: 实现角色管理后，需要根据用户角色返回对应的菜单
        // 通过用户ID获取角色ID列表
        // List<Long> roleIds = SpringUtil.getBean(UserRoleService.class).queryChain()
        // .select(USER_ROLE.ROLE_ID)
        // .where(USER_ROLE.USER_ID.eq(userId))
        // .list()
        // .stream()
        // .map(ur -> ur.getRoleId())
        // .toList();

        // // 通过角色ID列表获取菜单ID列表
        // List<Long> menuIds = SpringUtil.getBean(RoleMenuService.class).queryChain()
        // .select(ROLE_MENU.MENU_ID)
        // .where(ROLE_MENU.ROLE_ID.in(roleIds))
        // .list()
        // .stream()
        // .map(rm -> rm.getMenuId())
        // .toList();

        // // 查询菜单列表
        // List<Menu> menus = this.list(
        // QueryWrapper.create()
        // .where(MENU.ID.in(menuIds))
        // .orderBy(MENU.ORDER_NUM.asc()));
        // 目前临时返回所有可见的菜单
        List<Menu> menus = this.list(
                QueryWrapper.create()
                        .where(MENU.STATUS.eq("1")) // 状态正常
                        .and(MENU.VISIBLE.eq("1")) // 显示状态正常
                        .orderBy(MENU.ORDER_NUM.asc()));

        return buildMenuTree(menus, 0L);
    }

    @Override
    public List<MenuResponse> getMenuTreeByRoleId(Long roleId) {
        // 通过角色ID获取菜单ID列表
        List<Long> menuIds = SpringUtil.getBean(RoleMenuService.class).queryChain()
                .select(ROLE_MENU.MENU_ID)
                .where(ROLE_MENU.ROLE_ID.eq(roleId))
                .list()
                .stream()
                .map(rm -> rm.getMenuId())
                .toList();

        // 查询菜单列表
        List<Menu> menus = this.list(
                QueryWrapper.create()
                        .where(MENU.ID.in(menuIds))
                        .orderBy(MENU.ORDER_NUM.asc()));

        return buildMenuTree(menus, 0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createMenu(MenuRequest request) {
        // 校验菜单名称唯一性
        if (!checkMenuNameUnique(request.getMenuName(), request.getParentId(), null)) {
            throw new RuntimeException("菜单名称已存在");
        }

        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        return this.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateMenu(MenuRequest request) {
        // 校验菜单名称唯一性
        if (!checkMenuNameUnique(request.getMenuName(), request.getParentId(), request.getId())) {
            throw new RuntimeException("菜单名称已存在");
        }

        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        return this.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(Long menuId) {
        // 判断是否存在子菜单
        long count = this.count(QueryWrapper.create().where(MENU.PARENT_ID.eq(menuId)));
        if (count > 0) {
            throw new RuntimeException("存在子菜单,不允许删除");
        }

        // 删除角色菜单关联
        SpringUtil.getBean(RoleMenuService.class).remove(
                QueryWrapper.create().where(ROLE_MENU.MENU_ID.eq(menuId)));

        return this.removeById(menuId);
    }

    @Override
    public Boolean checkMenuNameUnique(String menuName, Long parentId, Long menuId) {
        QueryWrapper query = QueryWrapper.create()
                .where(MENU.MENU_NAME.eq(menuName))
                .and(MENU.PARENT_ID.eq(parentId));

        if (menuId != null) {
            query.and(MENU.ID.ne(menuId));
        }

        return this.count(query) == 0;
    }

    /**
     * 构建菜单树
     *
     * @param menus    菜单列表
     * @param parentId 父ID
     * @return 菜单树
     */
    private List<MenuResponse> buildMenuTree(List<Menu> menus, Long parentId) {
        List<MenuResponse> tree = new ArrayList<>();
        menus.stream()
                .filter(menu -> parentId == 0L ? menu.getParentId() == null || menu.getParentId() == 0L
                        : Objects.equals(menu.getParentId(), parentId))
                .forEach(menu -> {
                    MenuResponse node = new MenuResponse();
                    BeanUtils.copyProperties(menu, node);
                    List<MenuResponse> children = buildMenuTree(menus, menu.getId());
                    node.setChildren(children);
                    node.setHasChildren(!children.isEmpty());
                    tree.add(node);
                });
        return tree;
    }
}
