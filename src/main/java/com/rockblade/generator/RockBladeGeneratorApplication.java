/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 21:43:20
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-03-16 22:34:10
 * @FilePath: /rock-blade-admin-java/home/db/Workspace/Template-Workspace/rock-blade-java/src/main/java/com/rockblade/generator/RockBladeGeneratorApplication.java
 * @Description: 代码生成器
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.generator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.TableConfig;
import com.mybatisflex.core.exception.MybatisFlexException;
import com.rockblade.framework.core.base.entity.BaseEntity;
import com.rockblade.framework.core.base.entity.BaseInsertListener;
import com.rockblade.framework.core.base.entity.BaseUpdateListener;
import com.zaxxer.hikari.HikariDataSource;

import io.github.cdimascio.dotenv.Dotenv;
import io.micrometer.common.util.StringUtils;

public class RockBladeGeneratorApplication {

  private static final Dotenv dotenv = Dotenv.load();

  /** 数据库 URL */
  private static final String URL = dotenv.get("DEV_DATABASE_URL");

  /** 数据库用户名 */
  private static final String USERNAME = dotenv.get("DEV_DATABASE_USERNAME");

  /** 数据库密码 */
  private static final String PASSWORD = dotenv.get("DEV_DATABASE_PASSWORD");

  /** 输出路径 */
  private static final String EXPORT_URL = "";

  /** 模块 */
  private static final String EXPORT_ITEM = "";

  /** 表前缀 */
  private static final String TABLE_PREFIX = "";

  /**
   * 主入口
   *
   * @param args 主入口参数
   * @author DB
   * @since 2024/05/23
   */
  public static void main(String[] args) {
    // 配置数据源
    try (HikariDataSource dataSource = new HikariDataSource()) {
      dataSource.setJdbcUrl(URL);
      dataSource.setUsername(USERNAME);
      dataSource.setPassword(PASSWORD);
      // 创建配置内容，两种风格都可以。
      GlobalConfig globalConfig = createGlobalConfig();
      // 通过 datasource 和 globalConfig 创建代码生成器
      Generator generator = new Generator(dataSource, globalConfig);
      // 生成代码
      generator.generate();
    }
  }

  /**
   * 命令行输入表名
   *
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  private static String scanner() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("请输入表名，多个英文逗号分割：");
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isNotBlank(ipt)) {
        return ipt;
      }
    }
    throw new MybatisFlexException("请输入正确的表名，多个英文逗号分割！");
  }

  /**
   * 构建代码生成配置
   *
   * @return GlobalConfig
   * @author DB
   * @since 2023/8/4 0004 14:05
   */
  private static GlobalConfig createGlobalConfig() {
    String[] tables = scanner().split(",");
    // 创建配置内容
    GlobalConfig globalConfig = new GlobalConfig();

    // 设置注释配置
    globalConfig
        .getJavadocConfig()
        .setAuthor("")
        .setSince(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    // 设置包配置
    globalConfig
        .getPackageConfig()
        .setSourceDir(System.getProperty("user.dir") + EXPORT_URL + "/src/main/java")
        .setBasePackage("com.rockblade." + EXPORT_ITEM + ".business")
        .setMapperXmlPath(
            System.getProperty("user.dir") + EXPORT_URL + "/src/main/resources/mapper");
    // 设置策略配置
    globalConfig
        .getStrategyConfig()
        .setTablePrefix(TABLE_PREFIX)
        .setLogicDeleteColumn("is_delete")
        .setGenerateTable(tables);
    // 设置模板配置
    globalConfig
        .getTemplateConfig()
        .setEntity(System.getProperty("user.dir") + "/src/main/resources/template/enjoy/entity.tpl")
        .setMapper(System.getProperty("user.dir") + "/src/main/resources/template/enjoy/mapper.tpl")
        .setService(
            System.getProperty("user.dir") + "/src/main/resources/template/enjoy/service.tpl")
        .setServiceImpl(
            System.getProperty("user.dir") + "/src/main/resources/template/enjoy/serviceImpl.tpl")
        .setController(
            System.getProperty("user.dir") + "/src/main/resources/template/enjoy/controller.tpl")
        .setMapperXml(
            System.getProperty("user.dir") + "/src/main/resources/template/enjoy/mapperXml.tpl");
    // Entity 生成配置
    globalConfig
        .getEntityConfig()
        .setSuperClass(BaseEntity.class)
        .setWithLombok(true)
        .setWithSwagger(true)
        .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);
    // 开启 Entity 的生成
    globalConfig.enableEntity();
    // 开启 Mapper 的生成
    globalConfig.enableMapper();
    // 开启 Service 的生成
    globalConfig.enableService();
    // 开启 ServiceImpl 的生成
    globalConfig.enableServiceImpl();
    // 开启 Controller 的生成
    globalConfig.enableController();
    // 开启 xml 的生成
    globalConfig.enableMapperXml();
    // 遍历设置表配置
    Arrays.asList(tables)
        .forEach(
            item -> {
              TableConfig tableConfig = new TableConfig();
              tableConfig.setInsertListenerClass(BaseInsertListener.class);
              tableConfig.setUpdateListenerClass(BaseUpdateListener.class);
              tableConfig.setTableName(item);
              tableConfig.setMapperGenerateEnable(false);
              globalConfig.setTableConfig(tableConfig);
            });
    return globalConfig;
  }
}
