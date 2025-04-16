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
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

/**
 * 部门管理
 *
 * @author rockblade
 * @since 2025-04-16
 */
@RestController
@RequestMapping("/system/dept")
@Tag(name = "部门管理接口")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 获取部门列表（树形结构）
     */
    @GetMapping("/list")
    @Operation(summary = "获取部门列表（树形结构）")
    public R<List<DeptResponse>> list(DeptRequest request) {
        List<DeptResponse> depts = deptService.listDeptTree(request);
        return R.ok(depts);
    }

    /**
     * 获取部门列表（分页）
     */
    @GetMapping("/list/page")
    @Operation(summary = "获取部门列表（分页）")
    public R<Page<DeptResponse>> listPage(DeptRequest request) {
        return R.ok(deptService.listDepts(request));
    }

    /**
     * 获取部门信息
     */
    @GetMapping(value = "/{deptId}")
    @Operation(summary = "获取部门信息")
    public R<DeptResponse> getInfo(@PathVariable("deptId") String deptId) {
        return R.ok(deptService.getDeptById(deptId));
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

    /**
     * 校验部门名称是否唯一
     */
    @GetMapping("/check/{deptName}/{parentId}")
    @Operation(summary = "校验部门名称是否唯一")
    public R<Boolean> checkDeptNameUnique(
            @PathVariable("deptName") String deptName,
            @PathVariable(value = "parentId", required = false) String parentId,
            @RequestParam(value = "deptId", required = false) String deptId) {
        return R.ok(deptService.checkDeptNameUnique(deptName, parentId, deptId));
    }
}
