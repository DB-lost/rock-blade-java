package com.rockblade.framework.monitor;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库监控服务
 * 用于收集PostgreSQL数据库性能指标、慢查询统计等
 */
@Slf4j
@Component
public class DatabaseMonitor {

  private final MeterRegistry meterRegistry;
  private final JdbcTemplate jdbcTemplate;
  private final HikariDataSource dataSource;

  public DatabaseMonitor(MeterRegistry meterRegistry, JdbcTemplate jdbcTemplate, HikariDataSource dataSource) {
    this.meterRegistry = meterRegistry;
    this.jdbcTemplate = jdbcTemplate;
    this.dataSource = dataSource;
    initializeMetrics();
  }

  private void initializeMetrics() {
    // 连接池指标
    meterRegistry.gauge("hikaricp.connections.max", dataSource, HikariDataSource::getMaximumPoolSize);
    meterRegistry.gauge("hikaricp.connections.min", dataSource, HikariDataSource::getMinimumIdle);
    meterRegistry.gauge("hikaricp.connections.timeout", dataSource, HikariDataSource::getConnectionTimeout);
    meterRegistry.gauge("hikaricp.connections.active",
        dataSource.getHikariPoolMXBean(),
        bean -> bean.getActiveConnections());
    meterRegistry.gauge("hikaricp.connections.idle",
        dataSource.getHikariPoolMXBean(),
        bean -> bean.getIdleConnections());
  }

  /**
   * 每5分钟收集一次慢查询统计
   * 需要在PostgreSQL中启用pg_stat_statements扩展
   */
  @Scheduled(fixedRate = 300000)
  public void collectSlowQueryMetrics() {
    try {
      // 首先确保pg_stat_statements扩展已安装
      String checkExtensionSql = "SELECT EXISTS (SELECT 1 FROM pg_extension WHERE extname = 'pg_stat_statements')";
      Boolean extensionExists = jdbcTemplate.queryForObject(checkExtensionSql, Boolean.class);

      if (Boolean.TRUE.equals(extensionExists)) {
        String slowQuerySql = """
            SELECT datname as schema_name,
                   query as query_pattern,
                   calls as execution_count,
                   total_exec_time/calls as avg_latency_ms,
                   max_exec_time as max_latency_ms,
                   mean_exec_time as mean_latency_ms
            FROM pg_stat_statements
            JOIN pg_database ON pg_stat_statements.dbid = pg_database.oid
            WHERE total_exec_time/calls > 1000  -- 平均执行时间超过1秒
            ORDER BY total_exec_time/calls DESC
            LIMIT 10
            """;

        List<Map<String, Object>> slowQueries = jdbcTemplate.queryForList(slowQuerySql);

        for (Map<String, Object> query : slowQueries) {
          String schemaName = (String) query.get("schema_name");
          String queryPattern = (String) query.get("query_pattern");
          Double avgLatency = (Double) query.get("avg_latency_ms");
          Double maxLatency = (Double) query.get("max_latency_ms");
          Long execCount = (Long) query.get("execution_count");

          // 记录慢查询指标
          String truncatedQuery = queryPattern.substring(0, Math.min(queryPattern.length(), 100));
          Tags tags = Tags.of("schema", schemaName, "query", truncatedQuery);

          meterRegistry.gauge("postgres.slow_query.avg_latency", tags, avgLatency);
          meterRegistry.gauge("postgres.slow_query.max_latency", tags, maxLatency);
          meterRegistry.gauge("postgres.slow_query.execution_count", tags, execCount);
        }
      } else {
        log.warn("pg_stat_statements扩展未安装，无法收集SQL性能统计信息");
      }
    } catch (Exception e) {
      log.error("收集慢查询统计失败: " + e.getMessage(), e);
    }
  }

