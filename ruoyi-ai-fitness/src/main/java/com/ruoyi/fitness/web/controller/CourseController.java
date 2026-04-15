package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.fitness.domain.Course;
import com.ruoyi.fitness.service.ICourseService;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/fitness/course")
public class CourseController extends BaseController
{
    @Autowired
    private ICourseService icourseservice;

    /**
     * 查询课程列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:course:list')")
    @GetMapping("/list")
    public TableDataInfo list(Course course)
    {
        startPage();
        List<Course> list = icourseservice.selectCourseList(course);
        return getDataTable(list);
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:course:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(icourseservice.selectCourseById(id));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('fitness:course:add')")
    @Log(title = "课程", code = "log.ai.course", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course course)
    {
        return toAjax(icourseservice.insertCourse(course));
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('fitness:course:edit')")
    @Log(title = "课程", code = "log.ai.course", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course course)
    {
        return toAjax(icourseservice.updateCourse(course));
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('fitness:course:remove')")
    @Log(title = "课程", code = "log.ai.Course", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(iCourseService.deleteCourseByIds(ids));
        return toAjax(icourseservice.deleteCourse(ids));
    }

    @Log(title = "课程", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('fitness:course:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Course course)
    {
        List<Course> list = icourseservice.selectCourseList(course);
        ExcelUtil<Course> util = new ExcelUtil<Course>(Course.class);
        util.exportExcel(response, list, "课程");
    }
}
