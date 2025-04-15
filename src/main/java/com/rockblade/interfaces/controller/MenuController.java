/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 22:21:37
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-15 16:34:57
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

import static com.rockblade.domain.user.entity.table.MenuTableDef.MENU;

import java.util.List;

@RestController
@Tag(name = "菜单权限管理接口")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表")
    public R<List<MenuResponse>> list() {
        return R.ok(menuService.getMenuList());
    }

    @GetMapping("/nameExists")
    @Operation(summary = "菜单名是否存在")
    public R<Boolean> nameExists(
            @Parameter(description = "菜单名") @RequestParam("name") String name,
            @Parameter(description = "菜单id", required = false) @RequestParam(required = false) Long id) {
        return R.ok(menuService.queryChain().where(MENU.NAME.eq(name))
                .and(MENU.ID.ne(id)).exists());
    }

    @GetMapping("/pathExists")
    @Operation(summary = "菜单路径是否存在")
    public R<Boolean> pathExists(
            @Parameter(description = "菜单路径") @RequestParam("path") String path,
            @Parameter(description = "菜单id", required = false) @RequestParam(required = false) Long id) {
        return R.ok(menuService.queryChain().where(MENU.PATH.eq(path))
                .and(MENU.ID.ne(id)).exists());
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
    public R<Boolean> remove(@PathVariable("menuId") String menuId) {
        return R.ok(menuService.deleteMenu(menuId));
    }

    @GetMapping("/tree")
    @Operation(summary = "获取菜单树", description = "返回所有菜单的树形结构")
    public R<List<MenuResponse>> getMenuTree() {
        return R.ok(menuService.getMenuTree());
    }

}
