package com.ruoyi.common.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;

/**
 * OpenAPI 基础配置类
 * 其他模块可以通过继承此类来添加自己的 API 分组
 */
public abstract class BaseOpenApiConfig {

    @Autowired
    protected RuoYiConfig ruoyiConfig;

    /**
     * 全局 OpenAPI 配置
     * 子类可以通过 @Bean 覆盖此方法来自定义配置
     */
    @Bean
    public OpenAPI globalOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AI医疗客服系统 API")
                        .version(ruoyiConfig.getVersion())
                        .description("AI智能客服系统完整接口文档")
                        .contact(new Contact()
                                .name(ruoyiConfig.getName())
                                .email("")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .name("Authorization")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("JWT Token")));
    }

    /**
     * 创建分组的方法 - 供子类使用
     */
    protected GroupedOpenApi createGroup(String groupName, String packagePath, String pathPattern) {
        return GroupedOpenApi.builder()
                .group(groupName)
                .packagesToScan(packagePath)
                .pathsToMatch(pathPattern)
                .build();
    }

    /**
     * 创建仅按路径分组的方- 供子类使用
     */
    protected GroupedOpenApi createPathGroup(String groupName, String pathPattern) {
        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch(pathPattern)
                .build();
    }

    /**
     * 创建仅按包分组的方- 供子类使用
     */
    protected GroupedOpenApi createPackageGroup(String groupName, String packagePath) {
        return GroupedOpenApi.builder()
                .group(groupName)
                .packagesToScan(packagePath)
                .build();
    }
}
