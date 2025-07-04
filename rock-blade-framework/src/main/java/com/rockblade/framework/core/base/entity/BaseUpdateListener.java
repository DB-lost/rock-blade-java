/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 14:33:40
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/core/base/entity/BaseUpdateListener.java
 * @Description: 基础更新监听器
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.base.entity;

import java.time.LocalDateTime;

import com.mybatisflex.annotation.UpdateListener;
import com.rockblade.common.constants.Constants;

import cn.dev33.satoken.exception.SaTokenContextException;
import cn.dev33.satoken.stp.StpUtil;

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
      userId =
          StpUtil.getLoginIdDefaultNull() == null
              ? Constants.SUPER_ADMIN_ID
              : StpUtil.getLoginIdAsString();
    } catch (SaTokenContextException e) {
      userId = Constants.SUPER_ADMIN_ID;
    }
    // 设置 account 被更新时的一些默认数据
    baseEntity.setUpdatedAt(LocalDateTime.now());
    baseEntity.setUpdatedBy(userId);
  }
}
