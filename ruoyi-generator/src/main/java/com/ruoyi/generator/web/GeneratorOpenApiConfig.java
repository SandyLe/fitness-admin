package com.ruoyi.generator.web;

import com.ruoyi.common.config.BaseOpenApiConfig;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorOpenApiConfig extends BaseOpenApiConfig {

    /**
     * 工具服务接口
     */
    @Bean
    public GroupedOpenApi toolOpenApi() {
        return createGroup("工具服务接口",
                "com.ruoyi.generator.web.controller",
                "/tool/**");
    }
}
