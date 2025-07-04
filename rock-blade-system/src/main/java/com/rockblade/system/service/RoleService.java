/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:18:20
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/RoleService.java
 * @Description: 角色信息表 服务层。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service;

import java.util.List;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.rockblade.common.dto.system.request.RolePageRequest;
import com.rockblade.common.dto.system.request.RoleRequest;
import com.rockblade.common.dto.system.response.RoleResponse;
import com.rockblade.system.entity.Role;

public interface RoleService extends IService<Role> {
  /**
   * 根据角色标识获取角色信息
   *
   * @param roleKey 角色标识
   * @return 角色信息
   */
  Role getRoleByKey(String roleKey);

  /**
   * 获取角色列表
   *
   * @param request 参数
   * @return 角色列表
   */
  Page<RoleResponse> getRolePage(RolePageRequest request);

  /**
   * 创建角色
   *
   * @param request 角色请求参数
   */
  void createRole(RoleRequest request);

  /**
   * 更新角色
   *
   * @param request 角色请求参数
   */
  void updateRole(RoleRequest request);

  /**
   * 删除角色
   *
   * @param id 角色ID
   */
  void deleteRole(String id);

  /**
   * 获取角色详情
   *
   * @param id 角色ID
   * @return 角色详情
   */
  RoleResponse getRoleDetail(String id);

  /**
   * 获取用户的角色列表
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  List<Role> getRolesByUserId(String userId);
}
