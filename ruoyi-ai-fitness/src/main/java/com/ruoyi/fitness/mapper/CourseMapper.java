package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.Course;

import java.util.List;

public interface CourseMapper {

    /**
     * 查询课程
     *
     * @param id 课程主键
     * @return 课程
     */
    public Course selectCourseById(Long id);

    /**
     * 查询课程列表
     *
     * @param Course 课程
     * @return 课程集合
     */
    public List<Course> selectCourseList(Course Course);

    /**
     * 新增课程
     *
     * @param Course 课程
     * @return 结果
     */
    public int insertCourse(Course Course);

    /**
     * 修改课程
     *
     * @param Course 课程
     * @return 结果
     */
    public int updateCourse(Course Course);

    /**
     * 删除课程
     *
     * @param id 课程主键
     * @return 结果
     */
    public int deleteCourseById(Long id);

    /**
     * 批量删除课程
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseByIds(Long[] ids);
}
