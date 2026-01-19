package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.CourseActionPoints;

import java.util.List;

public interface CourseActionPointsMapper {

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
     * 删除课程动作要点
     *
     * @param id 课程动作要点主键
     * @return 结果
     */
    public int deleteCourseActionPointsById(Long id);

    /**
     * 批量删除课程动作要点
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseActionPointsByIds(Long[] ids);
}
