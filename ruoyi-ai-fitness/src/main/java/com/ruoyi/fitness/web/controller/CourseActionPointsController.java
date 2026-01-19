package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.CourseActionPoints;
import com.ruoyi.fitness.service.ICourseActionPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程动作要点动作要点Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/courseActionPoints")
public class CourseActionPointsController extends BaseController
{
    @Autowired
    private ICourseActionPointsService courseActionPointsService;

    /**
     * 查询课程动作要点动作要点列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionPoints:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseActionPoints courseActionPoints)
    {
        startPage();
        List<CourseActionPoints> list = courseActionPointsService.selectCourseActionPointsList(courseActionPoints);
        return getDataTable(list);
    }

    /**
     * 获取课程动作要点动作要点详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionPoints:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseActionPointsService.selectCourseActionPointsById(id));
    }

    /**
     * 新增课程动作要点动作要点
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionPoints:add')")
    @Log(title = "课程动作要点动作要点", code = "log.ai.Course", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseActionPoints courseActionPoints)
    {
        return toAjax(courseActionPointsService.insertCourseActionPoints(courseActionPoints));
    }

    /**
     * 修改课程动作要点动作要点
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionPoints:edit')")
    @Log(title = "课程动作要点动作要点", code = "log.ai.CourseActionPoints", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseActionPoints courseActionPoints)
    {
        return toAjax(courseActionPointsService.updateCourseActionPoints(courseActionPoints));
    }

    /**
     * 删除课程动作要点动作要点
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionPoints:remove')")
    @Log(title = "课程动作要点动作要点", code = "log.ai.courseActionPoints", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(courseActionPointsService.deleteCourseByIds(ids));
        return toAjax(courseActionPointsService.deleteCourseActionPoints(ids));
    }
}
