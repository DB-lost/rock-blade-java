/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 00:13:16
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 15:06:21
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/entity/User.java
 * @Description: 用户信息表
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.entity;

import java.io.Serializable;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.common.enums.UserType;
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
@Table(value = "sys_user", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)
public class User extends BaseEntity implements Serializable {

  /** 主键ID */
  @Id
  private String id;

  /** 用户名 */
  private String username;

  /** 昵称 */
  private String nickname;

  /** 密码 */
  private String password;

  /** 手机号 */
  private String phone;

  /** 邮箱 */
  private String email;

  /** 头像 */
  private String avatar;

  /** 状态 */
  private String status;

  /** 用户类型(admin/user/guest) */
  private UserType userType;

  /** 是否删除 */
  private Boolean deleted;
}
