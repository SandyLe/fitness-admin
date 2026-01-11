package com.ruoyi.web;

import com.ruoyi.common.config.BaseOpenApiConfig;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemOpenApiConfig extends BaseOpenApiConfig {

    /**
     * 通用服务接口
     */
    @Bean
    public GroupedOpenApi commonOpenApi() {
        return createGroup("通用服务接口",
                "com.ruoyi.web.controller.common",
                "/common/**");
    }

    /**
     * 监控服务接口
     */
    @Bean
    public GroupedOpenApi monitorOpenApi() {
        return createGroup("监控服务接口",
                "com.ruoyi.web.controller.monitor",
                "/monitor/**");
    }

    /**
     * 系统监控接口
     */
    @Bean
    public GroupedOpenApi systemOpenApi() {
        return createGroup("系统服务接口",
                "com.ruoyi.web.controller.system",
                "/system/**");
    }
}
