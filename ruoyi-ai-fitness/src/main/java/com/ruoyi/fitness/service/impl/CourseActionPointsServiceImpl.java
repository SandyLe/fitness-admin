package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.CourseActionPoints;
import com.ruoyi.fitness.mapper.CourseActionPointsMapper;
import com.ruoyi.fitness.service.ICourseActionPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程动作指标Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class CourseActionPointsServiceImpl implements ICourseActionPointsService {

    @Autowired
    private CourseActionPointsMapper courseActionPointsMapper;

    @Override
    public CourseActionPoints selectCourseActionPointsById(Long id) {
        return courseActionPointsMapper.selectCourseActionPointsById(id);
    }

    @Override
    public List<CourseActionPoints> selectCourseActionPointsList(CourseActionPoints courseActionPoints) {
        return courseActionPointsMapper.selectCourseActionPointsList(courseActionPoints);
    }

    @Override
    public int insertCourseActionPoints(CourseActionPoints courseActionPoints) {
        return courseActionPointsMapper.insertCourseActionPoints(courseActionPoints);
    }

    @Override
    public int updateCourseActionPoints(CourseActionPoints courseActionPoints) {
        return courseActionPointsMapper.updateCourseActionPoints(courseActionPoints);
    }

    @Override
    public int deleteCourseActionPointsByIds(Long[] ids) {
        return courseActionPointsMapper.deleteCourseActionPointsByIds(ids);
    }

    @Override
    public int deleteCourseActionPointsById(Long id) {
        return courseActionPointsMapper.deleteCourseActionPointsById(id);
    }

    @Override
    public int deleteCourseActionPoints(Long[] ids) {
        return courseActionPointsMapper.deleteCourseActionPointsByIds(ids);
    }
}
