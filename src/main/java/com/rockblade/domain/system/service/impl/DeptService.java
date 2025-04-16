/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-16 10:16:49
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 10:51:09
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/service/impl/DeptService.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.rockblade.domain.system.dto.request.DeptRequest;
import com.rockblade.domain.system.dto.response.DeptResponse;
import com.rockblade.domain.system.entity.Dept;

import java.util.List;

/**
 * 部门表 服务层。
 *
 * @author rockblade
 * @since 2025-04-16
 */
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
     * @param deptId   部门ID（可选，用于更新校验）
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
}
