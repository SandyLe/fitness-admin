package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.CourseActionComment;
import com.ruoyi.fitness.mapper.CourseActionCommentMapper;
import com.ruoyi.fitness.service.ICourseActionCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程动作指标Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class CourseActionCommentServiceImpl implements ICourseActionCommentService {

    @Autowired
    private CourseActionCommentMapper courseActionCommentMapper;

    @Override
    public CourseActionComment selectCourseActionCommentById(Long id) {
        return courseActionCommentMapper.selectCourseActionCommentById(id);
    }

    @Override
    public List<CourseActionComment> selectCourseActionCommentList(CourseActionComment courseActionComment) {
        return courseActionCommentMapper.selectCourseActionCommentList(courseActionComment);
    }

    @Override
    public List<CourseActionComment> selectCourseIndicatorCommentList(CourseActionComment courseActionComment) {
        return courseActionCommentMapper.selectCourseIndicatorCommentList(courseActionComment);
    }
    @Override
    public int insertCourseActionComment(CourseActionComment courseActionComment) {
        return courseActionCommentMapper.insertCourseActionComment(courseActionComment);
    }

    @Override
    public int updateCourseActionComment(CourseActionComment courseActionComment) {
        return courseActionCommentMapper.updateCourseActionComment(courseActionComment);
    }

    @Override
    public int deleteCourseActionCommentByIds(Long[] ids) {
        return courseActionCommentMapper.deleteCourseActionCommentByIds(ids);
    }

    @Override
    public int deleteCourseActionCommentById(Long id) {
        return courseActionCommentMapper.deleteCourseActionCommentById(id);
    }

    @Override
    public int deleteCourseActionComment(Long[] ids) {
        return courseActionCommentMapper.deleteCourseActionCommentByIds(ids);
    }
}
