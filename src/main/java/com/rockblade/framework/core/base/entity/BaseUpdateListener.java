/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-15 21:27:29
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/base/entity/BaseUpdateListener.java
 * @Description: 基础更新监听器
 * 
 * Copyright (c) 2025 by DB, All Rights Reserved. 
 */
package com.rockblade.framework.core.base.entity;

import cn.dev33.satoken.exception.SaTokenContextException;
import cn.dev33.satoken.stp.StpUtil;
import com.mybatisflex.annotation.UpdateListener;
import com.rockblade.framework.core.constants.Constants;

import java.time.LocalDateTime;

public class BaseUpdateListener implements UpdateListener {

    /**
     * 更新
     *
     * @param entity 实体
     * @author DB
     * @since 2024/05/23
     */
    @Override
    public void onUpdate(Object entity) {
        BaseEntity baseEntity = (BaseEntity) entity;
        String userId;
        try {
            userId = StpUtil.getLoginIdDefaultNull() == null ? Constants.SUPER_ADMIN_ID : StpUtil.getLoginIdDefaultNull().toString();
        } catch (SaTokenContextException e) {
            userId = Constants.SUPER_ADMIN_ID;
        }
        //设置 account 被更新时的一些默认数据
        baseEntity.setUpdateTime(LocalDateTime.now());
        baseEntity.setUpdateUserId(userId);
    }
}
