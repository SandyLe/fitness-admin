package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 营养指导模板明细
 * @author lixt
 * @created
 */
public class NutritionGuidanceDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    @Excel(name = "模板ID")
    private Long templateId;
    
    /** 类型编码 */
    private String typeCode;

    /** 项目名称 */
    private String itemName;

    /** 项目明细值 */
    @Excel(name = "项目明细值")
    private String itemValue;

    /** 状态 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
