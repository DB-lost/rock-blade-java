# 渐进式RBAC权限设计方案

## 1. 设计理念

渐进式RBAC是一种灵活的权限控制设计模式，它允许系统根据不同项目的需求采用不同级别的权限控制。这种设计模式的核心是在保持完整RBAC模型的同时，提供简化的使用方式，使系统能够适应不同场景的需求。

## 2. 架构层级

### 2.1 基础层
- 简单角色控制
- 基础权限校验
- 适用于内容网站、简单业务系统

### 2.2 标准层
- 完整RBAC模型
- 细粒度权限控制
- 适用于中等复杂度的业务系统

### 2.3 高级层
- 数据级权限控制
- 动态权限管理
- 适用于复杂的企业级应用

## 3. 技术实现

### 3.1 数据库设计

保持完整的RBAC表结构，包括：
```sql
-- 用户表
CREATE TABLE sys_user (
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    status CHAR(1) DEFAULT '0',
    -- other fields
);

-- 角色表
CREATE TABLE sys_role (
    role_id BIGINT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    role_key VARCHAR(50) NOT NULL,
    role_sort INT,
    status CHAR(1) DEFAULT '0'
);

-- 菜单表
CREATE TABLE sys_menu (
    menu_id BIGINT PRIMARY KEY,
    menu_name VARCHAR(50),
    parent_id BIGINT,
    order_num INT,
    path VARCHAR(200),
    component VARCHAR(255),
    perms VARCHAR(100),
    menu_type CHAR(1),
    visible CHAR(1),
    status CHAR(1)
);

-- 用户角色关联表
CREATE TABLE sys_user_role (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id)
);

-- 角色菜单关联表
CREATE TABLE sys_role_menu (
    role_id BIGINT,
    menu_id BIGINT,
    PRIMARY KEY (role_id, menu_id)
);
```

### 3.2 权限配置

application.yml配置示例：
```yaml
rbac:
  # 权限模式：simple/standard/advanced
  mode: simple
  
  # 功能特性配置
  features:
    data-permission: false    # 数据权限控制
    dynamic-permission: false # 动态权限
    audit-log: true          # 审计日志
    
  # 简单模式配置
  simple:
    default-role: user       # 默认角色
    admin-role: admin        # 管理员角色
    
  # 安全配置
  security:
    ignore-urls:             # 不需要权限验证的URL
      - /auth/login
      - /auth/register
      - /public/**
```

### 3.3 核心实现

#### 3.3.1 注解控制

```java
// 简单模式
@RequiresRoles("admin")
public void adminOperation() {
    // 管理员操作
}

// 标准模式
@RequiresPermissions("system:user:view")
public void viewUser() {
    // 查看用户
}

// 高级模式
@DataScope(deptAlias = "d", userAlias = "u")
public List<User> getUserList(User user) {
    // 带数据权限的查询
}
```

#### 3.3.2 权限验证实现

```java
@Service
public class PermissionService {
    
    @Autowired
    private RbacProperties rbacProperties;
    
    public boolean hasPermission(String permission) {
        // 根据模式选择验证方式
        switch (rbacProperties.getMode()) {
            case SIMPLE:
                return hasRole(getLoginUser().getRoles());
            case STANDARD:
                return hasPermissionStandard(permission);
            case ADVANCED:
                return hasPermissionAdvanced(permission);
            default:
                return false;
        }
    }
    
    // 简单模式：仅角色验证
    private boolean hasRole(Set<String> roles) {
        return roles.contains(rbacProperties.getSimple().getAdminRole()) 
            || roles.contains(permission);
    }
    
    // 标准模式：权限验证
    private boolean hasPermissionStandard(String permission) {
        // 完整的RBAC权限验证
        return getLoginUser().getPermissions().contains(permission);
    }
    
    // 高级模式：数据权限验证
    private boolean hasPermissionAdvanced(String permission) {
        // 包含数据权限的验证
        return true;
    }
}
```

## 4. 使用指南

### 4.1 简单模式

适用场景：
- 内容网站
- 简单的管理系统
- 用户量较小的应用

实现方式：
1. 配置`rbac.mode=simple`
2. 使用`@RequiresRoles`注解
3. 仅需维护用户和角色关系

### 4.2 标准模式

适用场景：
- 企业管理系统
- 需要细粒度权限控制的应用
- 多角色多权限场景

实现方式：
1. 配置`rbac.mode=standard`
2. 使用`@RequiresPermissions`注解
3. 维护完整的RBAC权限体系

### 4.3 高级模式

适用场景：
- 大型企业应用
- 需要数据级权限控制
- 复杂的组织架构管理

实现方式：
1. 配置`rbac.mode=advanced`
2. 使用`@DataScope`等高级注解
3. 实现数据权限和动态权限控制

## 5. 最佳实践

### 5.1 权限粒度控制
- URI资源权限
- 功能操作权限
- 数据范围权限
- 字段级权限

### 5.2 缓存策略
```java
@Cacheable(value = "permission", key = "#userId")
public Set<String> getUserPermissions(Long userId) {
    // 获取用户权限
}
```

### 5.3 性能优化
- 权限树预加载
- 权限结果缓存
- 批量权限检查

## 6. 扩展性设计

### 6.1 自定义权限验证
```java
public interface PermissionValidator {
    boolean validate(String permission);
}

@Component
public class CustomPermissionValidator implements PermissionValidator {
    @Override
    public boolean validate(String permission) {
        // 自定义验证逻辑
        return true;
    }
}
```

### 6.2 动态权限
```java
public interface DynamicPermissionProvider {
    Set<String> getPermissions(Long userId);
}
```

## 7. 注意事项

1. 权限设计原则
   - 最小权限原则
   - 职责分离原则
   - 易用性原则

2. 安全考虑
   - 防止权限提升
   - 日志审计
   - 会话管理

3. 性能考虑
   - 合理使用缓存
   - 优化权限检查频率
   - 避免过度的权限粒度

## 8. 版本规划

### v1.0 基础版
- 简单角色控制
- 基本权限验证
- 配置化管理

### v2.0 标准版
- 完整RBAC模型
- 细粒度权限控制
- 权限缓存机制

### v3.0 高级版
- 数据权限控制
- 动态权限管理
- 权限智能分析
