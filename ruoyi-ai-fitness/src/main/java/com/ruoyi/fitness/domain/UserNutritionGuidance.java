package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户营养指导
 * @author lixt
 * @created
 */
public class UserNutritionGuidance extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Excel(name = "id")
    private Long id;

    /** 营养指导名称 */
    private String guidanceName;

    /** 编码ID */
    private String templateCode;

    /** 营养指导描述 */
    @Excel(name = "营养指导描述")
    private String templateDesc;

    /** 主题ID */
    @Excel(name = "主题ID")
    private Long themeId;

    /** 状态 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getGuidanceName() {
        return guidanceName;
    }

    public void setGuidanceName(String guidanceName) {
        this.guidanceName = guidanceName;
    }
}
