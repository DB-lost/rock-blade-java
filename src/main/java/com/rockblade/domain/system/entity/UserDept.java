package com.rockblade.domain.system.entity;

import java.io.Serializable;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户和部门关联表 实体类。
 *
 * @author
 * @since 2025-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(
    value = "sys_user_dept",
    onInsert = BaseInsertListener.class,
    onUpdate = BaseUpdateListener.class,
    mapperGenerateEnable = false)
public class UserDept extends BaseEntity implements Serializable {

  /** 用户ID */
  @Id private String userId;

  /** 部门ID */
  @Id private String deptId;

  /** 是否为主部门 */
  private Boolean isPrimary;
}
