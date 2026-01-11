package com.ruoyi.ai.service;

import java.util.List;
import com.ruoyi.ai.domain.Agent;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;

/**
 * 智能体Service接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface IAgentService 
{

    
    /**
     * 获取智能体
     * @param id
     * @return
     */
    StreamingChatLanguageModel getAgent(Long id);

    /**
     * 查询智能体
     * 
     * @param id 智能体主键
     * @return 智能体
     */
    public Agent selectAgentById(Long id);

    /**
     * 查询智能体列表
     * 
     * @param agent 智能体
     * @return 智能体集合
     */
    public List<Agent> selectAgentList(Agent agent);

    /**
     * 新增智能体
     * 
     * @param agent 智能体
     * @return 结果
     */
    public int insertAgent(Agent agent);

    /**
     * 修改智能体
     * 
     * @param agent 智能体
     * @return 结果
     */
    public int updateAgent(Agent agent);

    /**
     * 批量删除智能体
     * 
     * @param ids 需要删除的智能体主键集合
     * @return 结果
     */
    public int deleteAgentByIds(Long[] ids);

    /**
     * 删除智能体信息
     * 
     * @param id 智能体主键
     * @return 结果
     */
    public int deleteAgentById(Long id);

    /**
     * 删除后，智能体下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteAgent(Long[] ids);
}
