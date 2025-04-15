/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-12 11:36:57
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-12 14:59:08
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/service/impl/StpInterfaceImpl.java
 * @Description: 自定义权限加载接口实现类
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.dev33.satoken.stp.StpInterface;

@Component // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        list.add("playground:view");
        list.add("playground:history:view");
        list.add("playground:starred:view");
        list.add("playground:settings:view");
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<String>();
        list.add("admin");
        list.add("super-admin");
        return list;
    }

}
