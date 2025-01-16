/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-15 21:27:24
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/base/entity/BaseInsertListener.java
 * @Description: 基础插入侦听器
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.framework.core.base.entity;

import java.time.LocalDateTime;

import com.mybatisflex.annotation.InsertListener;
import com.rockblade.framework.core.constants.Constants;

import cn.dev33.satoken.exception.SaTokenContextException;
import cn.dev33.satoken.stp.StpUtil;

public class BaseInsertListener implements InsertListener {

  /**
   * 新增
   *
   * @param entity 实体
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void onInsert(Object entity) {
    BaseEntity baseEntity = (BaseEntity) entity;
    // 设置 account 被新增时的一些默认数据
    String userId;
    try {
      userId =
          StpUtil.getLoginIdDefaultNull() == null
              ? Constants.SUPER_ADMIN_ID
              : StpUtil.getLoginIdDefaultNull().toString();
    } catch (SaTokenContextException e) {
      userId = Constants.SUPER_ADMIN_ID;
    }
    baseEntity.setCreateTime(LocalDateTime.now());
    baseEntity.setCreateUserId(userId);
    baseEntity.setUpdateTime(LocalDateTime.now());
    baseEntity.setUpdateUserId(userId);
  }
}
