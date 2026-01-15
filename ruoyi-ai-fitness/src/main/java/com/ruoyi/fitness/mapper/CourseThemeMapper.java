package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.CourseTheme;

import java.util.List;

public interface CourseThemeMapper {

    /**
     * 查询课程主题
     *
     * @param id 课程主题主键
     * @return 课程主题
     */
    public CourseTheme selectCourseThemeById(Long id);

    /**
     * 查询课程主题列表
     *
     * @param CourseTheme 课程主题
     * @return 课程主题集合
     */
    public List<CourseTheme> selectCourseThemeList(CourseTheme CourseTheme);

    /**
     * 新增课程主题
     *
     * @param CourseTheme 课程主题
     * @return 结果
     */
    public int insertCourseTheme(CourseTheme CourseTheme);

    /**
     * 修改课程主题
     *
     * @param CourseTheme 课程主题
     * @return 结果
     */
    public int updateCourseTheme(CourseTheme CourseTheme);

    /**
     * 删除课程主题
     *
     * @param id 课程主题主键
     * @return 结果
     */
    public int deleteCourseThemeById(Long id);

    /**
     * 批量删除课程主题
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseThemeByIds(Long[] ids);
}
