/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-16 10:16:49
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:28:18
 * @FilePath: /rock-blade-java/rock-blade-api/src/main/java/com/rockblade/api/controller/system/DeptController.java
 * @Description: 部门管理接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.api.controller.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rockblade.common.dto.system.request.DeptRequest;
import com.rockblade.common.dto.system.response.DeptResponse;
import com.rockblade.framework.core.base.entity.R;
import com.rockblade.system.service.DeptService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dept")
@Tag(name = "部门管理接口")
public class DeptController {

  @Autowired private DeptService deptService;

  /** 获取部门列表（树形结构） */
  @GetMapping("/list")
  @Operation(summary = "获取部门列表（树形结构）")
  public R<List<DeptResponse>> list() {
    List<DeptResponse> depts = deptService.listDeptTree();
    return R.ok(depts);
  }

  /** 新增部门 */
  @PostMapping
  @Operation(summary = "新增部门")
  public R<Boolean> add(@RequestBody DeptRequest dept) {
    return R.ok(deptService.addDept(dept));
  }

  /** 修改部门 */
  @PutMapping
  @Operation(summary = "修改部门")
  public R<Boolean> edit(@RequestBody DeptRequest dept) {
    return R.ok(deptService.updateDept(dept));
  }

  /** 删除部门 */
  @DeleteMapping("/{deptId}")
  @Operation(summary = "删除部门")
  public R<Boolean> remove(@PathVariable String deptId) {
    return R.ok(deptService.deleteDept(deptId));
  }

  /** 获取部门详情 */
  @GetMapping("/detail/{deptId}")
  @Operation(summary = "获取部门详情")
  public R<DeptResponse> getDeptById(@PathVariable String deptId) {
    return R.ok(deptService.getDeptById(deptId));
  }
}
