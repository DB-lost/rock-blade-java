/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 16:54:33
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/MenuController.java
 * @Description: 菜单控制层。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.interfaces.controller;

import com.mybatisflex.core.paginate.Page;
import com.rockblade.domain.user.dto.request.MenuRequest;
import com.rockblade.domain.user.dto.response.MenuResponse;
import com.rockblade.domain.user.service.MenuService;
import com.rockblade.framework.core.base.entity.R;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@Tag(name = "菜单权限表接口")
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表")
    public R<List<MenuResponse>> list(@Valid MenuRequest request) {
        return R.ok(menuService.getMenuList(request));
    }

    @GetMapping("/{menuId}")
    @Operation(summary = "获取菜单详情")
    public R<MenuResponse> getInfo(@PathVariable("menuId") Long menuId) {
        return R.ok(menuService.getMenuInfo(menuId));
    }

    @GetMapping("/tree")
    @Operation(summary = "获取菜单树")
    public R<List<MenuResponse>> tree() {
        return R.ok(menuService.getMenuTree());
    }

    @GetMapping("/tree/user/{userId}")
    @Operation(summary = "根据用户ID获取菜单树")
    public R<List<MenuResponse>> treeByUserId(@PathVariable("userId") Long userId) {
        return R.ok(menuService.getMenuTreeByUserId(userId));
    }

    @GetMapping("/tree/role/{roleId}")
    @Operation(summary = "根据角色ID获取菜单树")
    public R<List<MenuResponse>> treeByRoleId(@PathVariable("roleId") Long roleId) {
        return R.ok(menuService.getMenuTreeByRoleId(roleId));
    }

    @PostMapping
    @Operation(summary = "新增菜单")
    public R<Boolean> add(@RequestBody @Valid MenuRequest request) {
        return R.ok(menuService.createMenu(request));
    }

    @PutMapping
    @Operation(summary = "修改菜单")
    public R<Boolean> edit(@RequestBody @Valid MenuRequest request) {
        return R.ok(menuService.updateMenu(request));
    }

    @DeleteMapping("/{menuId}")
    @Operation(summary = "删除菜单")
    public R<Boolean> remove(@PathVariable("menuId") Long menuId) {
        return R.ok(menuService.deleteMenu(menuId));
    }
}
