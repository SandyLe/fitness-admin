package com.ruoyi.ai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 知识库文档对象 ai_knowledge_document
 *
 * @author ruoyi
 * @date 2025-12-01
 */
public class KnowledgeDocument extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文档ID */
    private Long id;

    /** 知识库ID */
    @Excel(name = "知识库ID")
    private Long kbId;

    /** 文档名称 */
    @Excel(name = "文档名称")
    private String docName;

    /** 文档类型(txt/pdf/docx等) */
    @Excel(name = "文档类型(txt/pdf/docx等)")
    private String docType;

    /** 文件存储路径 */
    @Excel(name = "文件存储路径")
    private String filePath;

    /** 文件大小(字节) */
    @Excel(name = "文件大小(字节)")
    private Long fileSize;

    /** 状态(0:处理中 1:完成 2:失败) */
    @Excel(name = "状态(0:处理中 1:完成 2:失败)")
    private Integer status;

    /** 处理结果信息 */
    @Excel(name = "处理结果信息")
    private String processResult;

    /** 向量数据库的向量ID */
    @Excel(name = "向量数据库的向量ID")
    private String vectorId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setKbId(Long kbId)
    {
        this.kbId = kbId;
    }

    public Long getKbId()
    {
        return kbId;
    }

    public void setDocName(String docName)
    {
        this.docName = docName;
    }

    public String getDocName()
    {
        return docName;
    }

    public void setDocType(String docType)
    {
        this.docType = docType;
    }

    public String getDocType()
    {
        return docType;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
    }

    public Long getFileSize()
    {
        return fileSize;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setProcessResult(String processResult)
    {
        this.processResult = processResult;
    }

    public String getProcessResult()
    {
        return processResult;
    }

    public void setVectorId(String vectorId)
    {
        this.vectorId = vectorId;
    }

    public String getVectorId()
    {
        return vectorId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("kbId", getKbId())
                .append("docName", getDocName())
                .append("docType", getDocType())
                .append("filePath", getFilePath())
                .append("fileSize", getFileSize())
                .append("status", getStatus())
                .append("processResult", getProcessResult())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("vectorId", getVectorId())
                .toString();
    }
}
