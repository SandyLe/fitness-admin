package com.ruoyi.ai.mapper;

import java.util.List;
import com.ruoyi.ai.domain.ConversationSession;
import org.apache.ibatis.annotations.Param;

/**
 * 对话会话Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface ConversationSessionMapper 
{

    ConversationSession selectByMemoryId(String memoryId);
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
     * 删除对话会话
     * 
     * @param id 对话会话主键
     * @return 结果
     */
    public int deleteConversationSessionById(Long id);

    /**
     * 批量删除对话会话
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConversationSessionByIds(Long[] ids);

    /**
     * 根据智能体ID删除对话信息
     * @param agentId
     * @return
     */
    int deleteConversationSessionByAgentId(@Param("agentId") Long agentId);
}
