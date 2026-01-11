package com.ruoyi.ai.mapper;

import java.util.List;
import com.ruoyi.ai.domain.CalendarEvent;

/**
 * 日历事件Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-11
 */
public interface CalendarEventMapper 
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
     * 查询当日提醒的日历事件列表
     * @param calendarEvent 日历事件
     * @return
     */
    public List<CalendarEvent> selectCurrentReminderEventsWithCalc(CalendarEvent calendarEvent);

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
     * 删除日历事件
     * 
     * @param eventId 日历事件主键
     * @return 结果
     */
    public int deleteCalendarEventByEventId(Long eventId);

    /**
     * 批量删除日历事件
     * 
     * @param eventIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCalendarEventByEventIds(Long[] eventIds);
}
