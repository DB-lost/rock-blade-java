/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-16 10:16:49
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:18:02
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/DeptService.java
 * @Description: 部门表 服务层。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service;

import java.util.List;

import com.mybatisflex.core.service.IService;
import com.rockblade.common.dto.system.request.DeptRequest;
import com.rockblade.common.dto.system.response.DeptResponse;
import com.rockblade.system.entity.Dept;

public interface DeptService extends IService<Dept> {

  /**
   * 查询部门树结构
   *
   * @param request 查询参数
   * @return 部门树
   */
  List<DeptResponse> listDeptTree();

  /**
   * 根据ID查询部门
   *
   * @param deptId 部门ID
   * @return 部门信息
   */
  DeptResponse getDeptById(String deptId);

  /**
   * 新增部门
   *
   * @param request 部门信息
   * @return 结果
   */
  boolean addDept(DeptRequest request);

  /**
   * 修改部门
   *
   * @param request 部门信息
   * @return 结果
   */
  boolean updateDept(DeptRequest request);

  /**
   * 删除部门
   *
   * @param deptId 部门ID
   * @return 结果
   */
  boolean deleteDept(String deptId);

  /**
   * 校验部门名称是否唯一
   *
   * @param deptName 部门名称
   * @param parentId 父部门ID
   * @param deptId 部门ID（可选，用于更新校验）
   * @return 结果
   */
  boolean checkDeptNameUnique(String deptName, String parentId, String deptId);

  /**
   * 检查是否存在子部门
   *
   * @param deptId 部门ID
   * @return 结果
   */
  boolean hasChildDept(String deptId);

  /**
   * 根据用户ID获取部门信息列表
   *
   * @param userId 用户ID
   * @return 部门信息列表
   */
  List<DeptResponse> getDeptsByUserId(String userId);
}
