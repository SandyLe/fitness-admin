package com.ruoyi.framework.config;

import com.ruoyi.common.i18n.MultiSourceLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import com.ruoyi.common.constant.Constants;

/**
 * 资源文件配置加载
 * 
 * @author ruoyi
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer
{
    @Bean
    public LocaleResolver localeResolver()
    {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
        MultiSourceLocaleResolver resolver = new MultiSourceLocaleResolver(); //注册自定义语言解析器
        // 默认语言
        resolver.setDefaultLocale(Constants.DEFAULT_LOCALE);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
