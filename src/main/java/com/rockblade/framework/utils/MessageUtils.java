/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:33:22
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-15 23:34:05
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/utils/MessageUtils.java
 * @Description: 获取i18n资源文件
 * 
 * Copyright (c) 2025 by DB, All Rights Reserved. 
 */
package com.rockblade.framework.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtils {

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
