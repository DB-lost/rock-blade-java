/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-20 11:55:49
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-21 12:15:58
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/config/MonitorConfig.java
 * @Description: 监控配置类 配置系统资源监控指标
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.config;

import java.io.File;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.net.NetworkInterface;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.rockblade.framework.utils.MessageUtils;
import com.zaxxer.hikari.HikariDataSource;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;

@Configuration
@EnableScheduling
public class MonitorConfig {

  private final HikariDataSource dataSource;
  private final AtomicInteger onlineUserCount = new AtomicInteger(0);

  public MonitorConfig(@Autowired HikariDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public MeterRegistryCustomizer<MeterRegistry> metricsCustomizer() {
    return registry -> {
      registry.config().commonTags("application", "rock-blade-ITOM");
      initializeConnectionPoolMetrics(registry);
      initializeAllMetrics(registry);
    };
  }

  private void initializeAllMetrics(MeterRegistry registry) {
    recordBasicMetrics(registry);
    recordDiskMetrics(registry);
    recordNetworkMetrics(registry);
    recordGCMetrics(registry);
    initializeBusinessMetrics(registry);
  }

  /** 记录基础系统指标 */
  private void recordBasicMetrics(MeterRegistry registry) {
    OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

    // 系统负载
    Gauge.builder("system.load.average", osBean::getSystemLoadAverage)
        .description(MessageUtils.message("monitor.system.load.average"))
        .register(registry);

    // CPU使用率
    if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
      com.sun.management.OperatingSystemMXBean sunOsBean =
          (com.sun.management.OperatingSystemMXBean) osBean;
      Gauge.builder("system.cpu.usage", sunOsBean::getCpuLoad)
          .description(MessageUtils.message("monitor.system.cpu.usage"))
          .register(registry);
    }

    // 堆内存使用情况
    Gauge.builder("jvm.memory.heap.used", () -> memoryBean.getHeapMemoryUsage().getUsed())
        .description(MessageUtils.message("monitor.jvm.memory.heap.used"))
        .baseUnit("bytes")
        .register(registry);

    Gauge.builder("jvm.memory.heap.max", () -> memoryBean.getHeapMemoryUsage().getMax())
        .description(MessageUtils.message("monitor.jvm.memory.heap.max"))
        .baseUnit("bytes")
        .register(registry);

    // 非堆内存使用情况
    Gauge.builder("jvm.memory.nonheap.used", () -> memoryBean.getNonHeapMemoryUsage().getUsed())
        .description(MessageUtils.message("monitor.jvm.memory.nonheap.used"))
        .baseUnit("bytes")
        .register(registry);

    Gauge.builder("jvm.memory.nonheap.max", () -> memoryBean.getNonHeapMemoryUsage().getMax())
        .description(MessageUtils.message("monitor.jvm.memory.nonheap.max"))
        .baseUnit("bytes")
        .register(registry);

    // JVM线程数
    Gauge.builder("jvm.threads.count", () -> ManagementFactory.getThreadMXBean().getThreadCount())
        .description(MessageUtils.message("monitor.jvm.threads"))
        .register(registry);
  }

  /** 记录磁盘使用情况 */
  private void recordDiskMetrics(MeterRegistry registry) {
    File root = new File("/");
    long totalSpace = root.getTotalSpace();
    long usableSpace = root.getUsableSpace();
    long usedSpace = totalSpace - usableSpace;

    Gauge.builder("system.disk.total", () -> totalSpace)
        .description(MessageUtils.message("monitor.system.disk.total"))
        .baseUnit("bytes")
        .register(registry);

    Gauge.builder("system.disk.used", () -> usedSpace)
        .description(MessageUtils.message("monitor.system.disk.used"))
        .baseUnit("bytes")
        .register(registry);

    Gauge.builder("system.disk.usage", () -> (double) usedSpace / totalSpace * 100)
        .description(MessageUtils.message("monitor.system.disk.usage"))
        .baseUnit("percent")
        .register(registry);
  }

