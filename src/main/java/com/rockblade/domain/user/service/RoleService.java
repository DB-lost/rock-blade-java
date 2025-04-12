/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-12 09:36:02
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/RoleService.java
 * @Description: 角色信息表 服务层。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service;

import com.mybatisflex.core.service.IService;
import com.rockblade.domain.user.entity.Role;

public interface RoleService extends IService<Role> {
    /**
     * 根据角色标识获取角色信息
     *
     * @param roleKey 角色标识
     * @return 角色信息
     */
    Role getRoleByKey(String roleKey);

}
