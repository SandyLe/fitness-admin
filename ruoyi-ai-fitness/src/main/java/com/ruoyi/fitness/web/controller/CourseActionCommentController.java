package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.CourseActionComment;
import com.ruoyi.fitness.service.ICourseActionCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程动作评价动作评价Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/courseActionComment")
public class CourseActionCommentController extends BaseController
{
    @Autowired
    private ICourseActionCommentService courseActionCommentService;

    /**
     * 查询课程动作评价动作评价列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionComment:list')")
    @GetMapping("/list")
    public TableDataInfo list(CourseActionComment courseActionComment)
    {
        startPage();
        List<CourseActionComment> list = courseActionCommentService.selectCourseActionCommentList(courseActionComment);
        return getDataTable(list);
    }

    /**
     * 获取课程动作评价动作评价详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionComment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(courseActionCommentService.selectCourseActionCommentById(id));
    }

    /**
     * 新增课程动作评价动作评价
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionComment:add')")
    @Log(title = "课程动作评价动作评价", code = "log.ai.Course", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CourseActionComment courseActionComment)
    {
        return toAjax(courseActionCommentService.insertCourseActionComment(courseActionComment));
    }

    /**
     * 修改课程动作评价动作评价
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionComment:edit')")
    @Log(title = "课程动作评价动作评价", code = "log.ai.CourseActionComment", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CourseActionComment courseActionComment)
    {
        return toAjax(courseActionCommentService.updateCourseActionComment(courseActionComment));
    }

    /**
     * 删除课程动作评价动作评价
     */
    @PreAuthorize("@ss.hasPermi('fitness:courseActionComment:remove')")
    @Log(title = "课程动作评价动作评价", code = "log.ai.courseActionComment", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(courseActionCommentService.deleteCourseByIds(ids));
        return toAjax(courseActionCommentService.deleteCourseActionComment(ids));
    }
}
