# 配置.env

1. 在根目录下创建.env 文件
2. 文件内容

```
#数据库相关
DEV_DATABASE_URL=
DEV_DATABASE_USERNAME=
DEV_DATABASE_PASSWORD=

#redis相关
DEV_REDIS_HOST=
DEV_REDIS_PORT=
DEV_REDIS_PASSWORD=
DEV_REDIS_DATABASE=

#公私钥相关
PUBLIC_KEY_PATH=
PRIVATE_KEY_PATH=

#JWT相关
JWT_SECRET_KEY=
```

# 初始化公私钥

RockBladeSystemWebApplicationTests
@Test
public void initKeyPair()
