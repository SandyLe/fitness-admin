package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.Course;
import com.ruoyi.fitness.mapper.CourseMapper;
import com.ruoyi.fitness.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Course selectCourseById(Long id) {
        return courseMapper.selectCourseById(id);
    }

    @Override
    public List<Course> selectCourseList(Course course) {
        return courseMapper.selectCourseList(course);
    }

    @Override
    public int insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public int deleteCourseByIds(Long[] ids) {
        return courseMapper.deleteCourseByIds(ids);
    }

    @Override
    public int deleteCourseById(Long id) {
        return courseMapper.deleteCourseById(id);
    }

    @Override
    public int deleteCourse(Long[] ids) {
        return courseMapper.deleteCourseByIds(ids);
    }
}
