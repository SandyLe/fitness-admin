package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.CourseActionComment;

import java.util.List;

public interface CourseActionCommentMapper {

    /**
     * 查询课程动作评价
     *
     * @param id 课程动作评价主键
     * @return 课程动作评价
     */
    public CourseActionComment selectCourseActionCommentById(Long id);

    /**
     * 查询课程动作评价列表
     *
     * @param courseActionComment 课程动作评价
     * @return 课程动作评价集合
     */
    public List<CourseActionComment> selectCourseActionCommentList(CourseActionComment courseActionComment);

    /**
     * 新增课程动作评价
     *
     * @param courseActionComment 课程动作评价
     * @return 结果
     */
    public int insertCourseActionComment(CourseActionComment courseActionComment);

    /**
     * 修改课程动作评价
     *
     * @param courseActionComment 课程动作评价
     * @return 结果
     */
    public int updateCourseActionComment(CourseActionComment courseActionComment);

    /**
     * 删除课程动作评价
     *
     * @param id 课程动作评价主键
     * @return 结果
     */
    public int deleteCourseActionCommentById(Long id);

    /**
     * 批量删除课程动作评价
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseActionCommentByIds(Long[] ids);

    /**
     * 查询课程动作评价列表
     *
     * @param courseActionComment 课程动作评价
     * @return 课程动作评价集合
     */
    public List<CourseActionComment> selectCourseIndicatorCommentList(CourseActionComment courseActionComment);
}
