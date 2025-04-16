package com.rockblade.interfaces.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.rockblade.domain.user.entity.Dept;
import com.rockblade.domain.user.service.DeptService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 部门表 控制层。
 *
 * @author 
 * @since 2025-04-16
 */
@RestController
@Tag(name = "部门表接口")
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 分页查询部门表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary="分页查询部门表")
    public Page<Dept> page(@Parameter(description="分页信息")Page<Dept> page) {
        return deptService.page(page);
    }

    /**
     * 添加部门表。
     *
     * @param dept 部门表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary="保存部门表")
    public boolean save(@RequestBody @Parameter(description="部门表")Dept dept) {
        return deptService.save(dept);
    }

    /**
     * 根据主键删除部门表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary="根据主键部门表")
    public boolean remove(@PathVariable @Parameter(description="部门表主键")Serializable id) {
        return deptService.removeById(id);
    }

    /**
     * 根据主键更新部门表。
     *
     * @param dept 部门表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary="根据主键更新部门表")
    public boolean update(@RequestBody @Parameter(description="部门表主键")Dept dept) {
        return deptService.updateById(dept);
    }

    /**
     * 查询所有部门表。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary="查询所有部门表")
    public List<Dept> list() {
        return deptService.list();
    }

    /**
     * 根据部门表主键获取详细信息。
     *
     * @param id 部门表主键
     * @return 部门表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary="根据主键获取部门表")
    public Dept getInfo(@PathVariable Serializable id) {
        return deptService.getById(id);
    }

}
