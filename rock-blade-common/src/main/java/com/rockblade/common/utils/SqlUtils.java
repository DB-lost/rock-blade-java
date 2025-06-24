/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:33:22
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 09:03:50
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/utils/SqlUtils.java
 * @Description: sql操作工具类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.utils;

import com.rockblade.framework.core.base.entity.PageDomain;
import com.rockblade.framework.core.base.exception.UtilException;

import cn.hutool.core.util.StrUtil;

public class SqlUtils {

  /**
   * sql工具类空参构造器
   *
   * @author DB
   * @since 2024/05/23
   */
  public SqlUtils() {}

  /**
   * SQL工具类实例
   *
   * @author DB
   * @version 1.1.0
   * @since 2024/05/23
   */
  private static class SqlUtilInstance {
    private static final SqlUtils INSTANCE = new SqlUtils();
  }

  /**
   * 获得实例
   *
   * @return {@link SqlUtils }
   * @author DB
   * @since 2024/05/23
   */
  public static SqlUtils getInstance() {
    return SqlUtilInstance.INSTANCE;
  }

  /** 当前记录起始索引 */
  public final String PAGE_NUM = "page";

  /** 每页显示记录数 */
  public final String PAGE_SIZE = "pageSize";

  /** 排序列 */
  public final String ORDER_BY_COLUMN = "orderByColumn";

  /** 排序的方向 "desc" 或者 "asc". */
  public final String IS_ASC = "isAsc";

  /** 分页参数合理化 */
  public final String REASONABLE = "reasonable";

  /** 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序） */
  public final String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

  /**
   * 封装分页对象
   *
   * @return {@link PageDomain }
   * @author DB
   * @since 2024/05/23
   */
  public PageDomain getPageDomain() {
    PageDomain pageDomain = new PageDomain();
    pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
    pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
    pageDomain.setOrderByColumn(
        null == ServletUtils.getParameter(ORDER_BY_COLUMN)
            ? null
            : SqlUtils.getInstance().escapeOrderBySql(ServletUtils.getParameter(ORDER_BY_COLUMN)));
    pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
    pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
    return pageDomain;
  }

  /**
   * 构建页请求
   *
   * @return {@link PageDomain }
   * @author DB
   * @since 2024/05/23
   */
  public PageDomain buildPageRequest() {
    return getPageDomain();
  }

  /**
   * 检查字符，防止注入绕过
   *
   * @param value 价值
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String escapeOrderBySql(String value) {
    if (StrUtil.isNotBlank(value) && !isValidOrderBySql(value)) {
      throw new UtilException("参数不符合规范，不能进行查询");
    }
    return value;
  }

  /**
   * 验证 order by 语法是否符合规范
   *
   * @param value 价值
   * @return boolean
   * @author DB
   * @since 2024/05/23
   */
  public boolean isValidOrderBySql(String value) {
    return value.matches(SQL_PATTERN);
  }

  /**
   * 设置请求排序数据
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String startOrderBy() {
    PageDomain pageDomain = getPageDomain();
    if (StrUtil.isNotBlank(pageDomain.getOrderBy())) {
      return escapeOrderBySql(pageDomain.getOrderBy());
    } else {
      throw new UtilException("获取排序参数失败");
    }
  }
}
