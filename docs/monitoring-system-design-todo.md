<!--
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-05-20 14:30:32
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-05-20 18:45:10
 * @FilePath: /rock-blade-ITOM-Backstage/docs/monitoring-system-design-todo.md
 * @Description: 
 * 
 * Copyright (c) 2025 by RockBlade, All Rights Reserved. 
-->
# 监控系统实现进度

## 第一阶段 - 基础框架（2周）

### ✓ 已完成项

1. 基础环境搭建：
   - [x] Spring Boot 项目初始化
   - [x] Actuator + Micrometer配置
   - [x] 国际化支持

2. 基础监控实现：
   - [x] CPU使用率监控
   - [x] JVM堆内存监控
   - [x] JVM线程数监控
   - [x] 数据库连接池监控（活动连接数、空闲连接数）

### ⚠ 进行中

1. Prometheus集成：
   - [x] Prometheus服务器配置文件
   ```yaml
   global:
     scrape_interval: 15s      # 采集间隔
     evaluation_interval: 15s   # 规则评估间隔
     scrape_timeout: 10s       # 采集超时时间
     external_labels:
       env: 'production'
       region: 'cn-east'

   rule_files:
     - 'rules/*.yml'           # 告警规则文件目录

   scrape_configs:
     - job_name: 'spring-boot'
       metrics_path: '/actuator/prometheus'
       scheme: 'http'
       static_configs:
         - targets: ['localhost:8080']
       relabel_configs:
         - source_labels: [__address__]
           target_label: instance
           replacement: 'rock-blade-ITOM'
       metric_relabel_configs:
         - source_labels: [__name__]
           regex: 'jvm_memory_used_bytes|system_cpu_usage|system_memory_used'
           action: keep
   ```

### ❌ 待完成项

1. 监控环境配置：
   - [x] Prometheus服务器部署
   - [x] Grafana安装配置（见docs/grafana-setup.md）
   - [x] Grafana数据源配置（见docs/grafana-setup.md）
   - [x] 监控面板模板设计（见docs/grafana-setup.md）

2. 补充监控指标：
   - [x] 磁盘使用率监控
   - [x] 网络IO监控
   - [x] JVM垃圾回收监控
   - [x] 自定义业务指标

3. 告警功能实现：
   - [ ] 邮件告警通知系统
   - [ ] 告警规则配置接口
   - [ ] 告警规则管理页面
   - [ ] 告警历史记录

4. 监控界面开发：
   - [ ] Vue3 + ECharts集成
   - [ ] 仪表盘页面开发
   - [ ] 系统设置页面开发
   - [ ] 监控数据实时展示

## 后续工作建议

1. 优先级排序：
   - 首要任务：完成Prometheus和Grafana的环境配置
   - 次要任务：补充其他监控指标
   - 最后阶段：实现告警功能和界面开发

2. 技术准备：
   - Prometheus服务器环境准备
   - Grafana安装包准备
   - Vue3开发环境配置

3. 注意事项：
   - 监控指标采集间隔合理配置（建议15-30秒）
   - 数据保留策略规划
   - 告警阈值合理设置

## 项目依赖检查

### ✓ 已添加依赖
- [x] spring-boot-starter-actuator
- [x] micrometer-core
- [x] micrometer-registry-prometheus
- [x] spring-boot-starter-mail（用于告警通知）

### ❌ 待添加依赖
- [ ] prometheus-client
- [ ] grafana-client（可选）
- [ ] vue相关依赖（前端项目）
- [ ] echarts（数据可视化）
