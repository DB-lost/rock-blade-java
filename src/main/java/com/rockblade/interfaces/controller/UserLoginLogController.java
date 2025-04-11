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
import com.rockblade.domain.user.entity.UserLoginLog;
import com.rockblade.domain.user.service.UserLoginLogService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 用户登录日志表 控制层。
 *
 * @author 
 * @since 2025-04-11
 */
@RestController
@Tag(name = "用户登录日志表接口")
@RequestMapping("/userLoginLog")
public class UserLoginLogController {

    @Autowired
    private UserLoginLogService userLoginLogService;

    /**
     * 分页查询用户登录日志表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary="分页查询用户登录日志表")
    public Page<UserLoginLog> page(@Parameter(description="分页信息")Page<UserLoginLog> page) {
        return userLoginLogService.page(page);
    }

    /**
     * 添加用户登录日志表。
     *
     * @param userLoginLog 用户登录日志表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary="保存用户登录日志表")
    public boolean save(@RequestBody @Parameter(description="用户登录日志表")UserLoginLog userLoginLog) {
        return userLoginLogService.save(userLoginLog);
    }

    /**
     * 根据主键删除用户登录日志表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary="根据主键用户登录日志表")
    public boolean remove(@PathVariable @Parameter(description="用户登录日志表主键")Serializable id) {
        return userLoginLogService.removeById(id);
    }

    /**
     * 根据主键更新用户登录日志表。
     *
     * @param userLoginLog 用户登录日志表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary="根据主键更新用户登录日志表")
    public boolean update(@RequestBody @Parameter(description="用户登录日志表主键")UserLoginLog userLoginLog) {
        return userLoginLogService.updateById(userLoginLog);
    }

    /**
     * 查询所有用户登录日志表。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary="查询所有用户登录日志表")
    public List<UserLoginLog> list() {
        return userLoginLogService.list();
    }

    /**
     * 根据用户登录日志表主键获取详细信息。
     *
     * @param id 用户登录日志表主键
     * @return 用户登录日志表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary="根据主键获取用户登录日志表")
    public UserLoginLog getInfo(@PathVariable Serializable id) {
        return userLoginLogService.getById(id);
    }

}
