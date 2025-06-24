/*
 * @Author: DB
 * @Date: 2025-04-16 14:30:00
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-13 16:30:28
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/response/UserPageResponse.java
 * @Description: 用户详细信息响应实体
 */
package com.rockblade.domain.system.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户分页信息响应实体")
public class UserPageResponse {

  /** 用户ID */
  @Schema(description = "用户ID")
  private String id;

  /** 用户名 */
  @Schema(description = "用户名")
  private String username;

  /** 昵称 */
  @Schema(description = "昵称")
  private String nickname;

  /** 邮箱 */
  @Schema(description = "邮箱")
  private String email;

  /** 状态 */
  @Schema(description = "状态（1正常 0停用）")
  private String status;

  /** 更新时间 */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Schema(description = "创建时间")
  private LocalDateTime createdAt;

  /** 主部门ID */
  @Schema(description = "主部门ID")
  private String primaryDeptId;

  /** 主部门名称 */
  @Schema(description = "主部门名称")
  private String primaryDeptName;

  /** 部门信息 */
  @Schema(description = "部门信息")
  private List<DeptInfo> deptInfo;

  /** 角色信息 */
  @Schema(description = "角色信息")
  private List<RoleInfo> roleInfo;

  @Data
  @Schema(description = "部门信息")
  public static class DeptInfo {

    /** 部门信息 */
    @Schema(description = "部门信息")
    private String name;

    /** 部门IDs */
    @Schema(description = "部门ID")
    private String id;
  }

  @Data
  @Schema(description = "角色信息")
  public static class RoleInfo {

    /** 角色信息 */
    @Schema(description = "角色信息")
    private String roleName;

    /** 角色IDs */
    @Schema(description = "角色ID")
    private String id;
  }
}
