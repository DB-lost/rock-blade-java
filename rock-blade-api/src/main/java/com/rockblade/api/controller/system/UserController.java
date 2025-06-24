/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 00:13:16
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:29:41
 * @FilePath: /rock-blade-java/rock-blade-api/src/main/java/com/rockblade/api/controller/system/UserController.java
 * @Description: 用户信息接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.api.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.rockblade.common.dto.system.request.UserPageRequest;
import com.rockblade.common.dto.system.request.UserRequest;
import com.rockblade.common.dto.system.response.DeptResponse;
import com.rockblade.common.dto.system.response.RoleResponse;
import com.rockblade.common.dto.system.response.UserPageResponse;
import com.rockblade.framework.core.base.entity.R;
import com.rockblade.system.entity.Role;
import com.rockblade.system.service.DeptService;
import com.rockblade.system.service.RoleService;
import com.rockblade.system.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "用户信息接口")
@RequestMapping("/user")
public class UserController {

  @Autowired private UserService userService;

  @Autowired private DeptService deptService;

  @Autowired private RoleService roleService;

  @GetMapping("/page")
  @Operation(summary = "分页查询用户列表")
  public R<Page<UserPageResponse>> page(UserPageRequest request) {
    return R.ok(userService.page(request));
  }

  @PutMapping
  @Operation(summary = "修改用户")
  public R<Void> edit(@Validated @RequestBody UserRequest request) {
    userService.edit(request);
    return R.ok();
  }

  @GetMapping("/getDeptList")
  @Operation(summary = "获取部门列表")
  public R<List<DeptResponse>> getDeptList() {
    return R.ok(deptService.listDeptTree());
  }

  @GetMapping("/getRoleList")
  @Operation(summary = "获取角色列表")
  public R<List<RoleResponse>> getRoleList() {
    return R.ok(
        roleService.listAs(QueryWrapper.create().eq(Role::getStatus, "1"), RoleResponse.class));
  }
}