  /**
   * 每分钟收集一次数据库性能指标
   */
  @Scheduled(fixedRate = 60000)
  public void collectPerformanceMetrics() {
    try {
      // 收集数据库统计信息
      String dbStatsSql = """
          SELECT * FROM pg_stat_database
          WHERE datname = current_database()
          """;

      List<Map<String, Object>> dbStats = jdbcTemplate.queryForList(dbStatsSql);

      if (!dbStats.isEmpty()) {
        Map<String, Object> stats = dbStats.get(0);
        Tags dbTags = Tags.of("database", (String) stats.get("datname"));

        // 记录数据库级别的统计信息
        meterRegistry.gauge("postgres.stats.commits", dbTags, ((Number) stats.get("xact_commit")).longValue());
        meterRegistry.gauge("postgres.stats.rollbacks", dbTags, ((Number) stats.get("xact_rollback")).longValue());
        meterRegistry.gauge("postgres.stats.blocks_read", dbTags, ((Number) stats.get("blks_read")).longValue());
        meterRegistry.gauge("postgres.stats.blocks_hit", dbTags, ((Number) stats.get("blks_hit")).longValue());
        meterRegistry.gauge("postgres.stats.rows_returned", dbTags, ((Number) stats.get("tup_returned")).longValue());
        meterRegistry.gauge("postgres.stats.rows_fetched", dbTags, ((Number) stats.get("tup_fetched")).longValue());
        meterRegistry.gauge("postgres.stats.rows_inserted", dbTags, ((Number) stats.get("tup_inserted")).longValue());
        meterRegistry.gauge("postgres.stats.rows_updated", dbTags, ((Number) stats.get("tup_updated")).longValue());
        meterRegistry.gauge("postgres.stats.rows_deleted", dbTags, ((Number) stats.get("tup_deleted")).longValue());
      }

      // 收集连接统计
      String connectionStatsSql = """
          SELECT count(*) as total,
                 count(*) FILTER (WHERE state = 'active') as active,
                 count(*) FILTER (WHERE state = 'idle') as idle
          FROM pg_stat_activity
          WHERE datname = current_database()
          """;

      Map<String, Object> connStats = jdbcTemplate.queryForMap(connectionStatsSql);

      meterRegistry.gauge("postgres.connections.total", Tags.of("type", "total"),
          ((Number) connStats.get("total")).intValue());
      meterRegistry.gauge("postgres.connections.active", Tags.of("type", "active"),
          ((Number) connStats.get("active")).intValue());
      meterRegistry.gauge("postgres.connections.idle", Tags.of("type", "idle"),
          ((Number) connStats.get("idle")).intValue());

      // 收集表统计信息
      String tableStatsSql = """
          SELECT schemaname, relname,
                 seq_scan, seq_tup_read,
                 idx_scan, idx_tup_fetch,
                 n_tup_ins, n_tup_upd, n_tup_del,
                 n_live_tup, n_dead_tup
          FROM pg_stat_user_tables
          """;

      List<Map<String, Object>> tableStats = jdbcTemplate.queryForList(tableStatsSql);

      for (Map<String, Object> tableStat : tableStats) {
        String schemaName = (String) tableStat.get("schemaname");
        String tableName = (String) tableStat.get("relname");
        Tags tableTags = Tags.of("schema", schemaName, "table", tableName);

        meterRegistry.gauge("postgres.table.seq_scan", tableTags,
            ((Number) tableStat.get("seq_scan")).longValue());
        meterRegistry.gauge("postgres.table.idx_scan", tableTags,
            ((Number) tableStat.get("idx_scan")).longValue());
        meterRegistry.gauge("postgres.table.rows_inserted", tableTags,
            ((Number) tableStat.get("n_tup_ins")).longValue());
        meterRegistry.gauge("postgres.table.rows_updated", tableTags,
            ((Number) tableStat.get("n_tup_upd")).longValue());
        meterRegistry.gauge("postgres.table.rows_deleted", tableTags,
            ((Number) tableStat.get("n_tup_del")).longValue());
        meterRegistry.gauge("postgres.table.live_rows", tableTags,
            ((Number) tableStat.get("n_live_tup")).longValue());
        meterRegistry.gauge("postgres.table.dead_rows", tableTags,
            ((Number) tableStat.get("n_dead_tup")).longValue());
      }

    } catch (Exception e) {
      log.error("收集数据库性能指标失败: " + e.getMessage(), e);
    }
  }
}
