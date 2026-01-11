package com.ruoyi.ai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户知识库配额对象 ai_knowledge_quota
 * 
 * @author ruoyi
 * @date 2025-12-11
 */
public class KnowledgeQuota extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配额ID */
    private Long quotaId;

    /** 用户ID（关联 sys_user.user_id） */
    @Excel(name = "用户ID", readConverterExp = "关=联,s=ys_user.user_id")
    private Long userId;

    /** 最大存储限额（字节），默认200M */
    @Excel(name = "最大存储限额", readConverterExp = "字=节")
    private Long maxStorageBytes;

    /** 最多可创建知识库数量，默认10个 */
    @Excel(name = "最多可创建知识库数量，默认10个")
    private Long maxKbCount;

    /** 已用存储（字节） */
    @Excel(name = "已用存储", readConverterExp = "字=节")
    private Long usedStorageBytes;

    /** 已创建知识库数量 */
    @Excel(name = "已创建知识库数量")
    private Long usedKbCount;

    public void setQuotaId(Long quotaId) 
    {
        this.quotaId = quotaId;
    }

    public Long getQuotaId() 
    {
        return quotaId;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setMaxStorageBytes(Long maxStorageBytes) 
    {
        this.maxStorageBytes = maxStorageBytes;
    }

    public Long getMaxStorageBytes() 
    {
        return maxStorageBytes;
    }

    public void setMaxKbCount(Long maxKbCount) 
    {
        this.maxKbCount = maxKbCount;
    }

    public Long getMaxKbCount() 
    {
        return maxKbCount;
    }

    public void setUsedStorageBytes(Long usedStorageBytes) 
    {
        this.usedStorageBytes = usedStorageBytes;
    }

    public Long getUsedStorageBytes() 
    {
        return usedStorageBytes;
    }

    public void setUsedKbCount(Long usedKbCount) 
    {
        this.usedKbCount = usedKbCount;
    }

    public Long getUsedKbCount() 
    {
        return usedKbCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("quotaId", getQuotaId())
            .append("userId", getUserId())
            .append("maxStorageBytes", getMaxStorageBytes())
            .append("maxKbCount", getMaxKbCount())
            .append("usedStorageBytes", getUsedStorageBytes())
            .append("usedKbCount", getUsedKbCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
