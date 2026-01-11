package com.ruoyi.ai.web.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.ai.domain.CalendarEvent;
import com.ruoyi.ai.service.ICalendarEventService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 日历事件Controller
 *
 * @author ruoyi
 * @date 2025-12-12
 */
@RestController
@RequestMapping("/ai/calendarevent")
public class CalendarEventController extends BaseController
{
    @Autowired
    private ICalendarEventService calendarEventService;

    /**
     * 查询日历事件列表
     */
    @PreAuthorize("@ss.hasPermi('ai:calendarevent:list')")
    @GetMapping("/list")
    public TableDataInfo list(CalendarEvent calendarEvent)
    {
        calendarEvent.setCreateBy(getUsername());
        startPage();
        List<CalendarEvent> list = calendarEventService.selectCalendarEventList(calendarEvent);
        return getDataTable(list);
    }

    /**
     * 导出日历事件列表
     */
    @PreAuthorize("@ss.hasPermi('ai:calendarevent:export')")
    @Log(title = "日历事件", code = "log.ai.calendarEvent", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CalendarEvent calendarEvent)
    {
        List<CalendarEvent> list = calendarEventService.selectCalendarEventList(calendarEvent);
        ExcelUtil<CalendarEvent> util = new ExcelUtil<CalendarEvent>(CalendarEvent.class);
        util.exportExcel(response, list, "日历事件数据");
    }

    /**
     * 获取日历事件详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:calendarevent:query')")
    @GetMapping(value = "/{eventId}")
    public AjaxResult getInfo(@PathVariable("eventId") Long eventId)
    {
        return success(calendarEventService.selectCalendarEventByEventId(eventId));
    }

    /**
     * 新增日历事件
     */
    @PreAuthorize("@ss.hasPermi('ai:calendarevent:add')")
    @Log(title = "日历事件", code = "log.ai.calendarEvent", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CalendarEvent calendarEvent)
    {
        return toAjax(calendarEventService.insertCalendarEvent(calendarEvent));
    }

    /**
     * 修改日历事件
     */
    @PreAuthorize("@ss.hasPermi('ai:calendarevent:edit')")
    @Log(title = "日历事件", code = "log.ai.calendarEvent", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CalendarEvent calendarEvent)
    {
        return toAjax(calendarEventService.updateCalendarEvent(calendarEvent));
    }

    /**
     * 删除日历事件
     */
    @PreAuthorize("@ss.hasPermi('ai:calendarevent:remove')")
    @Log(title = "日历事件", code = "log.ai.calendarEvent", businessType = BusinessType.DELETE)
    @DeleteMapping("/{eventIds}")
    public AjaxResult remove(@PathVariable Long[] eventIds)
    {
        return toAjax(calendarEventService.deleteCalendarEventByEventIds(eventIds));
    }
}
