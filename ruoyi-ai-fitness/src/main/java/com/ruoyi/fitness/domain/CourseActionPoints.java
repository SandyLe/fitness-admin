package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程动作要点
 * @author lixt
 * @created
 */
public class CourseActionPoints {
    private static final long serialVersionUID = 1L;

    /** 课程主题ID */
    private Long actionPointsId;
    /** 课程主题名称 */
    @Excel(name = "课程主题名称")
    private Long courseId;
    /** 动作要点 */
    @Excel(name = "动作要点")
    private String actionPoints;

    public Long getActionPointsId() {
        return actionPointsId;
    }

    public void setActionPointsId(Long actionPointsId) {
        this.actionPointsId = actionPointsId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(String actionPoints) {
        this.actionPoints = actionPoints;
    }
}
