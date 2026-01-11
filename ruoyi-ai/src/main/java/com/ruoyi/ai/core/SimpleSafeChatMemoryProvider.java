package com.ruoyi.ai.core;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的安全聊天记忆提供器
 */
@Slf4j
@Component
public class SimpleSafeChatMemoryProvider implements ChatMemoryProvider {

    private final ChatMemoryProvider delegate;
    private final Map<Object, String> lastUserMessageCache = new ConcurrentHashMap<>();

    /**
     * 使用您现有的 MongoDB ChatMemoryStore
     */
    @Autowired
    public SimpleSafeChatMemoryProvider(ChatMemoryStore mongoChatMemoryStore) {
        this.delegate = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(100)  // 保持您原有的配置
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }


    @Override
    public ChatMemory get(Object memoryId) {
        ChatMemory originalMemory = delegate.get(memoryId);

        // 返回一个匿名实现，只重写必要的方法
        return new ChatMemory() {
            @Override
            public void add(ChatMessage message) {
                // 防止重复的用户消息
                if (message instanceof UserMessage) {
                    String currentText = extractTextFromUserMessage((UserMessage) message);
                    String lastText = lastUserMessageCache.get(memoryId);

                    if (currentText != null && currentText.equals(lastText)) {
                        log.warn("🚫 阻止重复用户消息: memoryId={}, message={}", memoryId,
                                truncateMessage(currentText));
                        return;
                    }

                    lastUserMessageCache.put(memoryId, currentText);
                    log.debug("✅ 添加用户消息: memoryId={}, message={}", memoryId,
                            truncateMessage(currentText));
                }

                originalMemory.add(message);
            }

            @Override
            public List<ChatMessage> messages() {
                return originalMemory.messages();
            }

            @Override
            public void clear() {
                lastUserMessageCache.remove(memoryId);
                originalMemory.clear();
                log.info("🧹 清理记忆: memoryId={}", memoryId);
            }

            @Override
            public Object id() {
                return originalMemory.id();
            }
        };
    }

    private String extractTextFromUserMessage(UserMessage userMessage) {
        try {
            return userMessage.contents().stream()
                    .filter(content -> content instanceof dev.langchain4j.data.message.TextContent)
                    .map(content -> ((dev.langchain4j.data.message.TextContent) content).text())
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            log.warn("提取用户消息文本失败", e);
            return null;
        }
    }

    private String truncateMessage(String message) {
        if (message == null) return "null";
        return message.length() > 50 ? message.substring(0, 50) + "..." : message;
    }

    // ==================== 工具方法 ====================

    public void clearAllCaches() {
        int size = lastUserMessageCache.size();
        lastUserMessageCache.clear();
        log.info("🧹 清理所有记忆缓存，共清理 {} 个缓存项", size);
    }

    public void clearCacheForMemoryId(Object memoryId) {
        if (lastUserMessageCache.containsKey(memoryId)) {
            lastUserMessageCache.remove(memoryId);
            log.info("🧹 清理记忆缓存: memoryId={}", memoryId);
        }
    }
}