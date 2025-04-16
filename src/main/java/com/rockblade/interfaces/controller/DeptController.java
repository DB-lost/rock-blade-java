/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-16 10:16:49
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 11:47:10
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/DeptController.java
 * @Description: 部门管理接口
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.interfaces.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.rockblade.domain.system.dto.request.DeptRequest;
import com.rockblade.domain.system.dto.response.DeptResponse;
import com.rockblade.domain.system.service.impl.DeptService;
import com.rockblade.framework.core.base.entity.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

@RestController
@RequestMapping("/dept")
@Tag(name = "部门管理接口")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 获取部门列表（树形结构）
     */
    @GetMapping("/list")
    @Operation(summary = "获取部门列表（树形结构）")
    public R<List<DeptResponse>> list() {
        List<DeptResponse> depts = deptService.listDeptTree();
        return R.ok(depts);
    }

    /**
     * 新增部门
     */
    @PostMapping
    @Operation(summary = "新增部门")
    public R<Boolean> add(@RequestBody DeptRequest dept) {
        return R.ok(deptService.addDept(dept));
    }

    /**
     * 修改部门
     */
    @PutMapping
    @Operation(summary = "修改部门")
    public R<Boolean> edit(@RequestBody DeptRequest dept) {
        return R.ok(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    @Operation(summary = "删除部门")
    public R<Boolean> remove(@PathVariable String deptId) {
        return R.ok(deptService.deleteDept(deptId));
    }

}
