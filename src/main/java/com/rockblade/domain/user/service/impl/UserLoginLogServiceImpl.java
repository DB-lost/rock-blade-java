package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.UserLoginLog;
import com.rockblade.infrastructure.mapper.UserLoginLogMapper;
import com.rockblade.domain.user.service.UserLoginLogService;
import org.springframework.stereotype.Service;

/**
 * 用户登录日志表 服务层实现。
 *
 * @author 
 * @since 2025-04-11
 */
@Service("userLoginLogService")
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {

}
