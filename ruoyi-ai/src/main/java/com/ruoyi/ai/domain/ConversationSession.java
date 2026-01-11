package com.ruoyi.ai.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 对话会话对象 ai_conversation_session
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public class ConversationSession extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会话ID */
    private Long id;

    /** MongoDB中的memoryId */
    @Excel(name = "MongoDB中的memoryId")
    private String memoryId;

    /** 智能体ID */
    @Excel(name = "智能体ID")
    private Long agentId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 会话标题 */
    @Excel(name = "会话标题")
    private String sessionTitle;

    /** 总消耗token数 */
    @Excel(name = "总消耗token数")
    private Long totalTokens;

    /** 消息数量 */
    @Excel(name = "消息数量")
    private Long messageCount;

    /** 会话开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会话开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 会话结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会话结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }


    public void setMemoryId(String memoryId) 
    {
        this.memoryId = memoryId;
    }

    public String getMemoryId() 
    {
        return memoryId;
    }

    public void setAgentId(Long agentId) 
    {
        this.agentId = agentId;
    }

    public Long getAgentId() 
    {
        return agentId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setSessionTitle(String sessionTitle) 
    {
        this.sessionTitle = sessionTitle;
    }

    public String getSessionTitle() 
    {
        return sessionTitle;
    }

    public void setTotalTokens(Long totalTokens) 
    {
        this.totalTokens = totalTokens;
    }

    public Long getTotalTokens() 
    {
        return totalTokens;
    }

    public void setMessageCount(Long messageCount) 
    {
        this.messageCount = messageCount;
    }

    public Long getMessageCount() 
    {
        return messageCount;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }

    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("memoryId", getMemoryId())
            .append("agentId", getAgentId())
            .append("userId", getUserId())
            .append("sessionTitle", getSessionTitle())
            .append("totalTokens", getTotalTokens())
            .append("messageCount", getMessageCount())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .toString();
    }
}
