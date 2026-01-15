package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.CourseTheme;
import com.ruoyi.fitness.mapper.CourseThemeMapper;
import com.ruoyi.fitness.service.ICourseThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class CourseThemeServiceImpl implements ICourseThemeService {

    @Autowired
    private CourseThemeMapper courseThemeMapper;

    @Override
    public CourseTheme selectCourseThemeById(Long id) {
        return courseThemeMapper.selectCourseThemeById(id);
    }

    @Override
    public List<CourseTheme> selectCourseThemeList(CourseTheme CourseTheme) {
        return courseThemeMapper.selectCourseThemeList(CourseTheme);
    }

    @Override
    public int insertCourseTheme(CourseTheme CourseTheme) {
        return courseThemeMapper.insertCourseTheme(CourseTheme);
    }

    @Override
    public int updateCourseTheme(CourseTheme CourseTheme) {
        return courseThemeMapper.updateCourseTheme(CourseTheme);
    }

    @Override
    public int deleteCourseThemeByIds(Long[] ids) {
        return courseThemeMapper.deleteCourseThemeByIds(ids);
    }

    @Override
    public int deleteCourseThemeById(Long id) {
        return courseThemeMapper.deleteCourseThemeById(id);
    }

    @Override
    public int deleteCourseTheme(Long[] ids) {
        return courseThemeMapper.deleteCourseThemeByIds(ids);
    }
}
