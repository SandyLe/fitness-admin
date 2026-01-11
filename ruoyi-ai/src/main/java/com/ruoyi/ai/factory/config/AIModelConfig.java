//package com.ruoyi.ai.factory.config;
//
//import com.alibaba.dashscope.common.ResponseFormat;
//import com.ruoyi.ai.config.ModelType;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.Duration;
//import java.util.List;
//import java.util.Map;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class AIModelConfig {
//    // 基础配置
//    private ModelType modelType;
//    private String modelName;
//    private String apiKey;
//    private String baseUrl;
//
//    // 模型参数
//    private Float temperature;
//    private Double topP;
//    private Integer topK;
//    private Integer maxTokens;
//    private Double frequencyPenalty;
//    private Double presencePenalty;
//    private Float repetitionPenalty;
//    private Integer seed;
//    private Boolean enableSearch;
//
//    // 高级配置
//    private List<String> stopSequences;
//    private ResponseFormat responseFormat;
//    private Map<String, Object> extraParams;
//
//    // 连接配置
//    private Duration timeout;
//    private Integer maxRetries;
//    private Boolean logRequests;
//    private Boolean logResponses;
//
//    // 流式配置
//    private StreamingConfig streamingConfig;
//}
