package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.CourseActionIndicator;

import java.util.List;

public interface ICourseActionIndicatorService {
    

    /**
     * 查询课程动作指标
     *
     * @param id 课程动作指标主键
     * @return 课程动作指标
     */
    public CourseActionIndicator selectCourseActionIndicatorById(Long id);

    /**
     * 查询课程动作指标列表
     *
     * @param courseActionIndicator 课程动作指标
     * @return 课程动作指标集合
     */
    public List<CourseActionIndicator> selectCourseActionIndicatorList(CourseActionIndicator courseActionIndicator);

    /**
     * 新增课程动作指标
     *
     * @param courseActionIndicator 课程动作指标
     * @return 结果
     */
    public int insertCourseActionIndicator(CourseActionIndicator courseActionIndicator);

    /**
     * 修改课程动作指标
     *
     * @param courseActionIndicator 课程动作指标
     * @return 结果
     */
    public int updateCourseActionIndicator(CourseActionIndicator courseActionIndicator);

    /**
     * 批量删除课程动作指标
     *
     * @param ids 需要删除的课程动作指标主键集合
     * @return 结果
     */
    public int deleteCourseActionIndicatorByIds(Long[] ids);

    /**
     * 删除课程动作指标信息
     *
     * @param id 课程动作指标主键
     * @return 结果
     */
    public int deleteCourseActionIndicatorById(Long id);

    /**
     * 删除后，课程动作指标下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteCourseActionIndicator(Long[] ids);
}
