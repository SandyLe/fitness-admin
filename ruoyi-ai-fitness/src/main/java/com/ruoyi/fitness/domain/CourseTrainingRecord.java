package com.ruoyi.fitness.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.util.Date;

/**
 * 课程训练记录
 * @author lixt
 * @created 2026年1月24日12:29:57
 */
public class CourseTrainingRecord {
    private static final long serialVersionUID = 1L;

    /** 课程训练记录ID */
    private Long recordId;
    /** 课程主题名称 */
    @Excel(name = "课程主键")
    private Long courseId;
    @Excel(name = "课程主题名称")
    private Long userId;
    @Excel(name = "动作指标编码")
    private String actionPointsCode;
    @Excel(name = "标准值")
    private Double actionPointsValue;
    @Excel(name = "训练组数")
    private Integer groupsNum;
    @Excel(name = "每组次数")
    private Integer actionsNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Excel(name = "批次号")
    private String batchNo;
    @Excel(name = "课程名")
    private String name;
    @Excel(name = "动作指标")
    private String actionPoints;
    @Excel(name = "评论")
    private String actionCommentDesc;
    @Excel(name = "建议")
    private String suggestions;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionPointsCode() {
        return actionPointsCode;
    }

    public void setActionPointsCode(String actionPointsCode) {
        this.actionPointsCode = actionPointsCode;
    }

    public Double getActionPointsValue() {
        return actionPointsValue;
    }

    public void setActionPointsValue(Double actionPointsValue) {
        this.actionPointsValue = actionPointsValue;
    }

    public Integer getGroupsNum() {
        return groupsNum;
    }

    public void setGroupsNum(Integer groupsNum) {
        this.groupsNum = groupsNum;
    }

    public Integer getActionsNum() {
        return actionsNum;
    }

    public void setActionsNum(Integer actionsNum) {
        this.actionsNum = actionsNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(String actionPoints) {
        this.actionPoints = actionPoints;
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
