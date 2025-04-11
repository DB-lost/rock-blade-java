/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 00:13:16
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 00:15:53
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/impl/UserServiceImpl.java
 * @Description: 用户服务层实现类。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.User;
import com.rockblade.infrastructure.mapper.UserMapper;
import com.rockblade.domain.user.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
