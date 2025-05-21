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
 * 告警规则表 实体类。
 *
 * @author
 * @since 2025-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(
    value = "sys_alert_rule",
    onInsert = BaseInsertListener.class,
    onUpdate = BaseUpdateListener.class,
    mapperGenerateEnable = false)
public class AlertRule extends BaseEntity implements Serializable {

  /** 规则ID */
  @Id private String id;

  /** 规则名称 */
  private String name;

  /** 监控指标 */
  private String metric;

  /** 运算符 */
  private String operator;

  /** 阈值 */
  private Double threshold;

  /** 持续时间 */
  private String duration;

  /** 严重程度 */
  private String severity;

  /** 是否启用 */
  private Boolean enabled;

  /** 告警接收人 */
  private String receivers;

  /** 告警通知模板 */
  private String template;

  /** 是否删除 */
  private Boolean deleted;

  /** 备注 */
  private String remark;
}
