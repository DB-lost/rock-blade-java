/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:27:58
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:49:16
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/entity/UserLoginLog.java
 * @Description: 用户登录日志表 实体类。
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.entity;

import java.io.Serializable;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(value = "sys_user_login_log", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)
public class UserLoginLog extends BaseEntity implements Serializable {

  /** 主键ID */
  @Id
  private String id;

  /** 用户ID */
  private String userId;

  /** 登录方式 */
  private String loginType;

  /** 登录IP */
  private String ipAddress;

  /** 设备类型 */
  private String deviceType;

  /** 操作系统 */
  private String osName;

  /** 浏览器 */
  private String browser;

  /** 登录地点 */
  private String location;

  /** 登录状态 */
  private String status;

  /** 登录消息 */
  private String msg;
}