  /** 记录网络IO情况 */
  private void recordNetworkMetrics(MeterRegistry registry) {
    try {
      Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
      while (nets.hasMoreElements()) {
        NetworkInterface netint = nets.nextElement();
        if (netint.isUp() && !netint.isLoopback()) {
          String name = netint.getName();
          Gauge.builder("system.network.rx.bytes." + name, () -> getNetworkRxBytes(name))
              .description(MessageUtils.message("monitor.system.network.rx.bytes"))
              .baseUnit("bytes")
              .register(registry);

          Gauge.builder("system.network.tx.bytes." + name, () -> getNetworkTxBytes(name))
              .description(MessageUtils.message("monitor.system.network.tx.bytes"))
              .baseUnit("bytes")
              .register(registry);
        }
      }
    } catch (Exception e) {
      // 记录日志但不中断监控
      e.printStackTrace();
    }
  }

  /** 记录GC指标 */
  private void recordGCMetrics(MeterRegistry registry) {
    List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
    for (GarbageCollectorMXBean gcBean : gcBeans) {
      String name = gcBean.getName().replace(" ", "_").toLowerCase();

      Gauge.builder("jvm.gc.count." + name, gcBean::getCollectionCount)
          .description(MessageUtils.message("monitor.jvm.gc.count"))
          .register(registry);

      Gauge.builder("jvm.gc.time." + name, gcBean::getCollectionTime)
          .description(MessageUtils.message("monitor.jvm.gc.time"))
          .baseUnit("milliseconds")
          .register(registry);
    }
  }

  private long getNetworkRxBytes(String interfaceName) {
    try {
      String line = getNetworkStatLine(interfaceName);
      if (line != null) {
        String[] fields = line.trim().split("\\s+");
        return Long.parseLong(fields[1]); // 接收字节数在第2个字段
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  private long getNetworkTxBytes(String interfaceName) {
    try {
      String line = getNetworkStatLine(interfaceName);
      if (line != null) {
        String[] fields = line.trim().split("\\s+");
        return Long.parseLong(fields[9]); // 发送字节数在第10个字段
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  private String getNetworkStatLine(String interfaceName) {
    try {
      List<String> lines = Files.readAllLines(Paths.get("/proc/net/dev"));
      return lines.stream()
          .filter(line -> line.trim().startsWith(interfaceName + ":"))
          .findFirst()
          .orElse(null);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /** 初始化数据库连接池监控指标 */
  private void initializeConnectionPoolMetrics(MeterRegistry registry) {
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

  /** 初始化业务指标监控 */
  private void initializeBusinessMetrics(MeterRegistry registry) {
    // 认证相关指标
    Counter.builder("auth.login.total")
        .description("Total number of login attempts")
        .tag("type", "total")
        .register(registry);

    Counter.builder("auth.login.success")
        .description("Number of successful logins")
        .tag("type", "success")
        .register(registry);

    Counter.builder("auth.login.failure")
        .description("Number of failed logins")
        .tag("type", "failure")
        .register(registry);

    Counter.builder("auth.email.code.sent")
        .description("Number of email verification codes sent")
        .register(registry);

    Counter.builder("auth.email.code.verify")
        .tag("result", "success")
        .description("Number of successful email code verifications")
        .register(registry);

    // 用户活跃度指标
    Gauge.builder("user.online.concurrent", () -> (double) onlineUserCount.get())
        .description("Number of currently online users")
        .register(registry);

    // 设备统计指标
    Counter.builder("client.device")
        .description("Login attempts by device type")
        .tag("type", "unknown")
        .register(registry);

    Counter.builder("client.browser")
        .description("Login attempts by browser")
        .tag("type", "unknown")
        .register(registry);

    Counter.builder("client.location")
        .description("Login attempts by location")
        .tag("location", "unknown")
        .register(registry);

    // 安全相关指标
    Counter.builder("security.password.reset")
        .description("Number of password reset requests")
        .register(registry);
  }
}
