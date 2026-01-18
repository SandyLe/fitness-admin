package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程主题
 * @author lixt
 * @created
 */
public class CourseActionComment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 课程主题ID */
    private Long id;

    /** 课程主题名称 */
    @Excel(name = "课程主题名称")
    private String themeName;
    /** 编码 */
    @Excel(name = "编码")
    private String themeCode;
    /** 适用病症 */
    @Excel(name = "适用病症")
    private String indications;
    /** 康复目标 */
    @Excel(name = "康复目标")
    private String rehabilitationGoal;
    /** 训练运动频率 */
    @Excel(name = "训练运动频率")
    private String trainingFrequency;
    /** 训练运动 */
    @Excel(name = "训练运动")
    private String trainingAction;
    /** 状态 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeCode() {
        return themeCode;
    }

    public void setThemeCode(String themeCode) {
        this.themeCode = themeCode;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getRehabilitationGoal() {
        return rehabilitationGoal;
    }

    public void setRehabilitationGoal(String rehabilitationGoal) {
        this.rehabilitationGoal = rehabilitationGoal;
    }

    public String getTrainingFrequency() {
        return trainingFrequency;
    }

    public void setTrainingFrequency(String trainingFrequency) {
        this.trainingFrequency = trainingFrequency;
    }

    public String getTrainingAction() {
        return trainingAction;
    }

    public void setTrainingAction(String trainingAction) {
        this.trainingAction = trainingAction;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
