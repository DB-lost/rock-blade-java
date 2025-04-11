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
import com.rockblade.domain.user.entity.Role;
import com.rockblade.domain.user.service.RoleService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 角色信息表 控制层。
 *
 * @author 
 * @since 2025-04-11
 */
@RestController
@Tag(name = "角色信息表接口")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询角色信息表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary="分页查询角色信息表")
    public Page<Role> page(@Parameter(description="分页信息")Page<Role> page) {
        return roleService.page(page);
    }

    /**
     * 添加角色信息表。
     *
     * @param role 角色信息表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary="保存角色信息表")
    public boolean save(@RequestBody @Parameter(description="角色信息表")Role role) {
        return roleService.save(role);
    }

    /**
     * 根据主键删除角色信息表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary="根据主键角色信息表")
    public boolean remove(@PathVariable @Parameter(description="角色信息表主键")Serializable id) {
        return roleService.removeById(id);
    }

    /**
     * 根据主键更新角色信息表。
     *
     * @param role 角色信息表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary="根据主键更新角色信息表")
    public boolean update(@RequestBody @Parameter(description="角色信息表主键")Role role) {
        return roleService.updateById(role);
    }

    /**
     * 查询所有角色信息表。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary="查询所有角色信息表")
    public List<Role> list() {
        return roleService.list();
    }

    /**
     * 根据角色信息表主键获取详细信息。
     *
     * @param id 角色信息表主键
     * @return 角色信息表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary="根据主键获取角色信息表")
    public Role getInfo(@PathVariable Serializable id) {
        return roleService.getById(id);
    }

}
