-- 创建用户表
CREATE TABLE sys_user (
    id VARCHAR(32) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    nickname VARCHAR(50),
    password VARCHAR(100) NOT NULL,
    avatar VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    status CHAR(1) NOT NULL DEFAULT '1',           -- 角色状态（1正常 0停用）
    user_type VARCHAR(20) DEFAULT 'GUEST',         -- 用户类型（ADMIN/USER/GUEST）
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT uk_username UNIQUE (username),
    CONSTRAINT uk_phone UNIQUE (phone),
    CONSTRAINT uk_email UNIQUE (email)
);

COMMENT ON TABLE sys_user IS '用户信息表';
COMMENT ON COLUMN sys_user.id IS '主键ID';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.nickname IS '昵称';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.user_type IS '用户类型（ADMIN/USER/GUEST）';
COMMENT ON COLUMN sys_user.created_at IS '创建时间';
COMMENT ON COLUMN sys_user.created_by IS '创建人ID';
COMMENT ON COLUMN sys_user.updated_at IS '更新时间';
COMMENT ON COLUMN sys_user.updated_by IS '更新人ID';
COMMENT ON COLUMN sys_user.deleted IS '是否删除';
COMMENT ON COLUMN sys_user.avatar IS '头像';

-- 创建用户登录日志表
CREATE TABLE sys_user_login_log (
    id VARCHAR(32) PRIMARY KEY,
    user_id VARCHAR(32) NOT NULL,
    login_type VARCHAR(20) NOT NULL,    -- 登录方式：账号密码、手机验证码等
    ip_address VARCHAR(50),             -- 登录IP
    device_type VARCHAR(20),            -- 设备类型：PC、Mobile等
    os_name VARCHAR(50),                -- 操作系统
    browser VARCHAR(50),                -- 浏览器
    location VARCHAR(100),              -- 登录地点
    status CHAR(1) NOT NULL DEFAULT '1',           -- 角色状态（1正常 0停用）
    msg VARCHAR(200),                   -- 登录消息，如失败原因
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    CONSTRAINT fk_login_log_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id)
);

COMMENT ON TABLE sys_user_login_log IS '用户登录日志表';
COMMENT ON COLUMN sys_user_login_log.id IS '主键ID';
COMMENT ON COLUMN sys_user_login_log.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_login_log.login_type IS '登录方式';
COMMENT ON COLUMN sys_user_login_log.ip_address IS '登录IP';
COMMENT ON COLUMN sys_user_login_log.device_type IS '设备类型';
COMMENT ON COLUMN sys_user_login_log.os_name IS '操作系统';
COMMENT ON COLUMN sys_user_login_log.browser IS '浏览器';
COMMENT ON COLUMN sys_user_login_log.location IS '登录地点';
COMMENT ON COLUMN sys_user_login_log.status IS '登录状态';
COMMENT ON COLUMN sys_user_login_log.msg IS '登录消息';
COMMENT ON COLUMN sys_user_login_log.created_at IS '创建时间';
COMMENT ON COLUMN sys_user_login_log.created_by IS '创建人ID';
COMMENT ON COLUMN sys_user_login_log.updated_at IS '更新时间';
COMMENT ON COLUMN sys_user_login_log.updated_by IS '更新人ID';

-- 创建角色表
CREATE TABLE sys_role (
    id VARCHAR(32) PRIMARY KEY,
    role_name VARCHAR(30) NOT NULL,
    role_key VARCHAR(100) NOT NULL,    -- 角色标识符，如：admin、user
    user_type VARCHAR(20) DEFAULT 'guest',    -- 用户类型（admin/user/guest）
    status CHAR(1) NOT NULL DEFAULT '1',           -- 角色状态（1正常 0停用）
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    deleted BOOLEAN DEFAULT FALSE,
    remark VARCHAR(500),               -- 备注
    CONSTRAINT uk_role_key UNIQUE (role_key)
);

COMMENT ON TABLE sys_role IS '角色信息表';
COMMENT ON COLUMN sys_role.id IS '主键ID';
COMMENT ON COLUMN sys_role.role_name IS '角色名称';
COMMENT ON COLUMN sys_role.role_key IS '角色标识符';
COMMENT ON COLUMN sys_role.user_type IS '用户类型（admin/user/guest）';
COMMENT ON COLUMN sys_role.status IS '角色状态';
COMMENT ON COLUMN sys_role.created_at IS '创建时间';
COMMENT ON COLUMN sys_role.created_by IS '创建人ID';
COMMENT ON COLUMN sys_role.updated_at IS '更新时间';
COMMENT ON COLUMN sys_role.updated_by IS '更新人ID';
COMMENT ON COLUMN sys_role.deleted IS '是否删除';
COMMENT ON COLUMN sys_role.remark IS '备注';

