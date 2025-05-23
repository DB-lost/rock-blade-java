# Rock Blade 监控系统配置指南

## 1. 本地环境配置

### 1.1 基础环境变量配置
在根目录下创建 `.env` 文件，添加以下配置：

```env
# 数据库配置
DEV_DATABASE_URL=jdbc:postgresql://localhost:5432/rock_blade
DEV_DATABASE_USERNAME=your_username
DEV_DATABASE_PASSWORD=your_password

# Redis配置
DEV_REDIS_HOST=localhost
DEV_REDIS_PORT=6379
DEV_REDIS_PASSWORD=your_redis_password
DEV_REDIS_DATABASE=0

# 文件上传配置
PROFILE_PATH=/path/to/uploads

# JWT配置
JWT_SECRET_KEY=your_jwt_secret

# 邮箱配置
MAIL_HOST=smtp.example.com
MAIL_PORT=587
MAIL_USERNAME=your_email@example.com
MAIL_PASSWORD=your_email_password
```

### 1.2 PostgreSQL监控配置

1. 编辑 `postgresql.conf`，添加以下配置：
```ini
# 监控扩展配置
# - Shared Library Preloading -
shared_preload_libraries = 'pg_stat_statements'
pg_stat_statements.track = 'all'
pg_stat_statements.max = 10000

# 性能监控配置
# - What to Log -
log_min_duration_statement = 1000
track_io_timing = on
track_activities = on
track_counts = on

# 日志配置
# - Query Tuning -
log_connections = on
log_disconnections = on
log_line_prefix = '%m [%p] %q%u@%d '
```

2. 重启PostgreSQL后执行：
```sql
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;
```

### 1.3 Prometheus配置

1. 配置告警规则：
   - 将 `docs/prometheus/rules/alert_rules.yml` 复制到 Prometheus 配置目录

2. 编辑 `prometheus.yml`：
```yaml
rule_files:
  - "alert_rules.yml"

scrape_configs:
  - job_name: 'rock-blade'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['localhost:8080']
```

### 1.4 Grafana配置

1. 导入仪表盘配置：
   - 导入 `docs/grafana/grafana.json`

2. 配置数据源：
   - 添加 Prometheus 数据源
   - 添加 PostgreSQL 数据源

## 2. GitHub Actions配置

### 2.1 密钥配置
在 GitHub 仓库的 Settings > Secrets and variables > Actions 中添加以下密钥：

```plaintext
# 代码质量检查
SONAR_TOKEN             # SonarQube 访问令牌
SONAR_HOST_URL          # SonarQube 服务器地址，例如：https://sonar.your-company.com

# COS对象存储配置
COS_SECRET_ID           # 腾讯云COS访问密钥ID
COS_SECRET_KEY          # 腾讯云COS访问密钥
COS_BUCKET              # COS存储桶名称
COS_REGION              # COS存储桶所在地域

# 服务器配置
SERVER_HOST             # 部署服务器地址
SERVER_USERNAME         # 部署服务器用户名
SERVER_SSH_KEY          # 部署服务器SSH私钥

# 应用部署配置
APP_BASE               # 应用部署基础路径
DOCKER_NAME            # Docker容器名称前缀

# 其他配置
GITHUB_TOKEN           # GitHub访问令牌
MVN_SETTINGS           # Maven配置内容
```

### 2.2 配置说明

1. Sonar相关配置：
   - SONAR_TOKEN: SonarQube服务的访问令牌，用于代码质量分析
   - SONAR_HOST_URL: SonarQube服务器地址，例如：https://sonar.your-company.com

2. COS对象存储配置：
   - COS_SECRET_ID: 腾讯云COS访问密钥ID
   - COS_SECRET_KEY: 腾讯云COS访问密钥
   - COS_BUCKET: COS存储桶名称，用于存储应用构建产物
   - COS_REGION: COS存储桶所在地域，例如：ap-guangzhou

3. 服务器配置：
   - SERVER_HOST: 部署服务器地址，例如：your-server.com
   - SERVER_USERNAME: 部署服务器登录用户名
   - SERVER_SSH_KEY: 部署服务器SSH私钥，用于自动化部署

4. 应用部署配置：
   - APP_BASE: 应用部署的基础路径，例如：/opt/rock-blade
   - DOCKER_NAME: Docker容器名称前缀，用于容器管理和监控

5. 其他必要配置：
   - GITHUB_TOKEN: GitHub访问令牌，用于代码拉取和包发布
   - MVN_SETTINGS: Maven配置文件内容，包含私有仓库认证信息

注意事项：
- 所有密钥必须在GitHub仓库的Secrets中配置
- 生产环境的敏感信息应使用更严格的权限控制
- 定期轮换密钥以保证安全性
- 不同环境（测试/生产）使用不同的密钥

## 3. 验证步骤

### 3.1 本地环境验证
1. 检查PostgreSQL监控扩展：
```sql
SELECT * FROM pg_extension WHERE extname = 'pg_stat_statements';
```

2. 验证Prometheus规则：
```bash
promtool check rules alert_rules.yml
```

3. 检查监控指标：
   - 访问: `http://localhost:8080/actuator/prometheus`
   - 访问: `http://localhost:3000` (Grafana)

### 3.2 CI/CD验证
1. 检查 GitHub Actions 工作流：
   - 访问仓库的 Actions 页面
   - 确认 CI/CD 流程正常运行

2. 验证部署：
   - 检查应用是否正常启动
   - 验证监控系统是否正常采集数据
   - 测试告警规则是否生效

## 4. 注意事项

1. 配置安全：
   - 所有密码和密钥不要直接提交到代码仓库
   - 生产环境的敏感信息使用环境变量或密钥管理系统

2. 监控维护：
   - PostgreSQL配置修改后需要重启数据库
   - 定期检查监控数据的存储状况
   - 及时清理过期的监控数据

3. CI/CD注意事项：
   - 定期更新依赖版本
   - 保持 Action 运行环境的版本更新
   - 确保备份策略的有效性
