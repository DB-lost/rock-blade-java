# Maven多模块结构改造计划

## 一、项目结构改造目标

- [x] 将当前的`rock-blade-java`项目改造为Maven多模块结构，使其成为一个可扩展的模板项目，便于后续创建基于此模板的子项目。

## 二、模块划分方案

根据当前项目结构和功能职责，划分为以下模块：

- [x] **rock-blade-parent**: 父模块，管理依赖版本和公共配置
- [x] **rock-blade-core**: 核心领域模型和业务逻辑
- [x] **rock-blade-framework**: 框架配置和公共组件
- [x] **rock-blade-infrastructure**: 数据访问、外部服务集成等
- [x] **rock-blade-api**: 对外接口定义
- [x] **rock-blade-web**: Web应用入口模块

## 三、具体实施步骤

### 1. 创建父POM配置

- [x] 修改项目根目录下的`pom.xml`，将其转换为父POM：
  - [x] 添加`<packaging>pom</packaging>`标签
  - [x] 配置`<modules>`节点列出子模块
  - [x] 设置依赖版本管理(`<dependencyManagement>`)
  - [x] 配置插件管理(`<pluginManagement>`)

### 2. 创建各子模块目录结构

- [x] 为每个子模块创建标准Maven目录结构：

```
rock-blade-java/
├── pom.xml (父POM)
├── rock-blade-core/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       └── test/
├── rock-blade-framework/
│   ├── pom.xml
│   └── src/
├── rock-blade-infrastructure/
│   ├── pom.xml
│   └── src/
├── rock-blade-api/
│   ├── pom.xml
│   └── src/
└── rock-blade-web/
    ├── pom.xml
    └── src/
```

### 3. 子模块POM配置

- [ ] 配置每个子模块的`pom.xml`文件：
  - [ ] 设置父模块引用
  - [ ] 配置模块依赖关系
  - [ ] 设置打包方式和构建配置

### 4. 代码迁移

- [ ] 将现有代码按功能职责迁移到对应模块：
  - [ ] **rock-blade-core**: 领域模型(domain)相关代码
  - [ ] **rock-blade-framework**: 框架(framework)相关代码
  - [ ] **rock-blade-infrastructure**: 基础设施(infrastructure)相关代码
  - [ ] **rock-blade-api**: 接口定义和DTO
  - [ ] **rock-blade-web**: 控制器、配置和应用入口

### 5. 调整依赖关系

- [ ] 重新梳理各模块依赖关系，确保符合依赖倒置原则：
  - [ ] core模块不依赖其他业务模块
  - [ ] 确保模块间依赖清晰合理，避免循环依赖

### 6. 调整配置文件位置

- [ ] 将配置文件按职责划分到对应模块：
  - [ ] 应用程序主配置放在web模块
  - [ ] 公共配置放在framework模块
  - [ ] 特定功能配置放在对应模块

### 7. 修改构建脚本

- [ ] 更新Maven构建配置，确保整体项目和子模块都能正常构建。

## 四、注意事项

1. 确保模块间依赖关系清晰，避免循环依赖
2. 合理控制模块粒度，避免过度拆分
3. 保持向后兼容性，便于现有项目平滑迁移
4. 完善文档说明，便于后续项目使用此模板

## 五、后续Git Subtree应用场景

完成多模块改造后，未来可以考虑的Git Subtree应用场景：

1. 子项目可以通过Git Subtree引入模板项目中的通用模块
2. 多个项目之间共享通用功能模块，如framework模块
3. 子项目根据需要扩展和定制模板项目中的模块
