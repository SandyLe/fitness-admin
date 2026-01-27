package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.CourseTrainingRecord;
import com.ruoyi.fitness.domain.TrainingRecordData;
import com.ruoyi.fitness.mapper.CourseTrainingRecordMapper;
import com.ruoyi.fitness.service.ICourseTrainingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程训练记录Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class CourseTrainingRecordServiceImpl implements ICourseTrainingRecordService {

    @Autowired
    private CourseTrainingRecordMapper courseTrainingRecordMapper;

    @Override
    public CourseTrainingRecord selectCourseTrainingRecordById(Long id) {
        return courseTrainingRecordMapper.selectCourseTrainingRecordById(id);
    }

    @Override
    public List<CourseTrainingRecord> selectCourseTrainingRecordList(CourseTrainingRecord courseTrainingRecord) {
        return courseTrainingRecordMapper.selectCourseTrainingRecordList(courseTrainingRecord);
    }

    @Override
    public int insertCourseTrainingRecord(CourseTrainingRecord courseTrainingRecord) {
        return courseTrainingRecordMapper.insertCourseTrainingRecord(courseTrainingRecord);
    }

    @Override
    public int updateCourseTrainingRecord(CourseTrainingRecord courseTrainingRecord) {
        return courseTrainingRecordMapper.updateCourseTrainingRecord(courseTrainingRecord);
    }

    @Override
    public int deleteCourseTrainingRecordByIds(Long[] ids) {
        return courseTrainingRecordMapper.deleteCourseTrainingRecordByIds(ids);
    }

    @Override
    public int deleteCourseTrainingRecordById(Long id) {
        return courseTrainingRecordMapper.deleteCourseTrainingRecordById(id);
    }

    @Override
    public int deleteCourseTrainingRecord(Long[] ids) {
        return courseTrainingRecordMapper.deleteCourseTrainingRecordByIds(ids);
    }

    @Override
    public List<TrainingRecordData> selectCourseTrainingDataList(CourseTrainingRecord courseTrainingRecord) {
        return courseTrainingRecordMapper.selectCourseTrainingDataList(courseTrainingRecord);
    }
}
