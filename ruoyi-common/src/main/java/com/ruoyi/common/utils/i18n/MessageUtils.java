package com.ruoyi.common.utils.i18n;

import jakarta.annotation.PostConstruct;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 * 国际化工具类
 */
@Component
public class MessageUtils implements MessageSourceAware {

    private static MessageUtils instance;

    private MessageSource messageSource;

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        instance = this;
    }

    /**
     * 获取国际化消息
     *
     * @param code 消息代码
     * @param args 消息参数
     * @return 国际化消息
     */
    public static String message(String code, Object... args) {
        Assert.notNull(instance, "MessageUtils 未初始化");
        Locale locale = LocaleContextHolder.getLocale();
        return instance.messageSource.getMessage(code, args, code, locale);
    }

    /**
     * 获取国际化消息
     *
     * @param code 消息代码
     * @param args 消息参数
     * @param defaultMessage 默认消息
     * @return 国际化消息
     */
    public static String message(String code, Object[] args, String defaultMessage) {
        Assert.notNull(instance, "MessageUtils 未初始化");
        Locale locale = LocaleContextHolder.getLocale();
        return instance.messageSource.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * 获取当前语言
     *
     * @return 当前语言
     */
    public static String getCurrentLanguage() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.toLanguageTag();
    }

    /**
     * 获取当前语言简码
     *
     * @return 语言简码
     */
    public static String getCurrentLanguageCode() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.getLanguage();
    }
}