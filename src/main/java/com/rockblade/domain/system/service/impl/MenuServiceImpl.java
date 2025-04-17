/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 10:15:04
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/service/impl/MenuServiceImpl.java
 * @Description: 菜单权限表 服务实现层。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.dto.request.MenuRequest;
import com.rockblade.domain.system.dto.response.MenuResponse;
import com.rockblade.domain.system.entity.Menu;
import com.rockblade.domain.system.service.MenuService;
import com.rockblade.domain.system.service.RoleMenuService;
import com.rockblade.infrastructure.mapper.MenuMapper;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rockblade.domain.system.entity.table.MenuTableDef.MENU;
import static com.rockblade.domain.system.entity.table.RoleMenuTableDef.ROLE_MENU;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<MenuResponse> getMenuList() {
        return getMenuTree();
    }

    @Override
    public MenuResponse getMenuInfo(String menuId) {
        Menu menu = this.getById(menuId);
        if (menu == null) {
            return null;
        }
        return convertToResponse(menu);
    }

    @Override
    public List<MenuResponse> getMenuTree() {
        List<Menu> menus = this.list(QueryWrapper.create().orderBy(MENU.ORDER.asc()));
        return buildMenuTree(menus, null);
    }

    @Override
    public List<MenuResponse> getMenuTreeByUserId(String userId) {
        // 查询所有可见的菜单
        List<Menu> menus = this.list(
                QueryWrapper.create()
                        .orderBy(MENU.ORDER.asc()));

        // 过滤权限并构建树
        List<Menu> authorizedMenus = menus.stream()
                .filter(menu -> {
                    // 如果菜单忽略权限访问，直接返回true
                    // if (Boolean.TRUE.equals(menu.getIgnoreAccess())) {
                    // return true;
                    // }

                    // TODO: 实现角色权限检查
                    // 如果设置了authority,需要检查用户角色是否匹配
                    // if (menu.getAuthority() != null && menu.getAuthority().length > 0) {
                    // return checkUserHasRole(userId, menu.getAuthority());
                    // }

                    return true;
                })
                .toList();

        return buildMenuTree(authorizedMenus, "0");
    }

    @Override
    public List<MenuResponse> getMenuTreeByRoleId(String roleId) {
        // 通过角色ID获取菜单ID列表
        List<String> menuIds = SpringUtil.getBean(RoleMenuService.class).queryChain()
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
                        .orderBy(MENU.ORDER.asc()));

        return buildMenuTree(menus, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createMenu(MenuRequest request) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        BeanUtils.copyProperties(request.getMeta(), menu);
        return this.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateMenu(MenuRequest request) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(request, menu);
        BeanUtils.copyProperties(request.getMeta(), menu);
        return this.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(String menuId) {
        // 判断是否存在子菜单
        long count = this.count(QueryWrapper.create().where(MENU.PID.eq(menuId)));
        if (count > 0) {
            throw new RuntimeException("存在子菜单,不允许删除");
        }

        // 删除角色菜单关联
        SpringUtil.getBean(RoleMenuService.class).remove(
                QueryWrapper.create().where(ROLE_MENU.MENU_ID.eq(menuId)));

        return this.removeById(menuId);
    }

    @Override
    public Boolean checkMenuNameUnique(String menuName, String parentId, String menuId) {
        QueryWrapper query = QueryWrapper.create()
                .where(MENU.NAME.eq(menuName))
                .and(MENU.PID.eq(parentId));

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
    private List<MenuResponse> buildMenuTree(List<Menu> menus, String parentId) {
        List<MenuResponse> tree = new ArrayList<>();
        menus.stream()
                .filter(menu -> StrUtil.isBlank(parentId) ? menu.getPid() == null
                        : Objects.equals(menu.getPid(), parentId))
                .forEach(menu -> {
                    MenuResponse node = convertToResponse(menu);
                    List<MenuResponse> children = buildMenuTree(menus, menu.getId());
                    node.setChildren(children);
                    // node.setHasChildren(!children.isEmpty());
                    tree.add(node);
                });
        return tree;
    }

    /**
     * 将Menu实体转换为MenuResponse
     *
     * @param menu Menu实体
     * @return MenuResponse
     */
    private MenuResponse convertToResponse(Menu menu) {
        MenuResponse response = new MenuResponse();
        // 复制基本字段
        response.setId(menu.getId());
        response.setName(menu.getName());
        response.setPid(menu.getPid());
        response.setPath(menu.getPath());
        response.setComponent(menu.getComponent());
        response.setRedirect(menu.getRedirect());
        response.setType(menu.getType());
        response.setAuthCode(menu.getAuthCode());
        response.setStatus(menu.getStatus());

        // 创建并设置meta信息
        MenuResponse.MenuMeta meta = new MenuResponse.MenuMeta();
        meta.setActiveIcon(menu.getActiveIcon());
        meta.setActivePath(menu.getActivePath());
        meta.setAffixTab(menu.getAffixTab());
        meta.setAffixTabOrder(menu.getAffixTabOrder());
        meta.setBadge(menu.getBadge());
        meta.setBadgeType(menu.getBadgeType());
        meta.setBadgeVariants(menu.getBadgeVariants());
        meta.setHideChildrenInMenu(menu.getHideChildrenInMenu());
        meta.setHideInBreadcrumb(menu.getHideInBreadcrumb());
        meta.setHideInMenu(menu.getHideInMenu());
        meta.setHideInTab(menu.getHideInTab());
        meta.setIcon(menu.getIcon());
        meta.setIframeSrc(menu.getIframeSrc());
        meta.setKeepAlive(menu.getKeepAlive());
        meta.setLink(menu.getLink());
        meta.setMaxNumOfOpenTab(menu.getMaxNumOfOpenTab());
        meta.setNoBasicLayout(menu.getNoBasicLayout());
        meta.setOpenInNewWindow(menu.getOpenInNewWindow());
        meta.setOrder(menu.getOrder());
        meta.setTitle(menu.getTitle());

        response.setMeta(meta);
        return response;
    }
}
