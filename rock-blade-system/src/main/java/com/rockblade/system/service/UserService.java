/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-11 09:43:06
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:54:39
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/UserService.java
 * @Description: 用户服务接口
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import com.rockblade.common.dto.request.EmailCodeRequest;
import com.rockblade.common.dto.request.EmailLoginRequest;
import com.rockblade.common.dto.request.LoginRequest;
import com.rockblade.common.dto.request.RegisterRequest;
import com.rockblade.common.dto.request.ResetPasswordRequest;
import com.rockblade.common.dto.request.UserDetailsInfoRequest;
import com.rockblade.common.dto.request.UserPageRequest;
import com.rockblade.common.dto.request.UserRequest;
import com.rockblade.common.dto.request.VerifyEmailCodeRequest;
import com.rockblade.common.dto.response.PublicKeyResponse;
import com.rockblade.common.dto.response.UserPageResponse;
import com.rockblade.system.entity.User;

public interface UserService extends IService<User> {

  /**
   * 获取RSA公钥
   *
   * @param request 请求参数
   * @return PublicKeyResponse
   */
  PublicKeyResponse getPublicKey(String nonce);

  /**
   * 注册
   *
   * @param request 请求参数
   */
  void register(RegisterRequest request);

  /**
   * 登录
   *
   * @param request 请求参数
   * @return String
   */
  String login(LoginRequest request);

  /**
   * 重置密码
   *
   * @param request 请求参数
   */
  void resetPassword(ResetPasswordRequest request);

  /**
   * 发送邮箱验证码
   *
   * @param request 请求参数
   */
  void sendEmailCode(EmailCodeRequest request);

  /**
   * 校验邮箱验证码
   *
   * @param request 请求参数
   */
  void verifyEmailCode(VerifyEmailCodeRequest request);

  /**
   * 邮箱登录
   *
   * @param request 请求参数
   * @return String
   */
  String emailLogin(EmailLoginRequest request);

  /**
   * 分页查询用户列表
   *
   * @param request 分页请求参数
   * @return 用户分页数据
   */
  Page<UserPageResponse> page(UserPageRequest request);

  /**
   * 新增用户
   *
   * @param request 用户信息
   */
  void add(UserRequest request);

  /**
   * 修改用户
   *
   * @param request 用户信息
   */
  void edit(UserRequest request);

  /**
   * 删除用户
   *
   * @param ids 用户ID数组
   */
  void remove(String[] ids);

  /**
   * 给用户分配部门
   *
   * @param userId    用户ID
   * @param deptId    部门ID
   * @param isPrimary 是否为主部门
   */
  void assignDept(String userId, String deptId, Boolean isPrimary);

  /**
   * 移除用户部门关联
   *
   * @param userId 用户ID
   * @param deptId 部门ID
   */
  void removeDept(String userId, String deptId);

  /**
   * 给用户分配角色
   *
   * @param userId 用户ID
   * @param roleId 角色ID
   */
  void assignRole(String userId, String roleId);

  /**
   * 移除用户角色关联
   *
   * @param userId 用户ID
   * @param roleId 角色ID
   */
  void removeRole(String userId, String roleId);

  /**
   * 更新用户详情
   *
   * @param request 用户状态请求参数
   */
  void updateUserDetails(UserDetailsInfoRequest request);
}
