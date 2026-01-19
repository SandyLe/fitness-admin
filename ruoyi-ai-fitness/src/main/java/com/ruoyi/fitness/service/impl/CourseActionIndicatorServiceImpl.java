package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.CourseActionIndicator;
import com.ruoyi.fitness.mapper.CourseActionIndicatorMapper;
import com.ruoyi.fitness.service.ICourseActionIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程动作指标Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class CourseActionIndicatorServiceImpl implements ICourseActionIndicatorService {

    @Autowired
    private CourseActionIndicatorMapper courseActionIndicatorMapper;

    @Override
    public CourseActionIndicator selectCourseActionIndicatorById(Long id) {
        return courseActionIndicatorMapper.selectCourseActionIndicatorById(id);
    }

    @Override
    public List<CourseActionIndicator> selectCourseActionIndicatorList(CourseActionIndicator courseActionIndicator) {
        return courseActionIndicatorMapper.selectCourseActionIndicatorList(courseActionIndicator);
    }

    @Override
    public int insertCourseActionIndicator(CourseActionIndicator courseActionIndicator) {
        return courseActionIndicatorMapper.insertCourseActionIndicator(courseActionIndicator);
    }

    @Override
    public int updateCourseActionIndicator(CourseActionIndicator courseActionIndicator) {
        return courseActionIndicatorMapper.updateCourseActionIndicator(courseActionIndicator);
    }

    @Override
    public int deleteCourseActionIndicatorByIds(Long[] ids) {
        return courseActionIndicatorMapper.deleteCourseActionIndicatorByIds(ids);
    }

    @Override
    public int deleteCourseActionIndicatorById(Long id) {
        return courseActionIndicatorMapper.deleteCourseActionIndicatorById(id);
    }

    @Override
    public int deleteCourseActionIndicator(Long[] ids) {
        return courseActionIndicatorMapper.deleteCourseActionIndicatorByIds(ids);
    }
}
