package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 课程
 * @author lixt
 * @created
 */
public class Course extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 课程ID */
    private Long courseId;

    /** 主题ID */
    private Long themeId;

    /** 课程名称 */
    @Excel(name = "课程名称")
    private String name;

    /** 课程概述 */
    @Excel(name = "课程概述")
    private String brifeIntroduction;

    /** 视频地址 */
    @Excel(name = "视频地址")
    private String videoUrl;
    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgUrl;
    /** 课程详细 */
    @Excel(name = "课程详细")
    private String courseDesc;
    /** 时长 */
    @Excel(name = "时长")
    private Long duration;
    /** 首页展示 */
    @Excel(name = "首页展示")
    private Long isShowIndex;
    /** 等级 */
    @Excel(name = "等级")
    private String level;
    /** 编码 */
    @Excel(name = "编码")
    private String code;
    /** 课程动作*/
    @Excel(name = "课程动作")
    private String courseAction;
    /** 课程动作动作 */
    @Excel(name = "课程动作组数")
    private Integer groupsCount;
    /** 每组个数 */
    @Excel(name = "每组个数")
    private Integer actionsCount;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrifeIntroduction() {
        return brifeIntroduction;
    }

    public void setBrifeIntroduction(String brifeIntroduction) {
        this.brifeIntroduction = brifeIntroduction;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getIsShowIndex() {
        return isShowIndex;
    }

    public void setIsShowIndex(Long isShowIndex) {
        this.isShowIndex = isShowIndex;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseAction() {
        return courseAction;
    }

    public void setCourseAction(String courseAction) {
        this.courseAction = courseAction;
    }

    public Integer getGroupsCount() {
        return groupsCount;
    }

    public void setGroupsCount(Integer groupsCount) {
        this.groupsCount = groupsCount;
    }

    public Integer getActionsCount() {
        return actionsCount;
    }

    public void setActionsCount(Integer actionsCount) {
        this.actionsCount = actionsCount;
    }
}
