package com.ruoyi.ai.core;


import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

/**
 * 通用对话服务
 */
public interface UniversalChatService {

    // 基础聊天
    String chat(@UserMessage String message);

    // 带记忆的聊天
    String chatWithMemory(@MemoryId String sessionId, @UserMessage String message);

    // 流式聊天
    Flux<String> streamChat(@UserMessage String message);

    // 带记忆的流式聊天
    Flux<String> streamChatWithMemory(@MemoryId String sessionId, @UserMessage String message);
    // 流式聊天

}
