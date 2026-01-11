package com.ruoyi.ai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 知识库对象 ai_knowledge_base
 *
 * @author ruoyi
 * @date 2025-12-01
 */
public class KnowledgeBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 知识库ID */
    private Long id;

    /** 知识库名称 */
    @Excel(name = "知识库名称")
    private String kbName;

    /** 知识库描述 */
    @Excel(name = "知识库描述")
    private String kbDescription;

    /** 所属用户ID */
    @Excel(name = "所属用户ID")
    private Long userId;

    /** 文档数量 */
    @Excel(name = "文档数量")
    private Long fileCount;

    /** 状态(0:禁用 1:启用) */
    @Excel(name = "状态(0:禁用 1:启用)")
    private Long status;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setKbName(String kbName)
    {
        this.kbName = kbName;
    }

    public String getKbName()
    {
        return kbName;
    }

    public void setKbDescription(String kbDescription)
    {
        this.kbDescription = kbDescription;
    }

    public String getKbDescription()
    {
        return kbDescription;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setFileCount(Long fileCount)
    {
        this.fileCount = fileCount;
    }

    public Long getFileCount()
    {
        return fileCount;
    }

    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("kbName", getKbName())
                .append("kbDescription", getKbDescription())
                .append("userId", getUserId())
                .append("fileCount", getFileCount())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("remark", getRemark())
                .toString();
    }
}
