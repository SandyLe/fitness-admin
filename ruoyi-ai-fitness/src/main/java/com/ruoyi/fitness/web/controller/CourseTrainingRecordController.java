package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.CourseTrainingRecord;
import com.ruoyi.fitness.service.ICourseTrainingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程训练记录Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/courseTrainingRecord")
public class CourseTrainingRecordController extends BaseController
{
    @Autowired
    private ICourseTrainingRecordService courseTrainingRecordService;

    /**
     * 查询课程训练记录列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseTrainingRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseTrainingRecord courseTrainingRecord)
    {
        startPage();
        List<CourseTrainingRecord> list = courseTrainingRecordService.selectCourseTrainingRecordList(courseTrainingRecord);
        return getDataTable(list);
    }

    /**
     * 获取课程训练记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseTrainingRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseTrainingRecordService.selectCourseTrainingRecordById(id));
    }

    /**
     * 新增课程训练记录
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseTrainingRecord:add')")
    @Log(title = "课程训练记录", code = "log.ai.Course", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseTrainingRecord courseTrainingRecord)
    {
        return toAjax(courseTrainingRecordService.insertCourseTrainingRecord(courseTrainingRecord));
    }

    /**
     * 修改课程训练记录
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseTrainingRecord:edit')")
    @Log(title = "课程训练记录", code = "log.ai.courseTrainingRecord", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseTrainingRecord courseTrainingRecord)
    {
        return toAjax(courseTrainingRecordService.updateCourseTrainingRecord(courseTrainingRecord));
    }

    /**
     * 删除课程训练记录
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseTrainingRecord:remove')")
    @Log(title = "课程训练记录", code = "log.ai.courseTrainingRecord", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(CourseTrainingRecordService.deleteCourseByIds(ids));
        return toAjax(courseTrainingRecordService.deleteCourseTrainingRecord(ids));
    }
}
