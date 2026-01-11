package com.ruoyi.ai.service;

import java.util.List;
import com.ruoyi.ai.domain.ConversationSession;
import com.ruoyi.ai.domain.dto.ChatFormDTO;

/**
 * 对话会话Service接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface IConversationSessionService 
{



    /**
     * 记录会话开始
     * @param chatFormDTO
     */
    void recordSessionStart(ChatFormDTO chatFormDTO,Long userId,String username);

    /**
     * 记录会话结束
     * @param chatFormDTO
     */
    void recordSessionEnd(ChatFormDTO chatFormDTO,String username);


    /**
     * 记录会话错误
     * @param chatFormDTO
     * @param error
     */
    void recordSessionError(ChatFormDTO chatFormDTO, Throwable error,String username);
    /**
     * 查询对话会话
     * 
     * @param id 对话会话主键
     * @return 对话会话
     */
    public ConversationSession selectConversationSessionById(Long id);

    /**
     * 查询对话会话列表
     * 
     * @param conversationSession 对话会话
     * @return 对话会话集合
     */
    public List<ConversationSession> selectConversationSessionList(ConversationSession conversationSession);

    /**
     * 新增对话会话
     * 
     * @param conversationSession 对话会话
     * @return 结果
     */
    public int insertConversationSession(ConversationSession conversationSession);

    /**
     * 修改对话会话
     * 
     * @param conversationSession 对话会话
     * @return 结果
     */
    public int updateConversationSession(ConversationSession conversationSession);

    /**
     * 批量删除对话会话
     * 
     * @param ids 需要删除的对话会话主键集合
     * @return 结果
     */
    public int deleteConversationSessionByIds(Long[] ids);

    /**
     * 删除对话会话信息
     * 
     * @param id 对话会话主键
     * @return 结果
     */
    public int deleteConversationSessionById(Long id);

    /**
     * 根据智能体ID删除对话会话信息及对话详情
     *
     * @param agentId 智能体ID
     * @return 结果
     */
    public int deleteConversationSessionByAgentId(Long agentId);
}
