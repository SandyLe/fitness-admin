package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.CourseActionComment;

import java.util.List;

public interface ICourseActionCommentService {
    

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
     * 批量删除课程动作评价
     *
     * @param ids 需要删除的课程动作评价主键集合
     * @return 结果
     */
    public int deleteCourseActionCommentByIds(Long[] ids);

    /**
     * 删除课程动作评价信息
     *
     * @param id 课程动作评价主键
     * @return 结果
     */
    public int deleteCourseActionCommentById(Long id);

    /**
     * 删除后，课程动作评价下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteCourseActionComment(Long[] ids);
}