-- 创建菜单权限表
CREATE TABLE sys_menu (
    id VARCHAR(32) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,        -- 菜单名称
    pid VARCHAR(32),                  -- 父级ID
    path VARCHAR(200),                -- 路由路径
    component VARCHAR(255),           -- 组件路径
    redirect VARCHAR(200),            -- 重定向地址
    status CHAR(1) NOT NULL DEFAULT '1',           -- 角色状态（1正常 0停用）
    type VARCHAR(20) NOT NULL,        -- 菜单类型(catalog目录 menu菜单 embedded内嵌 link链接 button按钮)
    auth_code VARCHAR(100),           -- 后端权限标识
    active_icon VARCHAR(100),         -- 激活时显示的图标
    active_path VARCHAR(200),         -- 作为路由时，需要激活的菜单的Path
    affix_tab BOOLEAN DEFAULT FALSE,  -- 固定在标签栏
    affix_tab_order INTEGER,         -- 在标签栏固定的顺序
    badge VARCHAR(50),                -- 徽标内容(当徽标类型为normal时有效)
    badge_type VARCHAR(20),           -- 徽标类型(dot,normal)
    badge_variants VARCHAR(20),       -- 徽标变体颜色(default,destructive,primary,success,warning)
    hide_children_in_menu BOOLEAN DEFAULT FALSE, -- 在菜单中隐藏下级
    hide_in_breadcrumb BOOLEAN DEFAULT FALSE,   -- 在面包屑中隐藏
    hide_in_menu BOOLEAN DEFAULT FALSE,         -- 在菜单中隐藏
    hide_in_tab BOOLEAN DEFAULT FALSE,          -- 在标签栏中隐藏
    icon VARCHAR(100),               -- 菜单图标
    iframe_src VARCHAR(500),          -- 内嵌Iframe的URL
    keep_alive BOOLEAN DEFAULT FALSE, -- 是否缓存页面
    link VARCHAR(500),               -- 外链页面的URL
    max_num_of_open_tab INTEGER,     -- 同一个路由最大打开的标签数
    no_basic_layout BOOLEAN DEFAULT FALSE, -- 无需基础布局
    open_in_new_window BOOLEAN DEFAULT FALSE, -- 是否在新窗口打开
    "order" INTEGER DEFAULT 0,        -- 菜单排序
    title VARCHAR(100),              -- 菜单标题
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_menu_pid FOREIGN KEY (pid) REFERENCES sys_menu (id)
);

COMMENT ON TABLE sys_menu IS '菜单表';
COMMENT ON COLUMN sys_menu.id IS '菜单ID';
COMMENT ON COLUMN sys_menu.name IS '菜单名称';
COMMENT ON COLUMN sys_menu.pid IS '父级ID';
COMMENT ON COLUMN sys_menu.path IS '路由路径';
COMMENT ON COLUMN sys_menu.component IS '组件路径';
COMMENT ON COLUMN sys_menu.redirect IS '重定向地址';
COMMENT ON COLUMN sys_menu.type IS '菜单类型(catalog目录 menu菜单 embedded内嵌 link链接 button按钮)';
COMMENT ON COLUMN sys_menu.auth_code IS '后端权限标识';
COMMENT ON COLUMN sys_menu.active_icon IS '激活时显示的图标';
COMMENT ON COLUMN sys_menu.active_path IS '作为路由时，需要激活的菜单的Path';
COMMENT ON COLUMN sys_menu.affix_tab IS '固定在标签栏';
COMMENT ON COLUMN sys_menu.affix_tab_order IS '在标签栏固定的顺序';
COMMENT ON COLUMN sys_menu.badge IS '徽标内容(当徽标类型为normal时有效)';
COMMENT ON COLUMN sys_menu.badge_type IS '徽标类型(dot,normal)';
COMMENT ON COLUMN sys_menu.badge_variants IS '徽标变体颜色(default,destructive,primary,success,warning)';
COMMENT ON COLUMN sys_menu.hide_children_in_menu IS '在菜单中隐藏下级';
COMMENT ON COLUMN sys_menu.hide_in_breadcrumb IS '在面包屑中隐藏';
COMMENT ON COLUMN sys_menu.hide_in_menu IS '在菜单中隐藏';
COMMENT ON COLUMN sys_menu.hide_in_tab IS '在标签栏中隐藏';
COMMENT ON COLUMN sys_menu.icon IS '菜单图标';
COMMENT ON COLUMN sys_menu.iframe_src IS '内嵌Iframe的URL';
COMMENT ON COLUMN sys_menu.keep_alive IS '是否缓存页面';
COMMENT ON COLUMN sys_menu.link IS '外链页面的URL';
COMMENT ON COLUMN sys_menu.max_num_of_open_tab IS '同一个路由最大打开的标签数';
COMMENT ON COLUMN sys_menu.no_basic_layout IS '无需基础布局';
COMMENT ON COLUMN sys_menu.open_in_new_window IS '是否在新窗口打开';
COMMENT ON COLUMN sys_menu."order" IS '菜单排序';
COMMENT ON COLUMN sys_menu.title IS '菜单标题';
COMMENT ON COLUMN sys_menu.created_at IS '创建时间';
COMMENT ON COLUMN sys_menu.created_by IS '创建人ID';
COMMENT ON COLUMN sys_menu.updated_at IS '更新时间';
COMMENT ON COLUMN sys_menu.updated_by IS '更新人ID';
COMMENT ON COLUMN sys_menu.deleted IS '是否删除';

