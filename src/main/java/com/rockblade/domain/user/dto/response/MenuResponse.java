/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 22:21:37
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-14 12:55:52
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/dto/response/MenuResponse.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.dto.response;

import com.rockblade.domain.user.dto.request.MenuRequest;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuResponse extends MenuRequest {
    /**
     * 子菜单列表
     */
    private List<MenuResponse> children;

    /**
     * 是否存在子菜单
     */
    private Boolean hasChildren;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 更新时间
     */
    private String updatedAt;

    /**
     * 创建人ID
     */
    private Long createdBy;

    /**
     * 更新人ID
     */
    private Long updatedBy;
}
