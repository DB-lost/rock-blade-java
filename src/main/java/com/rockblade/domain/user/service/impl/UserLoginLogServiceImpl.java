/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 09:33:03
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/impl/UserLoginLogServiceImpl.java
 * @Description: 用户登录日志表 服务层实现。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.UserLoginLog;
import com.rockblade.infrastructure.mapper.UserLoginLogMapper;
import com.rockblade.domain.user.service.UserLoginLogService;
import org.springframework.stereotype.Service;

@Service("userLoginLogService")
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
        implements UserLoginLogService {

}