-- 创建角色权限表
CREATE TABLE sys_role_permission (
    role_id VARCHAR(32) NOT NULL,
    permission VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    PRIMARY KEY (role_id, permission),
    CONSTRAINT fk_rp_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id)
);

COMMENT ON TABLE sys_role_permission IS '角色权限关联表';
COMMENT ON COLUMN sys_role_permission.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_permission.permission IS '权限标识符';
COMMENT ON COLUMN sys_role_permission.created_at IS '创建时间';
COMMENT ON COLUMN sys_role_permission.created_by IS '创建人ID';
COMMENT ON COLUMN sys_role_permission.updated_at IS '更新时间';
COMMENT ON COLUMN sys_role_permission.updated_by IS '更新人ID';

-- 创建角色权限相关索引
CREATE INDEX idx_sys_role_permission_role_id ON sys_role_permission(role_id);

-- 创建用户-角色关联表
CREATE TABLE sys_user_role (
    user_id VARCHAR(32) NOT NULL,
    role_id VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id),
    CONSTRAINT fk_ur_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id)
);

COMMENT ON TABLE sys_user_role IS '用户和角色关联表';
COMMENT ON COLUMN sys_user_role.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_role.role_id IS '角色ID';
COMMENT ON COLUMN sys_user_role.created_at IS '创建时间';
COMMENT ON COLUMN sys_user_role.created_by IS '创建人ID';
COMMENT ON COLUMN sys_user_role.updated_at IS '更新时间';
COMMENT ON COLUMN sys_user_role.updated_by IS '更新人ID';

-- 创建角色-菜单关联表
CREATE TABLE sys_role_menu (
    role_id VARCHAR(32) NOT NULL,
    menu_id VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    PRIMARY KEY (role_id, menu_id),
    CONSTRAINT fk_rm_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id),
    CONSTRAINT fk_rm_menu_id FOREIGN KEY (menu_id) REFERENCES sys_menu (id)
);

COMMENT ON TABLE sys_role_menu IS '角色和菜单关联表';
COMMENT ON COLUMN sys_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN sys_role_menu.created_at IS '创建时间';
COMMENT ON COLUMN sys_role_menu.created_by IS '创建人ID';
COMMENT ON COLUMN sys_role_menu.updated_at IS '更新时间';
COMMENT ON COLUMN sys_role_menu.updated_by IS '更新人ID';

-- 添加登录日志索引
CREATE INDEX idx_sys_user_login_log_user_id ON sys_user_login_log(user_id);
CREATE INDEX idx_sys_user_login_log_created_at ON sys_user_login_log(created_at);
CREATE INDEX idx_sys_user_login_log_status ON sys_user_login_log(status);

-- 创建角色权限相关索引
CREATE INDEX idx_sys_role_status ON sys_role(status);
CREATE INDEX idx_sys_role_deleted ON sys_role(deleted);

CREATE INDEX idx_sys_menu_pid ON sys_menu(pid);
CREATE INDEX idx_sys_menu_deleted ON sys_menu(deleted);

-- 创建部门表
CREATE TABLE sys_dept (
    id VARCHAR(32) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,        -- 部门名称
    pid VARCHAR(32),                  -- 父级ID
    ancestors VARCHAR(500),           -- 祖级列表
    leader VARCHAR(20),               -- 负责人
    phone VARCHAR(11),                -- 联系电话
    email VARCHAR(50),                -- 邮箱
    "order" INTEGER DEFAULT 0,        -- 显示顺序
    status CHAR(1) NOT NULL DEFAULT '1', -- 部门状态（1正常 0停用）
    remark VARCHAR(500),               -- 备注
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_dept_pid FOREIGN KEY (pid) REFERENCES sys_dept (id)
);

