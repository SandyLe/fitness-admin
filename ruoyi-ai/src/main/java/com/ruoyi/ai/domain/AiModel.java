package com.ruoyi.ai.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * AI模型配置对象 ai_model
 *
 * @author ruoyi
 * @date 2025-12-01
 */
public class AiModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模型ID */
    private Long id;

    /** 模型名称 */
    @Excel(name = "模型名称")
    private String modelName;

    /** 模型类型(OPENAI/CHATGLM/ERNIE/QWEN等) */
    @Excel(name = "模型类型(OPENAI/CHATGLM/ERNIE/QWEN等)")
    private String modelType;

    /** 模型配置参数(JSON格式) */
    @Excel(name = "模型配置参数(JSON格式)")
    private String modelConfig;

    /** API密钥 */
    @Excel(name = "API密钥")
    private String apiKey;

    /** API基础URL */
    @Excel(name = "API基础URL")
    private String baseUrl;

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

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public String getModelName()
    {
        return modelName;
    }

    public void setModelType(String modelType)
    {
        this.modelType = modelType;
    }

    public String getModelType()
    {
        return modelType;
    }

    public void setModelConfig(String modelConfig)
    {
        this.modelConfig = modelConfig;
    }

    public String getModelConfig()
    {
        return modelConfig;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl()
    {
        return baseUrl;
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
                .append("modelName", getModelName())
                .append("modelType", getModelType())
                .append("modelConfig", getModelConfig())
                .append("apiKey", getApiKey())
                .append("baseUrl", getBaseUrl())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
