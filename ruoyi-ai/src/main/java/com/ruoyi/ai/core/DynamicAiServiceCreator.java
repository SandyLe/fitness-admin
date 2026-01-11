package com.ruoyi.ai.core;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.ruoyi.ai.domain.Agent;
import com.ruoyi.ai.service.IAgentService;
import com.ruoyi.common.utils.StringUtils;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * AI服务
 * 在这个服务类中完成模型、提示词、向量数据检索、工具调用组装后，生成AI服务
 */
@Component
public class DynamicAiServiceCreator {
    private final IAgentService agentService;
    private final ChatMemoryProvider chatMemoryProvider;

    private final KnowledgeVectorStoreService knowledgeVectorStoreService;


    public DynamicAiServiceCreator(IAgentService agentService,
                                   @Qualifier("chatMemoryProviderXiaozhi") ChatMemoryProvider chatMemoryProvider,
                                   @Qualifier("knowledgeVectorStoreService") KnowledgeVectorStoreService knowledgeVectorStoreService) {
        this.agentService = agentService;
        this.chatMemoryProvider = chatMemoryProvider;
        this.knowledgeVectorStoreService = knowledgeVectorStoreService;
    }

    /**
     * 创建基础的聊天服务
     * agentId 智能体ID
     */
    public <T> T createChatService(Class<T> serviceClass, Long agentId) {
        return createChatService(serviceClass, agentId, null);
    }


    /**
     * 创建带工具的聊天服务
     * agentId 智能体ID
     */
    public <T> T createChatService(Class<T> serviceClass, Long agentId, List<Object> tools) {
        StreamingChatLanguageModel serviceAgent = agentService.getAgent(agentId);
        //通过agentId获取配置的知识库ID，查询知识库下的所有文档，加载到向量存储数据库中
        Agent agent = agentService.selectAgentById(agentId);
        AiServices<T> builder = AiServices.builder(serviceClass)
                .streamingChatLanguageModel(serviceAgent) //配置AI模型
                .chatMemoryProvider(chatMemoryProvider)  //配置聊天记录，对话记忆
                .systemMessageProvider(memoryId -> agent.getSystemPrompt()); //系统提示词
        //添加工具类
        if (null != tools && !tools.isEmpty()) {
            builder.tools(tools.toArray());
        }
        //添加知识库
        if(isValidLongList(agent.getKnowledgeBaseIds())){
            List<Long> kbIds = Arrays.stream(agent.getKnowledgeBaseIds().split(",")).map(Long::valueOf).collect(Collectors.toList());
            ContentRetriever customerContentRetriever = new CustomerContentRetriever.Builder(knowledgeVectorStoreService).withKnowledgeBaseIds(kbIds).build();
            builder.contentRetriever(customerContentRetriever); //自定义内容检索，知识库
        }
        return builder.build();
    }


    /**
     * 验证字符串是否是有效的 Long 列表（支持 "1" 和 "[1,2]" 两种格式）
     */
    public static boolean isValidLongList(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        str = str.trim();
        // 情况1：已经是 JSON 数组格式
        if (str.startsWith("[") && str.endsWith("]")) {
            try {
                List<Long> list = JSON.parseArray(str, Long.class);
                return list != null && !list.isEmpty();
            } catch (JSONException e) {
                return false;
            }
        }
        // 情况2：单个数字字符串
        if (isValidLong(str)) {
            return true;
        }
        // 情况3：可能是逗号分隔的字符串 "1,2,3"
        if (str.contains(",")) {
            return isValidCommaSeparatedLongList(str);
        }
        return false;
    }


    /**
     * 验证逗号分隔的长整型字符串
     */
    private static boolean isValidCommaSeparatedLongList(String str) {
        String[] parts = str.split(",");
        for (String part : parts) {
            if (!isValidLong(part.trim())) {
                return false;
            }
        }
        return parts.length > 0;
    }

    /**
     * 验证单个字符串是否是有效的 Long
     */
    private static boolean isValidLong(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }

        try {
            Long.parseLong(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
