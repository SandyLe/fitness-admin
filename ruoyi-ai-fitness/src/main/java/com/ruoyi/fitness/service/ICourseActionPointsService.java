package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.CourseActionPoints;

import java.util.List;

public interface ICourseActionPointsService {
    

    /**
     * 查询课程动作要点
     *
     * @param id 课程动作要点主键
     * @return 课程动作要点
     */
    public CourseActionPoints selectCourseActionPointsById(Long id);

    /**
     * 查询课程动作要点列表
     *
     * @param courseActionPoints 课程动作要点
     * @return 课程动作要点集合
     */
    public List<CourseActionPoints> selectCourseActionPointsList(CourseActionPoints courseActionPoints);

    /**
     * 新增课程动作要点
     *
     * @param courseActionPoints 课程动作要点
     * @return 结果
     */
    public int insertCourseActionPoints(CourseActionPoints courseActionPoints);

    /**
     * 修改课程动作要点
     *
     * @param courseActionPoints 课程动作要点
     * @return 结果
     */
    public int updateCourseActionPoints(CourseActionPoints courseActionPoints);

    /**
     * 批量删除课程动作要点
     *
     * @param ids 需要删除的课程动作要点主键集合
     * @return 结果
     */
    public int deleteCourseActionPointsByIds(Long[] ids);

    /**
     * 删除课程动作要点信息
     *
     * @param id 课程动作要点主键
     * @return 结果
     */
    public int deleteCourseActionPointsById(Long id);

    /**
     * 删除后，课程动作要点下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteCourseActionPoints(Long[] ids);
}
