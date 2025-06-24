/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 14:43:52
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:28:06
 * @FilePath: /rock-blade-java/rock-blade-api/src/main/java/com/rockblade/api/controller/system/CommonController.java
 * @Description: 公共接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.api.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rockblade.common.dto.system.request.UserDetailsInfoRequest;
import com.rockblade.common.dto.system.response.MenuResponse;
import com.rockblade.common.dto.system.response.PermissionsResponse;
import com.rockblade.common.dto.system.response.UserInfoResponse;
import com.rockblade.system.service.MenuService;
import com.rockblade.system.service.UserService;
import com.rockblade.framework.core.base.entity.R;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/common")
@Tag(name = "公共接口")
public class CommonController {

  @Autowired
  private MenuService menuService;

  @Autowired
  private UserService userService;

  @GetMapping("/getUserInfo")
  @Operation(summary = "获取用户信息")
  public R<UserInfoResponse> getUserInfo() {
    UserInfoResponse userInfo = StpUtil.getSession().getModel("user", UserInfoResponse.class);
    return R.ok(userInfo);
  }

  @GetMapping("/getMenuList")
  @Operation(summary = "获取菜单树")
  public R<List<MenuResponse>> getMenuList() {
    return R.ok(menuService.getMenuTreeByUserId(StpUtil.getLoginIdAsString()));
  }

  @GetMapping("/getPermissions")
  @Operation(summary = "获取角色和权限码")
  public R<PermissionsResponse> getPermissions() {
    PermissionsResponse permissionsResponse = new PermissionsResponse();
    permissionsResponse.setPermissions(StpUtil.getPermissionList());
    // TODO: 后续支持多角色
    permissionsResponse.setRoles(StpUtil.getRoleList());
    return R.ok(permissionsResponse);
  }

  @PostMapping("/updateUserDetails")
  @Operation(summary = "更新用户详情")
  public R<Void> updateUserDetails(@RequestBody UserDetailsInfoRequest request) {
    userService.updateUserDetails(request);
    return R.ok();
  }
}
