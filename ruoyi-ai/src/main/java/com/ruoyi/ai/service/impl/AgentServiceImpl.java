package com.ruoyi.ai.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ruoyi.ai.domain.AiModel;
import com.ruoyi.ai.domain.ConversationSession;
import com.ruoyi.ai.factory.DynamicModelFactoryManager;
import com.ruoyi.ai.mapper.ConversationSessionMapper;
import com.ruoyi.ai.service.*;
import com.ruoyi.common.utils.SecurityUtils;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.AgentMapper;
import com.ruoyi.ai.domain.Agent;
import org.springframework.transaction.annotation.Transactional;

/**
 * 智能体Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@Service
public class AgentServiceImpl implements IAgentService 
{
    private static final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);


    //临时缓存
    private final Map<String, Object> modelCache = new ConcurrentHashMap<>();
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private IConversationSessionService conversationSessionService;

    @Autowired
    private IAiModelService aiModelService;
    @Autowired
    private DynamicModelFactoryManager dynamicModelFactoryManager;
    @Autowired
    private IChatMessagesService chatMessagesService;




    @Override
    public StreamingChatLanguageModel getAgent(Long id) {
        Agent agent = selectAgentById(id); //查找智能体
        AiModel aiModel = aiModelService.selectAiModelById(agent.getModelId()); //查找大模型
        StreamingChatLanguageModel streamingChatLanguageModel = dynamicModelFactoryManager.createStreamingModel(aiModel);
        return streamingChatLanguageModel;
    }


    /**
     * 查询智能体
     * 
     * @param id 智能体主键
     * @return 智能体
     */
    @Override
    public Agent selectAgentById(Long id)
    {
        return agentMapper.selectAgentById(id);
    }

    /**
     * 查询智能体列表
     * 
     * @param agent 智能体
     * @return 智能体
     */
    @Override
    public List<Agent> selectAgentList(Agent agent)
    {
        return agentMapper.selectAgentList(agent);
    }

    /**
     * 新增智能体
     * 
     * @param agent 智能体
     * @return 结果
     */
    @Override
    public int insertAgent(Agent agent)
    {
//        agent.setStatus(0);
        agent.setUserId(SecurityUtils.getUserId());
        agent.setCreateBy(SecurityUtils.getUsername());
        agent.setCreateTime(new Date());
        return agentMapper.insertAgent(agent);
    }

    /**
     * 修改智能体
     * 
     * @param agent 智能体
     * @return 结果
     */
    @Override
    public int updateAgent(Agent agent)
    {
        agent.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        agent.setUpdateTime(new Date());
        return agentMapper.updateAgent(agent);
    }

    /**
     * 批量删除智能体
     * 
     * @param ids 需要删除的智能体主键
     * @return 结果
     */
    @Override
    public int deleteAgentByIds(Long[] ids)
    {
        return agentMapper.deleteAgentByIds(ids);
    }

    /**
     * 删除智能体信息
     * 
     * @param id 智能体主键
     * @return 结果
     */
    @Override
    public int deleteAgentById(Long id)
    {
        return agentMapper.deleteAgentById(id);
    }

    /**
     * 删除后，智能体下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int deleteAgent(Long[] ids) {
        for (Long agentId:ids){
            conversationSessionService.deleteConversationSessionByAgentId(agentId);
        }
        //删除智能体
        return deleteAgentByIds(ids);
    }
}
