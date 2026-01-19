package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程动作评价
 * @author lixt
 * @created
 */
public class CourseActionComment {
    private static final long serialVersionUID = 1L;

    /** 课程动作评估ID */
    private Long actionCommentId;

    /** 课程主题名称 */
    @Excel(name = "课程主键")
    private Long courseId;
    /** 编码 */
    @Excel(name = "动作指标")
    private Long indicatorId;
    /** 适用病症 */
    @Excel(name = "大于或小于指标")
    private Long lessOrmore;
    /** 康复目标 */
    @Excel(name = "指标值")
    private String standardValue;
    /** 动作指标评估名称 */
    @Excel(name = "动作指标评估名称")
    private String actionCommentTitle;
    /** 动作指标评价 */
    @Excel(name = "动作指标评价")
    private String actionCommentDesc;
    /** 动作指标建议 */
    @Excel(name = "动作指标建议")
    private String suggestions;

    public Long getActionCommentId() {
        return actionCommentId;
    }

    public void setActionCommentId(Long actionCommentId) {
        this.actionCommentId = actionCommentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Long indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Long getLessOrmore() {
        return lessOrmore;
    }

    public void setLessOrmore(Long lessOrmore) {
        this.lessOrmore = lessOrmore;
    }

    public String getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(String standardValue) {
        this.standardValue = standardValue;
    }

    public String getActionCommentTitle() {
        return actionCommentTitle;
    }

    public void setActionCommentTitle(String actionCommentTitle) {
        this.actionCommentTitle = actionCommentTitle;
    }

    public String getActionCommentDesc() {
        return actionCommentDesc;
    }

    public void setActionCommentDesc(String actionCommentDesc) {
        this.actionCommentDesc = actionCommentDesc;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }
}
