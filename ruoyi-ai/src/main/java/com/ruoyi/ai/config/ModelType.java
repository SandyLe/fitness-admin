package com.ruoyi.ai.config;

/**
 * 接入的模型协议
 * OPENAI 通用模型接入客户端
 * QWEN  阿里云模型接入客户端
 */
public enum ModelType {

    OPENAI("OpenAI"),
    QWEN("通义千问"),
    UNKNOWN("未知类型");;
    private final String description;

    ModelType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ModelType fromString(String type) {
        if (type == null) return UNKNOWN;
        switch (type.toUpperCase()) {
            case "OPENAI": return OPENAI;
            case "QWEN": return QWEN;
            default: return UNKNOWN;
        }
    }

}
