package com.ruoyi.ai.config;

import com.ruoyi.ai.core.SimpleSafeChatMemoryProvider;
import com.ruoyi.ai.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * 小智智能助手配置
 * 功能最完整的智能助手配置
 * 对话记忆：100条消息，MongoDB 持久化
 * 知识检索：基于 Pinecone 向量数据库的 RAG 功能
 * 支持语义搜索和知识增强
 * 适用于复杂、长对话、需要知识库支持的场景
 */
@Configuration
public class XiaozhiAgentConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore<TextSegment> redisEmbeddingStore;


    /**
     * 对话记忆：100条消息，MongoDB 持久化
     *
     */
    @Bean
    public ChatMemoryProvider chatMemoryProviderXiaozhi() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(100)  //存储信息最大100条
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }

    /**
     * 注册安全的 ChatMemoryProvider 为主要实现
     */
    @Bean
    @Primary
    public ChatMemoryProvider safeChatMemoryProvider(ChatMemoryStore mongoChatMemoryStore) {
        return new SimpleSafeChatMemoryProvider(mongoChatMemoryStore);
    }


    /**
     * 全局构建向量存储加载器，这样可以使用同一个redis索引
     * @return
     */
    @Bean
    public EmbeddingStoreIngestor embeddingStoreIngestor() {
        return EmbeddingStoreIngestor.builder()
                //块分割大小2000字符，重叠字符200
                .documentSplitter(DocumentSplitters.recursive(1500, 150))
//                .embeddingStore(embeddingStore())  //内存存储，本地临时解决方案
                .embeddingModel(embeddingModel)
                .embeddingStore(redisEmbeddingStore)
                .build();
    }

    /**
     * 注册向量存储的检索器
     */
//    @Bean
//    public ContentRetriever contentRetrieverInMemory() {
//        return EmbeddingStoreContentRetriever.builder()
//                .embeddingModel(embeddingModel)
////                .embeddingStore(embeddingStore())  //内存存储，本地临时解决方案
//                .embeddingStore(redisEmbeddingStore)  //redisSearch服务
//                .maxResults(10)
//                .minScore(0.5)
//                .build();
//    }

}

