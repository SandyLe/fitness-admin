package com.ruoyi.ai.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 智能体对象 ai_agent
 *
 * @author ruoyi
 * @date 2025-12-01
 */
public class Agent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 智能体ID */
    private Long id;

    /** 智能体名称 */
    @Excel(name = "智能体名称")
    private String agentName;

    /** 智能体描述 */
    @Excel(name = "智能体描述")
    private String agentDescription;

    /** 使用的模型ID */
    @Excel(name = "使用的模型ID")
    private Long modelId;

    /** 关联的知识库ID列表(逗号分隔) */
    @Excel(name = "关联的知识库ID列表(逗号分隔)")
    private String knowledgeBaseIds;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    private Long userId;

    /** 系统提示词 */
    @Excel(name = "系统提示词")
    private String systemPrompt;

    /** 温度参数(0-1) */
    @Excel(name = "温度参数(0-1)")
    private BigDecimal temperature;

    /** 最大token数 */
    @Excel(name = "最大token数")
    private Long maxTokens;

    /** 状态(0:禁用 1:启用) */
    @Excel(name = "状态(0:禁用 1:启用)")
    private Integer status;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }

    public String getAgentName()
    {
        return agentName;
    }

    public void setAgentDescription(String agentDescription)
    {
        this.agentDescription = agentDescription;
    }

    public String getAgentDescription()
    {
        return agentDescription;
    }

    public void setModelId(Long modelId)
    {
        this.modelId = modelId;
    }

    public Long getModelId()
    {
        return modelId;
    }

    public void setKnowledgeBaseIds(String knowledgeBaseIds)
    {
        this.knowledgeBaseIds = knowledgeBaseIds;
    }

    public String getKnowledgeBaseIds()
    {
        return knowledgeBaseIds;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setSystemPrompt(String systemPrompt)
    {
        this.systemPrompt = systemPrompt;
    }

    public String getSystemPrompt()
    {
        return systemPrompt;
    }

    public void setTemperature(BigDecimal temperature)
    {
        this.temperature = temperature;
    }

    public BigDecimal getTemperature()
    {
        return temperature;
    }

    public void setMaxTokens(Long maxTokens)
    {
        this.maxTokens = maxTokens;
    }

    public Long getMaxTokens()
    {
        return maxTokens;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("agentName", getAgentName())
                .append("agentDescription", getAgentDescription())
                .append("modelId", getModelId())
                .append("knowledgeBaseIds", getKnowledgeBaseIds())
                .append("userId", getUserId())
                .append("systemPrompt", getSystemPrompt())
                .append("temperature", getTemperature())
                .append("maxTokens", getMaxTokens())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
