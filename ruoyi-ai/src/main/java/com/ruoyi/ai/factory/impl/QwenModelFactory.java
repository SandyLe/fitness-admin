package com.ruoyi.ai.factory.impl;

import com.ruoyi.ai.config.ModelType;
import com.ruoyi.ai.factory.AIModelFactory;
import com.ruoyi.common.utils.StringUtils;
import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class QwenModelFactory implements AIModelFactory {

    private static final Logger log = LoggerFactory.getLogger(QwenModelFactory.class);

    @Override
    public StreamingChatLanguageModel createStreamingModel(String baseUrl, String apiKey, String modeName) {
        log.info("创建 {} 流式聊天模型: {} {}", ModelType.QWEN.name(),modeName,apiKey);
        QwenStreamingChatModel.QwenStreamingChatModelBuilder builder = QwenStreamingChatModel.builder()
                .apiKey(apiKey)
                .modelName(modeName);
        if (StringUtils.isNotEmpty(baseUrl)) {
            // 只有在 baseUrl 不为空时才设置
            builder.baseUrl(baseUrl.trim());
        }
        return builder.build();
    }

    @Override
    public EmbeddingModel createEmbeddingModel(String baseUrl, String apiKey, String modeName) {
        log.info("创建 {} 嵌入模型: {}", ModelType.OPENAI.name(),modeName);
        return QwenEmbeddingModel.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .modelName(modeName)
                .build();
    }

    @Override
    public ModelType supportedModelType() {
        return ModelType.QWEN;
    }
}
