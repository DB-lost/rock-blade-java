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
import com.rockblade.domain.system.entity.ExportTask;
import com.rockblade.domain.system.service.ExportTaskService;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.List;

/**
 * 数据导出任务表 控制层。
 *
 * @author 
 * @since 2025-05-21
 */
@RestController
@Tag(name = "数据导出任务表接口")
@RequestMapping("/exportTask")
public class ExportTaskController {

    @Autowired
    private ExportTaskService exportTaskService;

    /**
     * 分页查询数据导出任务表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Operation(summary="分页查询数据导出任务表")
    public Page<ExportTask> page(@Parameter(description="分页信息")Page<ExportTask> page) {
        return exportTaskService.page(page);
    }

    /**
     * 添加数据导出任务表。
     *
     * @param exportTask 数据导出任务表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    @Operation(summary="保存数据导出任务表")
    public boolean save(@RequestBody @Parameter(description="数据导出任务表")ExportTask exportTask) {
        return exportTaskService.save(exportTask);
    }

    /**
     * 根据主键删除数据导出任务表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    @Operation(summary="根据主键数据导出任务表")
    public boolean remove(@PathVariable @Parameter(description="数据导出任务表主键")Serializable id) {
        return exportTaskService.removeById(id);
    }

    /**
     * 根据主键更新数据导出任务表。
     *
     * @param exportTask 数据导出任务表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    @Operation(summary="根据主键更新数据导出任务表")
    public boolean update(@RequestBody @Parameter(description="数据导出任务表主键")ExportTask exportTask) {
        return exportTaskService.updateById(exportTask);
    }

    /**
     * 查询所有数据导出任务表。
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    @Operation(summary="查询所有数据导出任务表")
    public List<ExportTask> list() {
        return exportTaskService.list();
    }

    /**
     * 根据数据导出任务表主键获取详细信息。
     *
     * @param id 数据导出任务表主键
     * @return 数据导出任务表详情
     */
    @GetMapping("/getInfo/{id}")
    @Operation(summary="根据主键获取数据导出任务表")
    public ExportTask getInfo(@PathVariable Serializable id) {
        return exportTaskService.getById(id);
    }

}
