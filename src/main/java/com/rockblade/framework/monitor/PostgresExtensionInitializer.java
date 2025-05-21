/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-21 14:14:37
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-21 14:17:05
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/monitor/PostgresExtensionInitializer.java
 * @Description: PostgreSQL扩展初始化器 用于在应用启动时检查和配置所需的PostgreSQL扩展
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package com.rockblade.framework.monitor;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PostgresExtensionInitializer {

    private final JdbcTemplate jdbcTemplate;

    public PostgresExtensionInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void initializePostgresExtensions() {
        try {
            // 检查pg_stat_statements扩展是否已安装
            Boolean extensionExists = jdbcTemplate.queryForObject(
                    "SELECT EXISTS (SELECT 1 FROM pg_extension WHERE extname = 'pg_stat_statements')",
                    Boolean.class);

            if (Boolean.FALSE.equals(extensionExists)) {
                log.warn("pg_stat_statements扩展未安装，尝试安装...");
                try {
                    // 创建扩展
                    jdbcTemplate.execute("CREATE EXTENSION IF NOT EXISTS pg_stat_statements");
                    log.info("pg_stat_statements扩展安装成功");

                    // 配置扩展参数
                    jdbcTemplate.execute("ALTER SYSTEM SET shared_preload_libraries = 'pg_stat_statements'");
                    jdbcTemplate.execute("ALTER SYSTEM SET pg_stat_statements.track = 'all'");
                    jdbcTemplate.execute("ALTER SYSTEM SET pg_stat_statements.max = 10000");

                    log.warn("需要重启PostgreSQL服务器以激活pg_stat_statements扩展");
                } catch (Exception e) {
                    log.error("安装pg_stat_statements扩展失败，请手动安装: " + e.getMessage());
                    log.info("手动安装步骤：");
                    log.info("1. 编辑postgresql.conf，添加：shared_preload_libraries = 'pg_stat_statements'");
                    log.info("2. 重启PostgreSQL服务");
                    log.info("3. 执行SQL：CREATE EXTENSION pg_stat_statements;");
                }
            } else {
                log.info("pg_stat_statements扩展已安装");

                // 检查扩展是否正确配置
                Boolean isEnabled = jdbcTemplate.queryForObject(
                        "SELECT count(*) > 0 FROM pg_settings WHERE name = 'pg_stat_statements.track' AND setting = 'all'",
                        Boolean.class);

                if (Boolean.FALSE.equals(isEnabled)) {
                    log.warn("pg_stat_statements扩展未正确配置，尝试更新配置...");
                    jdbcTemplate.execute("ALTER SYSTEM SET pg_stat_statements.track = 'all'");
                    jdbcTemplate.execute("ALTER SYSTEM SET pg_stat_statements.max = 10000");
                    log.warn("配置已更新，需要重启PostgreSQL服务器以生效");
                } else {
                    log.info("pg_stat_statements扩展配置正确");
                }
            }

            // 重置统计信息
            jdbcTemplate.execute("SELECT pg_stat_statements_reset()");
            log.info("已重置pg_stat_statements统计信息");

        } catch (Exception e) {
            log.error("初始化PostgreSQL扩展时发生错误: " + e.getMessage(), e);
        }
    }
}
