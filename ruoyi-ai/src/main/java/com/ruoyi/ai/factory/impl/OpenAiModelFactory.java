package com.ruoyi.ai.factory.impl;

import com.ruoyi.ai.config.ModelType;
import com.ruoyi.ai.factory.AIModelFactory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class OpenAiModelFactory implements AIModelFactory {

    private static final Logger log = LoggerFactory.getLogger(OpenAiModelFactory.class);


    @Override
    public StreamingChatLanguageModel createStreamingModel(String baseUrl, String apiKey, String modeName) {
        log.info("创建 {} 流式聊天模型: {}", ModelType.OPENAI.name(), modeName);
        return OpenAiStreamingChatModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modeName)
//                .temperature(Double.valueOf(config.getTemperature()))
//                .topP(config.getTopP())
//                .seed(config.getSeed())
//                .maxTokens(config.getMaxTokens())
                .build();
    }

    @Override
    public EmbeddingModel createEmbeddingModel(String baseUrl, String apiKey, String modeName) {
        log.info("创建 {} 嵌入模型: {}", ModelType.OPENAI.name(), modeName);
        return OpenAiEmbeddingModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modeName)
                .build();
    }

    @Override
    public ModelType supportedModelType() {
        return ModelType.OPENAI;
    }
}
