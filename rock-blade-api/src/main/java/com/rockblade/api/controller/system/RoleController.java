/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:30:01
 * @FilePath: /rock-blade-java/rock-blade-api/src/main/java/com/rockblade/api/controller/system/RoleController.java
 * @Description: 角色接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.api.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mybatisflex.core.paginate.Page;
import com.rockblade.common.dto.system.request.RolePageRequest;
import com.rockblade.common.dto.system.request.RoleRequest;
import com.rockblade.common.dto.system.response.RoleResponse;
import com.rockblade.system.service.RoleService;
import com.rockblade.framework.core.base.entity.R;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "角色接口")
@RequestMapping("/role")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @GetMapping("/page")
  @Operation(summary = "获取角色列表")
  public R<Page<RoleResponse>> getRolePage(RolePageRequest request) {
    return R.ok(roleService.getRolePage(request));
  }

  @PostMapping
  @Operation(summary = "创建角色")
  public R<Void> createRole(@RequestBody @Validated RoleRequest request) {
    roleService.createRole(request);
    return R.ok();
  }

  @PutMapping
  @Operation(summary = "更新角色")
  public R<Void> updateRole(@RequestBody @Validated RoleRequest request) {
    roleService.updateRole(request);
    return R.ok();
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "删除角色")
  public R<Void> deleteRole(@PathVariable String id) {
    roleService.deleteRole(id);
    return R.ok();
  }
}
