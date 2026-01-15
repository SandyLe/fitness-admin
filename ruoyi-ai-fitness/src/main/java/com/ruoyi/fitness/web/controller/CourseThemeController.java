package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.CourseTheme;
import com.ruoyi.fitness.service.ICourseThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/courseTheme")
public class CourseThemeController extends BaseController
{
    @Autowired
    private ICourseThemeService icourseThemeservice;

    /**
     * 查询课程列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseTheme:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseTheme courseTheme)
    {
        startPage();
        List<CourseTheme> list = icourseThemeservice.selectCourseThemeList(courseTheme);
        return getDataTable(list);
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:courseTheme:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(icourseThemeservice.selectCourseThemeById(id));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('ai:courseTheme:add')")
    @Log(title = "课程", code = "log.ai.courseTheme", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseTheme courseTheme)
    {
        return toAjax(icourseThemeservice.insertCourseTheme(courseTheme));
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('ai:courseTheme:edit')")
    @Log(title = "课程", code = "log.ai.courseTheme", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseTheme courseTheme)
    {
        return toAjax(icourseThemeservice.updateCourseTheme(courseTheme));
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('ai:courseTheme:remove')")
    @Log(title = "课程", code = "log.ai.courseTheme", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(icourseThemeService.deleteCourseThemeByIds(ids));
        return toAjax(icourseThemeservice.deleteCourseTheme(ids));
    }
}
