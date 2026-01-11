package com.ruoyi.framework.config.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RegExUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import com.ruoyi.common.annotation.Anonymous;
import org.springframework.web.util.pattern.PathPattern;

/**
 * 设置Anonymous注解允许匿名访问的url
 * 
 * @author ruoyi
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware
{
    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    private ApplicationContext applicationContext;

    private List<String> urls = new ArrayList<>();

    public String ASTERISK = "*";

    @Override
    public void afterPropertiesSet()
    {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            Anonymous method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), Anonymous.class);
            Optional.ofNullable(method).ifPresent(anonymous -> {
                // 获取所有路径
                List<String> patterns = getPatterns(info);
                patterns.forEach(url -> urls.add(RegExUtils.replaceAll(url, PATTERN, ASTERISK)));
            });

            // 获取类上边的注解, 替代path variable 为 *
            Anonymous controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Anonymous.class);
            Optional.ofNullable(controller).ifPresent(anonymous -> {
                // 获取所有路径
                List<String> patterns = getPatterns(info);
                patterns.forEach(url -> urls.add(RegExUtils.replaceAll(url, PATTERN, ASTERISK)));
            });
        });

        // 去重
        urls = urls.stream().distinct().collect(Collectors.toList());
    }


    /**
     * 兼容不同Spring Boot版本的路径获取方法
     */
    private List<String> getPatterns(RequestMappingInfo info) {
        List<String> patterns = new ArrayList<>();

        // 方法1：获取直接路径（兼容性最好）
        if (info.getDirectPaths() != null && !info.getDirectPaths().isEmpty()) {
            patterns.addAll(info.getDirectPaths());
        }

        // 方法2：Spring Boot 2.6+ 使用 PathPatternsCondition
        if (info.getPathPatternsCondition() != null) {
            patterns.addAll(
                    info.getPathPatternsCondition().getPatterns().stream()
                            .map(PathPattern::getPatternString)
                            .collect(Collectors.toList())
            );
        }

        // 方法3：Spring Boot 2.5及以下使用 PatternsCondition
        if (info.getPatternsCondition() != null) {
            patterns.addAll(info.getPatternsCondition().getPatterns());
        }

        // 方法4：使用 patternValues
        if (info.getPatternValues() != null && !info.getPatternValues().isEmpty()) {
            patterns.addAll(info.getPatternValues());
        }

        return patterns;
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        this.applicationContext = context;
    }

    public List<String> getUrls()
    {
        return urls;
    }

    public void setUrls(List<String> urls)
    {
        this.urls = urls;
    }
}
