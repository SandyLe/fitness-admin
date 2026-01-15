package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.CourseTheme;

import java.util.List;

public interface ICourseThemeService {
    

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
     * 批量删除课程主题
     *
     * @param ids 需要删除的课程主题主键集合
     * @return 结果
     */
    public int deleteCourseThemeByIds(Long[] ids);

    /**
     * 删除课程主题信息
     *
     * @param id 课程主题主键
     * @return 结果
     */
    public int deleteCourseThemeById(Long id);

    /**
     * 删除后，课程主题下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteCourseTheme(Long[] ids);
}
