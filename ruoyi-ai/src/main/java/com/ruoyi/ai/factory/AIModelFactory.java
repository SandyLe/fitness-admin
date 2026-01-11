package com.ruoyi.ai.factory;

import com.ruoyi.ai.config.ModelType;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;

/**
 * 模型工厂
 *
 * ChatLanguageModel 聊天模型
 * StreamingChatLanguageModel 流式聊天模型
 * EmbeddingModel 嵌入模型
 * MultimodalModel 多模态模型，应用场景：图像理解、文档分析、视觉问答
 * CompletionModel 补全模型，应用：代码补全、文本生成、内容续写
 * SpeechToTextModel 语音模型，应用：语音助手、语音笔记、音频内容生成
 * TextToSpeechModel 语音模型，应用：语音助手、语音笔记、音频内容生成
 * ImageModel 图像生成模型，应用：创意设计、营销素材、艺术创作
 * ModerationModel 审核模型，应用：内容审核、安全过滤、合规检查
 * ToolCallingModel 排序/重排模型，应用：API 调用、工具使用、外部系统集成
 * RerankingModel  函数调用模型，应用：搜索引擎优化、推荐系统
 * CodeModel  代码模型，应用：代码补全、bug 检测、代码解释
 * FineTunedModel  微调模型，应用：专业领域问答、企业专属模型
 * RagModel  检索增强生成模型，应用：知识库问答、文档分析
 * StructuredOutputModel  结构化输出模型，应用：数据提取、API 响应生成
 * BatchProcessingModel  批量处理模型，应用：大规模数据清洗、批量内容生成
 * AgentModel  代理模型，应用：自动化工作流、复杂任务处理
 * EvaluationMode  评估模型，应用：模型效果评估、质量监控
 */
public interface AIModelFactory {


    /**
     * 创建流式聊天模型
     */
    StreamingChatLanguageModel createStreamingModel(String baseUrl, String apiKey, String modeName);

    /**
     * 创建嵌入模型
     */
    EmbeddingModel createEmbeddingModel(String baseUrl, String apiKey, String modeName);

    /**
     * 支持的所有模型类型
     */
    ModelType supportedModelType();
}
