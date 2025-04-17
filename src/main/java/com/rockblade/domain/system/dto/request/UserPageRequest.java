/*
 * @Author: DB
 * @Date: 2025-04-16 14:30:00
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 15:30:25
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/dto/request/UserPageRequest.java
 * @Description: 用户分页查询请求
 */
package com.rockblade.domain.system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户分页查询请求")
public class UserPageRequest {

    /** 昵称 */
    @Schema(description = "昵称")
    private String nickname;

    /** 邮箱 */
    @Schema(description = "邮箱")
    private String email;

    /** 状态 */
    @Schema(description = "状态（1正常 0停用）")
    private String status;

    /** 部门id */
    @Schema(description = "部门id")
    private String deptId;

    /** 角色id */
    @Schema(description = "角色id")
    private String roleId;

}
