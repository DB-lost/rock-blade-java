/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 21:06:09
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-06-24 13:29:22
 * @FilePath: /rock-blade-java/rock-blade-framework/src/main/java/com/rockblade/framework/core/base/entity/PageDomain.java
 * @Description: 分页数据
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.base.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

@Getter
public class PageDomain {

  /** 当前记录起始索引 */
  private Integer page;

  /** 每页显示记录数 */
  private Integer pageSize;

  /** 排序列 */
  private String orderByColumn;

  /** 排序的方向desc或者asc */
  private String isAsc = "asc";

  /** 分页参数合理化 */
  private Boolean reasonable = true;

  /**
   * 获得订单
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String getOrderBy() {
    if (StrUtil.isBlank(orderByColumn)) {
      return "";
    }
    return StrUtil.toUnderlineCase(orderByColumn) + " " + isAsc;
  }

  /**
   * 设置页码
   *
   * @param pageNum 页面num
   * @author DB
   * @since 2024/05/23
   */
  public void setPageNum(Integer pageNum) {
    this.page = pageNum;
  }

  /**
   * 设置页面大小
   *
   * @param pageSize 页面大小
   * @author DB
   * @since 2024/05/23
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * 按列设置顺序
   *
   * @param orderByColumn 按列排序
   * @author DB
   * @since 2024/05/23
   */
  public void setOrderByColumn(String orderByColumn) {
    this.orderByColumn = orderByColumn;
  }

  /**
   * 是asc。
   *
   * @param isAsc 是asc
   * @author DB
   * @since 2024/05/23
   */
  public void setIsAsc(String isAsc) {
    if (StrUtil.isNotBlank(isAsc)) {
      // 兼容前端排序类型
      if ("ascending".equals(isAsc)) {
        isAsc = "asc";
      } else if ("descending".equals(isAsc)) {
        isAsc = "desc";
      }
      this.isAsc = isAsc;
    }
  }

  /**
   * 设置合理
   *
   * @param reasonable 合理
   * @author DB
   * @since 2024/05/23
   */
  public void setReasonable(Boolean reasonable) {
    this.reasonable = reasonable;
  }
}
