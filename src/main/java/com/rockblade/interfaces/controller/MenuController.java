/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 22:21:37
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-14 12:57:02
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/MenuController.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.interfaces.controller;

import com.rockblade.domain.user.dto.request.MenuRequest;
import com.rockblade.domain.user.dto.response.MenuResponse;
import com.rockblade.domain.user.service.MenuService;
import com.rockblade.framework.core.base.entity.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameters;
import java.util.List;

@RestController
@Tag(name = "菜单权限管理接口")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表", description = "支持按名称模糊查询，支持按状态筛选")
    public R<List<MenuResponse>> list(@Validated MenuRequest request) {
        return R.ok(menuService.getMenuList(request));
    }

    @GetMapping("/tree")
    @Operation(summary = "获取菜单树", description = "返回所有菜单的树形结构")
    public R<List<MenuResponse>> getMenuTree() {
        return R.ok(menuService.getMenuTree());
    }

    @GetMapping("/user/tree")
    @Operation(summary = "获取当前用户的菜单树", description = "返回当前用户有权限访问的菜单树形结构")
    @Parameter(name = "userId", description = "用户ID", required = true)
    public R<List<MenuResponse>> getUserMenuTree(@RequestParam("userId") Long userId) {
        return R.ok(menuService.getMenuTreeByUserId(userId));
    }

    @GetMapping("/role/tree/{roleId}")
    @Operation(summary = "获取角色的菜单树", description = "返回指定角色的菜单树形结构")
    @Parameter(name = "roleId", description = "角色ID", required = true)
    public R<List<MenuResponse>> getRoleMenuTree(@PathVariable("roleId") Long roleId) {
        return R.ok(menuService.getMenuTreeByRoleId(roleId));
    }

    @GetMapping("/{menuId}")
    @Operation(summary = "获取菜单详情", description = "根据菜单ID获取菜单详细信息")
    @Parameter(name = "menuId", description = "菜单ID", required = true)
    public R<MenuResponse> getInfo(@PathVariable("menuId") Long menuId) {
        return R.ok(menuService.getMenuInfo(menuId));
    }

    @PostMapping
    @Operation(summary = "新增菜单", description = "创建新的菜单项，支持目录、菜单、按钮三种类型")
    public R<Boolean> add(@RequestBody @Validated MenuRequest request) {
        return R.ok(menuService.createMenu(request));
    }

    @PutMapping
    @Operation(summary = "修改菜单", description = "更新已有菜单的信息")
    public R<Boolean> edit(@RequestBody @Validated MenuRequest request) {
        return R.ok(menuService.updateMenu(request));
    }

    @DeleteMapping("/{menuId}")
    @Operation(summary = "删除菜单", description = "删除指定的菜单，如果有子菜单则不允许删除")
    @Parameter(name = "menuId", description = "菜单ID", required = true)
    public R<Boolean> remove(@PathVariable("menuId") Long menuId) {
        return R.ok(menuService.deleteMenu(menuId));
    }
}
