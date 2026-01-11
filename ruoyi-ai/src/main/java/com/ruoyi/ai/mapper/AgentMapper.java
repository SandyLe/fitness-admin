package com.ruoyi.ai.mapper;

import java.util.List;
import com.ruoyi.ai.domain.Agent;

/**
 * 智能体Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface AgentMapper 
{
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
     * 删除智能体
     * 
     * @param id 智能体主键
     * @return 结果
     */
    public int deleteAgentById(Long id);

    /**
     * 批量删除智能体
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAgentByIds(Long[] ids);
}
