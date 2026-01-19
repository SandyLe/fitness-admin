package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程主题
 * @author lixt
 * @created
 */
public class CourseActionIndicator {
    private static final long serialVersionUID = 1L;

    /** 动作指标ID */
    private Long actionIndicatorId;
    /** 课程ID */
    private Long courseId;

    /** 动作指标名称 */
    @Excel(name = "动作指标名称")
    private String actionPoints;
    /** 编码 */
    @Excel(name = "编码")
    private String actionPointsCode;
    /** 点位1 */
    private Integer point1;
    /** 点位2 */
    private Integer point2;
    /** 点位3 */
    private Integer point3;
    /** 标准值 */
    @Excel(name = "标准值")
    private Double standardValue;
    /** 初始值 */
    private Double startValue;
    /** 结束值 */
    @Excel(name = "结束值")
    private Double endValue;

    public Long getActionIndicatorId() {
        return actionIndicatorId;
    }

    public void setActionIndicatorId(Long actionIndicatorId) {
        this.actionIndicatorId = actionIndicatorId;
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

    public String getActionPointsCode() {
        return actionPointsCode;
    }

    public void setActionPointsCode(String actionPointsCode) {
        this.actionPointsCode = actionPointsCode;
    }

    public Integer getPoint1() {
        return point1;
    }

    public void setPoint1(Integer point1) {
        this.point1 = point1;
    }

    public Integer getPoint2() {
        return point2;
    }

    public void setPoint2(Integer point2) {
        this.point2 = point2;
    }

    public Integer getPoint3() {
        return point3;
    }

    public void setPoint3(Integer point3) {
        this.point3 = point3;
    }

    public Double getStandardValue() {
        return standardValue;
    }

    public void setStandardValue(Double standardValue) {
        this.standardValue = standardValue;
    }

    public Double getStartValue() {
        return startValue;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }
}
