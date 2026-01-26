package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.CourseTrainingRecord;

import java.util.List;

public interface CourseTrainingRecordMapper {

    /**
     * 查询课程训练记录
     *
     * @param id 课程训练记录主键
     * @return 课程训练记录
     */
    public CourseTrainingRecord selectCourseTrainingRecordById(Long id);

    /**
     * 查询课程训练记录列表
     *
     * @param courseTrainingRecord 课程训练记录
     * @return 课程训练记录集合
     */
    public List<CourseTrainingRecord> selectCourseTrainingRecordList(CourseTrainingRecord courseTrainingRecord);

    /**
     * 新增课程训练记录
     *
     * @param courseTrainingRecord 课程训练记录
     * @return 结果
     */
    public int insertCourseTrainingRecord(CourseTrainingRecord courseTrainingRecord);

    /**
     * 修改课程训练记录
     *
     * @param courseTrainingRecord 课程训练记录
     * @return 结果
     */
    public int updateCourseTrainingRecord(CourseTrainingRecord courseTrainingRecord);

    /**
     * 删除课程训练记录
     *
     * @param id 课程训练记录主键
     * @return 结果
     */
    public int deleteCourseTrainingRecordById(Long id);

    /**
     * 批量删除课程训练记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseTrainingRecordByIds(Long[] ids);
}
