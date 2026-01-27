package com.ruoyi.fitness.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;

import java.util.Date;

/**
 * 课程训练记录
 * @author lixt
 * @created 2026年1月24日12:29:57
 */
public class TrainingRecordData {
    private static final long serialVersionUID = 1L;

    @Excel(name = "课程主键")
    private Long courseId;
    @Excel(name = "课程主题名称")
    private Long userId;
    @Excel(name = "课程主键")
    private String course;
    @Excel(name = "课程主题名称")
    private String user;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @Excel(name = "批次号")
    private String batchNo;
    @Excel(name = "得分")
    private Integer source;
    @Excel(name = "整体分析")
    private String analysis;
    @Excel(name = "建议")
    private String advice;

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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
