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
    private Long lessOrMore;
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
    /** 指标 */
    @Excel(name = "指标")
    private Long indicatorAtandardValue;
    /** 开始值 */
    @Excel(name = "开始值")
    private Long startValue;
    /** 结束值 */
    @Excel(name = "结束值")
    private Long endValue;
    /** 动作指标编码 */
    @Excel(name = "动作指标编码")
    private String actionPointsCode;

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

    public Long getLessOrMore() {
        return lessOrMore;
    }

    public void setLessOrMore(Long lessOrMore) {
        this.lessOrMore = lessOrMore;
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

    public Long getIndicatorAtandardValue() {
        return indicatorAtandardValue;
    }

    public void setIndicatorAtandardValue(Long indicatorAtandardValue) {
        this.indicatorAtandardValue = indicatorAtandardValue;
    }

    public Long getStartValue() {
        return startValue;
    }

    public void setStartValue(Long startValue) {
        this.startValue = startValue;
    }

    public Long getEndValue() {
        return endValue;
    }

    public void setEndValue(Long endValue) {
        this.endValue = endValue;
    }

    public String getActionPointsCode() {
        return actionPointsCode;
    }

    public void setActionPointsCode(String actionPointsCode) {
        this.actionPointsCode = actionPointsCode;
    }
}
