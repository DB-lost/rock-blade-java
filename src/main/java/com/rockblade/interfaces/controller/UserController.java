/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 00:13:16
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 00:16:24
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/interfaces/controller/UserController.java
 * @Description: 用户接口
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
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
import com.rockblade.domain.user.entity.User;
import com.rockblade.domain.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

@RestController
@Tag(name = "用户信息表接口")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询用户信息表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户信息表")
    public Page<User> page(@Parameter(description = "分页信息") Page<User> page) {
        return userService.page(page);
    }

    /**
     * 添加用户信息表。
     *
     * @param user 用户信息表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary = "保存用户信息表")
    public boolean save(@RequestBody @Parameter(description = "用户信息表") User user) {
        return userService.save(user);
    }

    /**
     * 根据主键删除用户信息表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary = "根据主键用户信息表")
    public boolean remove(@PathVariable @Parameter(description = "用户信息表主键") Serializable id) {
        return userService.removeById(id);
    }

    /**
     * 根据主键更新用户信息表。
     *
     * @param user 用户信息表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary = "根据主键更新用户信息表")
    public boolean update(@RequestBody @Parameter(description = "用户信息表主键") User user) {
        return userService.updateById(user);
    }

    /**
     * 查询所有用户信息表。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有用户信息表")
    public List<User> list() {
        return userService.list();
    }

    /**
     * 根据用户信息表主键获取详细信息。
     *
     * @param id 用户信息表主键
     * @return 用户信息表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary = "根据主键获取用户信息表")
    public User getInfo(@PathVariable Serializable id) {
        return userService.getById(id);
    }

}