COMMENT ON TABLE sys_dept IS '部门表';
COMMENT ON COLUMN sys_dept.id IS '部门ID';
COMMENT ON COLUMN sys_dept.name IS '部门名称';
COMMENT ON COLUMN sys_dept.pid IS '父部门ID';
COMMENT ON COLUMN sys_dept.ancestors IS '祖级列表';
COMMENT ON COLUMN sys_dept.leader IS '负责人';
COMMENT ON COLUMN sys_dept.phone IS '联系电话';
COMMENT ON COLUMN sys_dept.email IS '邮箱';
COMMENT ON COLUMN sys_dept."order" IS '显示顺序';
COMMENT ON COLUMN sys_dept.status IS '部门状态（1正常 0停用）';
COMMENT ON COLUMN sys_dept.created_at IS '创建时间';
COMMENT ON COLUMN sys_dept.created_by IS '创建人ID';
COMMENT ON COLUMN sys_dept.updated_at IS '更新时间';
COMMENT ON COLUMN sys_dept.updated_by IS '更新人ID';
COMMENT ON COLUMN sys_dept.deleted IS '是否删除';
COMMENT ON COLUMN sys_dept.remark IS '备注';

-- 创建部门相关索引
CREATE INDEX idx_sys_dept_pid ON sys_dept(pid);
CREATE INDEX idx_sys_dept_deleted ON sys_dept(deleted);
CREATE INDEX idx_sys_dept_status ON sys_dept(status);

-- 添加初始部门数据
INSERT INTO sys_dept (id, name, pid, ancestors, "order", status, created_by) VALUES
('1', '总公司', NULL, '0', 0, '1', '100'),
('2', '研发部门', '1', '0,1', 1, '1', '100'),
('3', '市场部门', '1', '0,1', 2, '1', '100'),
('4', '测试部门', '1', '0,1', 3, '1', '100'),
('5', '财务部门', '1', '0,1', 4, '1', '100'),
('6', '运维部门', '1', '0,1', 5, '1', '100');

-- 添加初始化菜单数据
INSERT INTO sys_menu (id, name, pid, "order", path, component, type, auth_code, icon, title, created_by) VALUES
-- Dashboard
('1', '仪表盘', NULL, '1', '/dashboard', '/dashboard/index', 'CATALOG', 'dashboard:view', 'dashboard', '仪表盘', '100'),

-- 系统管理
('2', '系统管理', NULL, '2', '/system', NULL, 'CATALOG', '', 'system', '系统管理', '100'),

-- 菜单管理
('3', '菜单管理', '2', 1, '/menu', '/system/menu/index', 'MENU', 'system:menu:list', 'menu', '菜单管理', '100'),
('4', '菜单查询', '3', 1, '', '', 'BUTTON', 'system:menu:query', '#', '菜单查询', '100'),
('5', '菜单新增', '3', 2, '', '', 'BUTTON', 'system:menu:add', '#', '菜单新增', '100'),
('6', '菜单修改', '3', 3, '', '', 'BUTTON', 'system:menu:edit', '#', '菜单修改', '100'),
('7', '菜单删除', '3', 4, '', '', 'BUTTON', 'system:menu:remove', '#', '菜单删除', '100');

-- 添加超级管理员用户
INSERT INTO sys_user (id, username, password, status, user_type, created_by) VALUES
('1', 'admin', '$2a$10$5kj44kCu2CyuN20/3qTt9eVnA7QoVhDoMumPuoCnCAMU9mEEb94vW', '1', 'ADMIN', '100');

-- 创建用户-部门关联表
CREATE TABLE sys_user_dept (
    user_id VARCHAR(32) NOT NULL,
    dept_id VARCHAR(32) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,    -- 是否为主部门
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(32),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(32),
    PRIMARY KEY (user_id, dept_id),
    CONSTRAINT fk_ud_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id),
    CONSTRAINT fk_ud_dept_id FOREIGN KEY (dept_id) REFERENCES sys_dept (id)
);

COMMENT ON TABLE sys_user_dept IS '用户和部门关联表';
COMMENT ON COLUMN sys_user_dept.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_dept.dept_id IS '部门ID';
COMMENT ON COLUMN sys_user_dept.is_primary IS '是否为主部门';
COMMENT ON COLUMN sys_user_dept.created_at IS '创建时间';
COMMENT ON COLUMN sys_user_dept.created_by IS '创建人ID';
COMMENT ON COLUMN sys_user_dept.updated_at IS '更新时间';
COMMENT ON COLUMN sys_user_dept.updated_by IS '更新人ID';

-- 创建用户部门相关索引
CREATE INDEX idx_sys_user_dept_user_id ON sys_user_dept(user_id);
CREATE INDEX idx_sys_user_dept_dept_id ON sys_user_dept(dept_id);
CREATE INDEX idx_sys_user_dept_is_primary ON sys_user_dept(is_primary);
