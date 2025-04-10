-- 创建用户表
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT uk_username UNIQUE (username),
    CONSTRAINT uk_phone UNIQUE (phone),
    CONSTRAINT uk_email UNIQUE (email)
);

COMMENT ON TABLE sys_user IS '用户信息表';
COMMENT ON COLUMN sys_user.id IS '主键ID';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.created_at IS '创建时间';
COMMENT ON COLUMN sys_user.created_by IS '创建人ID';
COMMENT ON COLUMN sys_user.updated_at IS '更新时间';
COMMENT ON COLUMN sys_user.updated_by IS '更新人ID';
COMMENT ON COLUMN sys_user.deleted IS '是否删除';

-- 创建用户登录日志表
CREATE TABLE sys_user_login_log (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    login_type VARCHAR(20) NOT NULL,    -- 登录方式：账号密码、手机验证码等
    ip_address VARCHAR(50),             -- 登录IP
    device_type VARCHAR(20),            -- 设备类型：PC、Mobile等
    os_name VARCHAR(50),                -- 操作系统
    browser VARCHAR(50),                -- 浏览器
    location VARCHAR(100),              -- 登录地点
    status VARCHAR(20) NOT NULL,        -- 登录状态：成功、失败
    msg VARCHAR(200),                   -- 登录消息，如失败原因
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT,
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
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(30) NOT NULL,
    role_key VARCHAR(100) NOT NULL,    -- 角色标识符，如：admin、user
    status CHAR(1) NOT NULL,           -- 角色状态（0正常 1停用）
    sort_order INTEGER DEFAULT 0,       -- 显示顺序
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted BOOLEAN DEFAULT FALSE,
    remark VARCHAR(500),               -- 备注
    CONSTRAINT uk_role_key UNIQUE (role_key)
);

COMMENT ON TABLE sys_role IS '角色信息表';
COMMENT ON COLUMN sys_role.id IS '主键ID';
COMMENT ON COLUMN sys_role.role_name IS '角色名称';
COMMENT ON COLUMN sys_role.role_key IS '角色标识符';
COMMENT ON COLUMN sys_role.status IS '角色状态';
COMMENT ON COLUMN sys_role.sort_order IS '显示顺序';
COMMENT ON COLUMN sys_role.created_at IS '创建时间';
COMMENT ON COLUMN sys_role.created_by IS '创建人ID';
COMMENT ON COLUMN sys_role.updated_at IS '更新时间';
COMMENT ON COLUMN sys_role.updated_by IS '更新人ID';
COMMENT ON COLUMN sys_role.deleted IS '是否删除';
COMMENT ON COLUMN sys_role.remark IS '备注';

-- 创建菜单权限表
CREATE TABLE sys_menu (
    id BIGSERIAL PRIMARY KEY,
    menu_name VARCHAR(50) NOT NULL,
    parent_id BIGINT,
    order_num INTEGER DEFAULT 0,
    path VARCHAR(200),                -- 路由地址
    component VARCHAR(255),           -- 组件路径
    query_param VARCHAR(255),         -- 路由参数
    is_frame BOOLEAN DEFAULT FALSE,   -- 是否为外链
    is_cache BOOLEAN DEFAULT FALSE,   -- 是否缓存
    menu_type CHAR(1) NOT NULL,      -- 菜单类型（M目录 C菜单 F按钮）
    visible CHAR(1) NOT NULL,        -- 显示状态（0显示 1隐藏）
    status CHAR(1) NOT NULL,         -- 菜单状态（0正常 1停用）
    perms VARCHAR(100),              -- 权限标识
    icon VARCHAR(100),               -- 菜单图标
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by BIGINT,
    deleted BOOLEAN DEFAULT FALSE,
    remark VARCHAR(500),             -- 备注
    CONSTRAINT fk_menu_parent_id FOREIGN KEY (parent_id) REFERENCES sys_menu (id)
);

COMMENT ON TABLE sys_menu IS '菜单权限表';
COMMENT ON COLUMN sys_menu.id IS '主键ID';
COMMENT ON COLUMN sys_menu.menu_name IS '菜单名称';
COMMENT ON COLUMN sys_menu.parent_id IS '父菜单ID';
COMMENT ON COLUMN sys_menu.order_num IS '显示顺序';
COMMENT ON COLUMN sys_menu.path IS '路由地址';
COMMENT ON COLUMN sys_menu.component IS '组件路径';
COMMENT ON COLUMN sys_menu.query_param IS '路由参数';
COMMENT ON COLUMN sys_menu.is_frame IS '是否为外链';
COMMENT ON COLUMN sys_menu.is_cache IS '是否缓存';
COMMENT ON COLUMN sys_menu.menu_type IS '菜单类型';
COMMENT ON COLUMN sys_menu.visible IS '显示状态';
COMMENT ON COLUMN sys_menu.status IS '菜单状态';
COMMENT ON COLUMN sys_menu.perms IS '权限标识';
COMMENT ON COLUMN sys_menu.icon IS '菜单图标';
COMMENT ON COLUMN sys_menu.created_at IS '创建时间';
COMMENT ON COLUMN sys_menu.created_by IS '创建人ID';
COMMENT ON COLUMN sys_menu.updated_at IS '更新时间';
COMMENT ON COLUMN sys_menu.updated_by IS '更新人ID';
COMMENT ON COLUMN sys_menu.deleted IS '是否删除';
COMMENT ON COLUMN sys_menu.remark IS '备注';

-- 创建用户-角色关联表
CREATE TABLE sys_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_ur_user_id FOREIGN KEY (user_id) REFERENCES sys_user (id),
    CONSTRAINT fk_ur_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id)
);

COMMENT ON TABLE sys_user_role IS '用户和角色关联表';
COMMENT ON COLUMN sys_user_role.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_role.role_id IS '角色ID';
COMMENT ON COLUMN sys_user_role.created_at IS '创建时间';
COMMENT ON COLUMN sys_user_role.created_by IS '创建人ID';

-- 创建角色-菜单关联表
CREATE TABLE sys_role_menu (
    role_id BIGINT NOT NULL,
    menu_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by BIGINT,
    PRIMARY KEY (role_id, menu_id),
    CONSTRAINT fk_rm_role_id FOREIGN KEY (role_id) REFERENCES sys_role (id),
    CONSTRAINT fk_rm_menu_id FOREIGN KEY (menu_id) REFERENCES sys_menu (id)
);

COMMENT ON TABLE sys_role_menu IS '角色和菜单关联表';
COMMENT ON COLUMN sys_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN sys_role_menu.created_at IS '创建时间';
COMMENT ON COLUMN sys_role_menu.created_by IS '创建人ID';

-- 添加登录日志索引
CREATE INDEX idx_sys_user_login_log_user_id ON sys_user_login_log(user_id);
CREATE INDEX idx_sys_user_login_log_created_at ON sys_user_login_log(created_at);
CREATE INDEX idx_sys_user_login_log_status ON sys_user_login_log(status);

-- 创建角色权限相关索引
CREATE INDEX idx_sys_role_status ON sys_role(status);
CREATE INDEX idx_sys_role_deleted ON sys_role(deleted);

CREATE INDEX idx_sys_menu_parent_id ON sys_menu(parent_id);
CREATE INDEX idx_sys_menu_status ON sys_menu(status);
CREATE INDEX idx_sys_menu_visible ON sys_menu(visible);
CREATE INDEX idx_sys_menu_deleted ON sys_menu(deleted);