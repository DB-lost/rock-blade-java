package com.rockblade.domain.user.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 部门表 实体类。
 *
 * @author 
 * @since 2025-04-16
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(value = "sys_dept", onInsert = BaseInsertListener.class, onUpdate = BaseUpdateListener.class, mapperGenerateEnable = false)

public class Dept extends BaseEntity implements Serializable {

    /**
     * 部门ID
     */
    @Id
    private String id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门ID
     */
    private String pid;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 显示顺序
     */
    private Integer order;

    /**
     * 部门状态（1正常 0停用）
     */
    private String status;

    /**
     * 是否删除
     */
    private Boolean deleted;

}
