/*
 * @Author: DB
 * @Date: 2025-04-16 14:38:00
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-16 14:53:11
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/system/service/impl/UserDeptServiceImpl.java
 * @Description: 用户部门关联服务实现
 */
package com.rockblade.domain.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.system.entity.UserDept;
import com.rockblade.domain.system.service.UserDeptService;
import com.rockblade.infrastructure.mapper.UserDeptMapper;

import static com.rockblade.domain.system.entity.table.UserDeptTableDef.USER_DEPT;

@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService {

    @Override
    public UserDept getPrimaryDept(String userId) {
        return this.getOne(QueryWrapper.create()
                .where(USER_DEPT.USER_ID.eq(userId))
                .and(USER_DEPT.IS_PRIMARY.eq(true)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPrimaryDept(String userId) {
        // 更新用户的所有部门关联为非主部门
        this.update(UserDept.builder().isPrimary(false).build(),
                QueryWrapper.create()
                        .where(USER_DEPT.USER_ID.eq(userId))
                        .and(USER_DEPT.IS_PRIMARY.eq(true)));
    }
}
