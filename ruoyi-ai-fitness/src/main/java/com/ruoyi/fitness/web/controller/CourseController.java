package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.Course;
import com.ruoyi.fitness.service.ICourseService;
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
    public TableDataInfo list(Course Course)
    {
        startPage();
        List<Course> list = icourseservice.selectCourseList(Course);
        return getDataTable(list);
    }

    /**
     * 获取课程详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:Course:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(icourseservice.selectCourseById(id));
    }

    /**
     * 新增课程
     */
    @PreAuthorize("@ss.hasPermi('ai:Course:add')")
    @Log(title = "课程", code = "log.ai.Course", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Course Course)
    {
        return toAjax(icourseservice.insertCourse(Course));
    }

    /**
     * 修改课程
     */
    @PreAuthorize("@ss.hasPermi('ai:Course:edit')")
    @Log(title = "课程", code = "log.ai.Course", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Course Course)
    {
        return toAjax(icourseservice.updateCourse(Course));
    }

    /**
     * 删除课程
     */
    @PreAuthorize("@ss.hasPermi('ai:Course:remove')")
    @Log(title = "课程", code = "log.ai.Course", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(iCourseService.deleteCourseByIds(ids));
        return toAjax(icourseservice.deleteCourse(ids));
    }
}
