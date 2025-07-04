/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-04-16 14:37:00
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:54:10
 * @FilePath: /rock-blade-java/rock-blade-system/src/main/java/com/rockblade/system/service/UserDeptService.java
 * @Description: 用户部门关联服务接口
 */
package com.rockblade.system.service;

import com.mybatisflex.core.service.IService;
import com.rockblade.system.entity.UserDept;

public interface UserDeptService extends IService<UserDept> {

  /**
   * 获取用户的主部门信息
   *
   * @param userId 用户ID
   * @return 主部门关联信息
   */
  UserDept getPrimaryDept(String userId);

  /**
   * 重置用户的主部门设置（将所有部门设置为非主部门）
   *
   * @param userId 用户ID
   */
  void resetPrimaryDept(String userId);
}
