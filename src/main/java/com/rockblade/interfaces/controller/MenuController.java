/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-12 16:50:40
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/MenuController.java
 * @Description: 菜单控制层。
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
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@Tag(name = "菜单权限表接口")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表")
    public R<List<MenuResponse>> list(@Validated MenuRequest request) {
        return R.ok(menuService.getMenuList(request));
    }

    @GetMapping("/{menuId}")
    @Operation(summary = "获取菜单详情")
    public R<MenuResponse> getInfo(@PathVariable("menuId") Long menuId) {
        return R.ok(menuService.getMenuInfo(menuId));
    }

    @PostMapping
    @Operation(summary = "新增菜单")
    public R<Boolean> add(@RequestBody @Validated MenuRequest request) {
        return R.ok(menuService.createMenu(request));
    }

    @PutMapping
    @Operation(summary = "修改菜单")
    public R<Boolean> edit(@RequestBody @Validated MenuRequest request) {
        return R.ok(menuService.updateMenu(request));
    }

    @DeleteMapping("/{menuId}")
    @Operation(summary = "删除菜单")
    public R<Boolean> remove(@PathVariable("menuId") Long menuId) {
        return R.ok(menuService.deleteMenu(menuId));
    }
}
