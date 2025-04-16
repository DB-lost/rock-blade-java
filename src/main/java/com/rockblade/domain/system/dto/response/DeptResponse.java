/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-16 10:20:15
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 11:09:40
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/response/DeptResponse.java
 * @Description: 部门响应DTO
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.system.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "部门响应DTO")
public class DeptResponse {

    /** 部门ID */
    @Schema(description = "部门ID")
    private String id;

    /** 部门名称 */
    @Schema(description = "部门名称")
    private String name;

    /** 上级部门ID */
    @Schema(description = "上级部门ID")
    private String pid;

    /** 排序 */
    @Schema(description = "排序")
    private Integer order;

    /** 部门负责人 */
    @Schema(description = "部门负责人")
    private String leader;

    /** 部门负责人邮箱 */
    @Schema(description = "部门负责人邮箱")
    private String email;

    /** 部门状态 */
    @Schema(description = "部门状态")
    private String status;

    /** 子部门 */
    @Schema(description = "子部门")
    private List<DeptResponse> children;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /** 备注 */
    @Schema(description = "备注")
    private String remark;

}
