/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 09:32:38
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/impl/MenuServiceImpl.java
 * @Description: 菜单权限表 服务层实现。
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.Menu;
import com.rockblade.infrastructure.mapper.MenuMapper;
import com.rockblade.domain.user.service.MenuService;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
