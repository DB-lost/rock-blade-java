/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-16 10:19:53
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:15:20
 * @FilePath: /rock-blade-java/rock-blade-common/src/main/java/com/rockblade/common/dto/system/request/DeptRequest.java
 * @Description: 部门请求DTO
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.common.dto.system.request;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rockblade.common.dto.system.response.DeptResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "部门请求DTO")
public class DeptRequest {
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
