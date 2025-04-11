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
import com.rockblade.domain.user.entity.UserRole;
import com.rockblade.domain.user.service.UserRoleService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 用户和角色关联表 控制层。
 *
 * @author 
 * @since 2025-04-11
 */
@RestController
@Tag(name = "用户和角色关联表接口")
@RequestMapping("/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 分页查询用户和角色关联表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary="分页查询用户和角色关联表")
    public Page<UserRole> page(@Parameter(description="分页信息")Page<UserRole> page) {
        return userRoleService.page(page);
    }

    /**
     * 添加用户和角色关联表。
     *
     * @param userRole 用户和角色关联表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary="保存用户和角色关联表")
    public boolean save(@RequestBody @Parameter(description="用户和角色关联表")UserRole userRole) {
        return userRoleService.save(userRole);
    }

    /**
     * 根据主键删除用户和角色关联表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary="根据主键用户和角色关联表")
    public boolean remove(@PathVariable @Parameter(description="用户和角色关联表主键")Serializable id) {
        return userRoleService.removeById(id);
    }

    /**
     * 根据主键更新用户和角色关联表。
     *
     * @param userRole 用户和角色关联表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary="根据主键更新用户和角色关联表")
    public boolean update(@RequestBody @Parameter(description="用户和角色关联表主键")UserRole userRole) {
        return userRoleService.updateById(userRole);
    }

    /**
     * 查询所有用户和角色关联表。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary="查询所有用户和角色关联表")
    public List<UserRole> list() {
        return userRoleService.list();
    }

    /**
     * 根据用户和角色关联表主键获取详细信息。
     *
     * @param id 用户和角色关联表主键
     * @return 用户和角色关联表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary="根据主键获取用户和角色关联表")
    public UserRole getInfo(@PathVariable Serializable id) {
        return userRoleService.getById(id);
    }

}
