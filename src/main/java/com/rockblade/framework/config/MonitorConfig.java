/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-20 11:55:49
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-21 10:49:33
 * @FilePath: /rock-blade-ITOM-Backstage/home/db/WorkSpace/Template-WorkSpace/rock-blade-java/src/main/java/com/rockblade/framework/config/MonitorConfig.java
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
 */
package main.java.com.rockblade.framework.config;

import com.rockblade.framework.utils.MessageUtils;
import com.zaxxer.hikari.HikariDataSource;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Gauge;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;

/**
 * 监控配置类
 * 配置系统资源监控指标
 */
@Configuration
@EnableScheduling
public class MonitorConfig {

    private final MeterRegistry registry;
    private final HikariDataSource dataSource;

    public MonitorConfig(MeterRegistry registry, @Autowired HikariDataSource dataSource) {
        this.registry = registry;
        this.dataSource = dataSource;
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "rock-blade-ITOM");
    }

    /**
     * 初始化系统资源监控指标
     */
    @Scheduled(fixedRate = 5000)
    public void recordSystemMetrics() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        // CPU使用率
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
            Gauge.builder("system.cpu.usage", sunOsBean::getCpuLoad)
                    .description(MessageUtils.message("monitor.system.cpu.usage"))
                    .register(registry);
        }

        // 系统内存使用情况
        Gauge.builder("system.memory.used", () -> memoryBean.getHeapMemoryUsage().getUsed())
                .description(MessageUtils.message("monitor.system.memory.used"))
                .baseUnit("bytes")
                .register(registry);

        Gauge.builder("system.memory.max", () -> memoryBean.getHeapMemoryUsage().getMax())
                .description(MessageUtils.message("monitor.system.memory.max"))
                .baseUnit("bytes")
                .register(registry);

        // JVM线程数
        Gauge.builder("jvm.threads.count", () -> ManagementFactory.getThreadMXBean().getThreadCount())
                .description(MessageUtils.message("monitor.jvm.threads"))
                .register(registry);
    }

    /**
     * 数据库连接池监控
     */
    @Bean
    public void initializeConnectionPoolMetrics() {
        // HikariCP连接池监控
        Gauge.builder("hikaricp.connections.active", () -> getHikariPoolActiveConnections())
                .description(MessageUtils.message("monitor.hikari.connections.active"))
                .register(registry);

        Gauge.builder("hikaricp.connections.idle", () -> getHikariPoolIdleConnections())
                .description(MessageUtils.message("monitor.hikari.connections.idle"))
                .register(registry);
    }

    private int getHikariPoolActiveConnections() {
        return dataSource.getHikariPoolMXBean().getActiveConnections();
    }

    private int getHikariPoolIdleConnections() {
        return dataSource.getHikariPoolMXBean().getIdleConnections();
    }
}
