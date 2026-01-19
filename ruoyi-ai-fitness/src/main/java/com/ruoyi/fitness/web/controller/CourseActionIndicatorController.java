package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.CourseActionIndicator;
import com.ruoyi.fitness.service.ICourseActionIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程动作指标动作指标Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/courseActionIndicator")
public class CourseActionIndicatorController extends BaseController
{
    @Autowired
    private ICourseActionIndicatorService courseActionIndicatorService;

    /**
     * 查询课程动作指标动作指标列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionIndicator:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseActionIndicator courseActionIndicator)
    {
        startPage();
        List<CourseActionIndicator> list = courseActionIndicatorService.selectCourseActionIndicatorList(courseActionIndicator);
        return getDataTable(list);
    }

    /**
     * 获取课程动作指标动作指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionIndicator:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseActionIndicatorService.selectCourseActionIndicatorById(id));
    }

    /**
     * 新增课程动作指标动作指标
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionIndicator:add')")
    @Log(title = "课程动作指标动作指标", code = "log.ai.Course", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseActionIndicator courseActionIndicator)
    {
        return toAjax(courseActionIndicatorService.insertCourseActionIndicator(courseActionIndicator));
    }

    /**
     * 修改课程动作指标动作指标
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionIndicator:edit')")
    @Log(title = "课程动作指标动作指标", code = "log.ai.CourseActionIndicator", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseActionIndicator courseActionIndicator)
    {
        return toAjax(courseActionIndicatorService.updateCourseActionIndicator(courseActionIndicator));
    }

    /**
     * 删除课程动作指标动作指标
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionIndicator:remove')")
    @Log(title = "课程动作指标动作指标", code = "log.ai.courseActionIndicator", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(courseActionIndicatorService.deleteCourseByIds(ids));
        return toAjax(courseActionIndicatorService.deleteCourseActionIndicator(ids));
    }
}
