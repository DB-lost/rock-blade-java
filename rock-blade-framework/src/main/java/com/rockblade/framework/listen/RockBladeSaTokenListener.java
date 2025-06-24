/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 20:22:04
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 20:23:09
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/listen/RockBladeSaTokenListener.java
 * @Description: SaToken监听器
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.listen;

import org.springframework.stereotype.Component;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;

@Component
public class RockBladeSaTokenListener implements SaTokenListener {

  /**
   * 每次登录时触发
   *
   * @param loginType 登录类型
   * @param loginId 登录id
   * @param tokenValue token
   * @param loginModel 登录模块
   * @author DB
   * @since 2024/3/20
   */
  @Override
  public void doLogin(
      String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
    System.out.println("---------- 自定义侦听器实现 doLogin");
  }

  /**
   * 每次注销时触发
   *
   * @author DB
   * @since 2024/3/20
   * @param loginType 登录类型
   * @param loginId 登录id
   * @param tokenValue token
   */
  @Override
  public void doLogout(String loginType, Object loginId, String tokenValue) {
    System.out.println("---------- 自定义侦听器实现 doLogout");
  }

  /**
   * 每次被踢下线时触发
   *
   * @param loginType 登录类型
   * @param loginId 登录id
   * @param tokenValue 令牌值
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doKickout(String loginType, Object loginId, String tokenValue) {
    System.out.println("---------- 自定义侦听器实现 doKickout");
  }

  /**
   * 每次被顶下线时触发
   *
   * @param loginType 登录类型
   * @param loginId 登录id
   * @param tokenValue 令牌值
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doReplaced(String loginType, Object loginId, String tokenValue) {
    System.out.println("---------- 自定义侦听器实现 doReplaced");
  }

  /**
   * 每次被封禁时触发
   *
   * @param loginType 登录类型
   * @param loginId 登录id
   * @param service 服务
   * @param level 水平
   * @param disableTime 禁用时间
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doDisable(
      String loginType, Object loginId, String service, int level, long disableTime) {
    System.out.println("---------- 自定义侦听器实现 doDisable");
  }

  /**
   * 每次被解封时触发
   *
   * @param loginType 登录类型
   * @param loginId 登录id
   * @param service 服务
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doUntieDisable(String loginType, Object loginId, String service) {
    System.out.println("---------- 自定义侦听器实现 doUntieDisable");
  }

  /**
   * 每次二级认证时触发
   *
   * @param loginType 登录类型
   * @param tokenValue 令牌值
   * @param service 服务
   * @param safeTime 安全时间
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
    System.out.println("---------- 自定义侦听器实现 doOpenSafe");
  }

  /**
   * 每次退出二级认证时触发
   *
   * @param loginType 登录类型
   * @param tokenValue 令牌值
   * @param service 服务
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doCloseSafe(String loginType, String tokenValue, String service) {
    System.out.println("---------- 自定义侦听器实现 doCloseSafe");
  }

  /**
   * 每次创建Session时触发
   *
   * @param id id
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doCreateSession(String id) {
    System.out.println("---------- 自定义侦听器实现 doCreateSession");
  }

  /**
   * 每次注销Session时触发
   *
   * @param id id
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doLogoutSession(String id) {
    System.out.println("---------- 自定义侦听器实现 doLogoutSession");
  }

  /**
   * 每次Token续期时触发
   *
   * @param tokenValue 令牌值
   * @param loginId 登录id
   * @param timeout 超时
   * @author DB
   * @since 2024/05/23
   */
  @Override
  public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
    System.out.println("---------- 自定义侦听器实现 doRenewTimeout");
  }
}
