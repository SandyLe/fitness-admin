package com.ruoyi.ai.service;

import java.util.List;
import com.ruoyi.ai.domain.CalendarEvent;

/**
 * 日历事件Service接口
 *
 * @author ruoyi
 * @date 2025-12-12
 */
public interface ICalendarEventService
{
    /**
     * 查询日历事件
     *
     * @param eventId 日历事件主键
     * @return 日历事件
     */
    public CalendarEvent selectCalendarEventByEventId(Long eventId);

    /**
     * 查询日历事件列表
     *
     * @param calendarEvent 日历事件
     * @return 日历事件集合
     */
    public List<CalendarEvent> selectCalendarEventList(CalendarEvent calendarEvent);

    /**
     * 新增日历事件
     *
     * @param calendarEvent 日历事件
     * @return 结果
     */
    public int insertCalendarEvent(CalendarEvent calendarEvent);

    /**
     * 修改日历事件
     *
     * @param calendarEvent 日历事件
     * @return 结果
     */
    public int updateCalendarEvent(CalendarEvent calendarEvent);

    /**
     * 批量删除日历事件
     *
     * @param eventIds 需要删除的日历事件主键集合
     * @return 结果
     */
    public int deleteCalendarEventByEventIds(Long[] eventIds);

    /**
     * 删除日历事件信息
     *
     * @param eventId 日历事件主键
     * @return 结果
     */
    public int deleteCalendarEventByEventId(Long eventId);
}
