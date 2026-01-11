package com.ruoyi.ai.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ruoyi.ai.domain.ChatMessages;
import com.ruoyi.ai.domain.dto.ChatFormDTO;
import com.ruoyi.ai.service.IChatMessagesService;
import com.ruoyi.common.utils.SecurityUtils;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.ConversationSessionMapper;
import com.ruoyi.ai.domain.ConversationSession;
import com.ruoyi.ai.service.IConversationSessionService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 对话会话Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@Service
public class ConversationSessionServiceImpl implements IConversationSessionService 
{
    private static final Logger log = LoggerFactory.getLogger(IConversationSessionService.class);
    @Autowired
    private ConversationSessionMapper conversationSessionMapper;
    @Autowired
    private IChatMessagesService chatMessagesService;

    @Override
    public void recordSessionStart(ChatFormDTO chatFormDTO,Long userId,String username) {
        try {
            // 检查是否已存在会话
            ConversationSession existing = conversationSessionMapper.selectByMemoryId(chatFormDTO.getMemoryId());
            if (existing == null) {
                // 创建新会话
                ConversationSession session = new ConversationSession();
                session.setMemoryId(String.valueOf(chatFormDTO.getMemoryId()));
                session.setAgentId(chatFormDTO.getAgentId());
                session.setUserId(userId);
                session.setCreateBy(username);
                session.setSessionTitle(extractTitle(chatFormDTO.getMessage()));
                session.setStartTime(new Date());
                conversationSessionMapper.insertConversationSession(session);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("记录会话开始失败: {}", e.getMessage());
        }
    }


    public void recordSessionEnd(ChatFormDTO chatFormDTO,String username) {
        try {
            ConversationSession session = conversationSessionMapper.selectByMemoryId(chatFormDTO.getMemoryId());
            if (session != null) {
                // 从MongoDB获取最新统计
                updateSessionStatsFromMongo(session, chatFormDTO.getMemoryId());
                // 更新结束时间
                session.setEndTime(new Date());
                session.setUpdateBy(username);
                session.setUpdateTime(new Date());
                conversationSessionMapper.updateConversationSession(session);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("记录会话结束失败: {}", e.getMessage());
        }
    }

    private void updateSessionStatsFromMongo(ConversationSession session, String memoryId) {
        try {
            ChatMessages chatMessages = chatMessagesService.selectChatMessagesById(memoryId);
            if (chatMessages != null) {
                List<ChatMessage> messages = ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
                session.setMessageCount((long) messages.size());
                // 简单token计算 TODO
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("从MongoDB更新会话统计失败", e);
        }
    }

    @Override
    public void recordSessionError(ChatFormDTO chatFormDTO, Throwable error, String username) {
        try {
            ConversationSession session = conversationSessionMapper.selectByMemoryId(chatFormDTO.getMemoryId());
            if (session != null) {
                session.setEndTime(new Date());
                // 可以添加错误状态字段
                session.setUpdateBy(username);
                session.setUpdateTime(new Date());
                conversationSessionMapper.updateConversationSession(session);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("记录会话错误失败", e);
        }
    }

    /**
     * 提取标题
     */
    private String extractTitle(String message) {
        if (message == null || message.trim().isEmpty()) {
            return "新对话";
        }
        String msg = message.trim();
        return msg.length() > 20 ? msg.substring(0, 20) + "..." : msg;
    }

    /**
     * 查询对话会话
     * 
     * @param id 对话会话主键
     * @return 对话会话
     */
    @Override
    public ConversationSession selectConversationSessionById(Long id)
    {
        return conversationSessionMapper.selectConversationSessionById(id);
    }

    /**
     * 查询对话会话列表
     * 
     * @param conversationSession 对话会话
     * @return 对话会话
     */
    @Override
    public List<ConversationSession> selectConversationSessionList(ConversationSession conversationSession)
    {
        return conversationSessionMapper.selectConversationSessionList(conversationSession);
    }

    /**
     * 新增对话会话
     * 
     * @param conversationSession 对话会话
     * @return 结果
     */
    @Override
    public int insertConversationSession(ConversationSession conversationSession)
    {
        conversationSession.setCreateBy(SecurityUtils.getUsername());
        conversationSession.setCreateTime(new Date());
        return conversationSessionMapper.insertConversationSession(conversationSession);
    }

    /**
     * 修改对话会话
     * 
     * @param conversationSession 对话会话
     * @return 结果
     */
    @Override
    public int updateConversationSession(ConversationSession conversationSession)
    {
        conversationSession.setUpdateBy(SecurityUtils.getUsername());
        conversationSession.setUpdateTime(new Date());
        return conversationSessionMapper.updateConversationSession(conversationSession);
    }

    /**
     * 批量删除对话会话
     * 
     * @param ids 需要删除的对话会话主键
     * @return 结果
     */
    @Override
    public int deleteConversationSessionByIds(Long[] ids)
    {
        return conversationSessionMapper.deleteConversationSessionByIds(ids);
    }

    /**
     * 删除对话会话信息
     * 
     * @param id 对话会话主键
     * @return 结果
     */
    @Override
    public int deleteConversationSessionById(Long id)
    {
        return conversationSessionMapper.deleteConversationSessionById(id);
    }

    @Override
    @Transactional
    public int deleteConversationSessionByAgentId(Long agentId) {
        //删除对话信息
        ConversationSession conversationSession = new ConversationSession();
        conversationSession.setAgentId(agentId);
        List<ConversationSession> conversationSessionList = selectConversationSessionList(conversationSession);
        if(null != conversationSessionList && !conversationSessionList.isEmpty()){
            // 提取 memoryId 并去重
            List<String> distinctMemoryIds = conversationSessionList.stream()
                    .map(ConversationSession::getMemoryId)
                    .filter(Objects::nonNull)
                    .distinct()                              // 去重
                    .collect(Collectors.toList());
            chatMessagesService.deleteChatMessagesByIds(distinctMemoryIds);
        }
        return conversationSessionMapper.deleteConversationSessionByAgentId(agentId);
    }
}
