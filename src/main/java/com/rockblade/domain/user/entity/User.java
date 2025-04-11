/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 00:13:16
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-04-11 10:50:07
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/domain/user/entity/User.java
 * @Description: 用户信息表
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.domain.user.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;
import java.io.Serializable;
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

    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /** 头像 */
    private String avatar;

    /**
     * 是否删除
     */
    private Boolean deleted;

}
