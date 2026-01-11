package com.ruoyi.ai.web;

import com.ruoyi.common.config.BaseOpenApiConfig;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiOpenApiConfig extends BaseOpenApiConfig {

    /**
     * Ai通用服务接口
     */
    @Bean
    public GroupedOpenApi aiCommonOpenApi() {
        return createGroup("Ai通用接口",
                "com.ruoyi.ai.web.controller",
                "/ai/**");
    }
}
