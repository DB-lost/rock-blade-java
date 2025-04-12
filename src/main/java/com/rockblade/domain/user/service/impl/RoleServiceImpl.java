/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-12 10:10:52
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/impl/RoleServiceImpl.java
 * @Description: 角色信息表 服务层实现。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.Role;
import com.rockblade.infrastructure.mapper.RoleMapper;
import com.rockblade.domain.user.service.RoleService;
import org.springframework.stereotype.Service;

import static com.rockblade.domain.user.entity.table.RoleTableDef.ROLE;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Role getRoleByKey(String roleKey) {
        return queryChain()
                .where(ROLE.ROLE_KEY.eq(roleKey))
                .one();
    }

}
