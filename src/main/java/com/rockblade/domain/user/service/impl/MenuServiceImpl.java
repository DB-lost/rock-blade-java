package com.rockblade.domain.user.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.rockblade.domain.user.entity.Menu;
import com.rockblade.infrastructure.mapper.MenuMapper;
import com.rockblade.domain.user.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单权限表 服务层实现。
 *
 * @author 
 * @since 2025-04-11
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
