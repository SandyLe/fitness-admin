package com.ruoyi.ai.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.CalendarEventMapper;
import com.ruoyi.ai.domain.CalendarEvent;
import com.ruoyi.ai.service.ICalendarEventService;

/**
 * 日历事件Service业务层处理
 *
 * @author ruoyi
 * @date 2025-12-12
 */
@Service
public class CalendarEventServiceImpl implements ICalendarEventService
{
    @Autowired
    private CalendarEventMapper calendarEventMapper;

    /**
     * 查询日历事件
     *
     * @param eventId 日历事件主键
     * @return 日历事件
     */
    @Override
    public CalendarEvent selectCalendarEventByEventId(Long eventId)
    {
        return calendarEventMapper.selectCalendarEventByEventId(eventId);
    }

    /**
     * 查询日历事件列表
     *
     * @param calendarEvent 日历事件
     * @return 日历事件
     */
    @Override
    public List<CalendarEvent> selectCalendarEventList(CalendarEvent calendarEvent)
    {
        return calendarEventMapper.selectCalendarEventList(calendarEvent);
    }

    /**
     * 新增日历事件
     *
     * @param calendarEvent 日历事件
     * @return 结果
     */
    @Override
    public int insertCalendarEvent(CalendarEvent calendarEvent)
    {
        calendarEvent.setCreateTime(DateUtils.getNowDate());
        calendarEvent.setCreateBy(SecurityUtils.getUsername());
        return calendarEventMapper.insertCalendarEvent(calendarEvent);
    }

    /**
     * 修改日历事件
     *
     * @param calendarEvent 日历事件
     * @return 结果
     */
    @Override
    public int updateCalendarEvent(CalendarEvent calendarEvent)
    {
        calendarEvent.setUpdateTime(DateUtils.getNowDate());
        calendarEvent.setUpdateBy(SecurityUtils.getUsername());
        return calendarEventMapper.updateCalendarEvent(calendarEvent);
    }

    /**
     * 批量删除日历事件
     *
     * @param eventIds 需要删除的日历事件主键
     * @return 结果
     */
    @Override
    public int deleteCalendarEventByEventIds(Long[] eventIds)
    {
        return calendarEventMapper.deleteCalendarEventByEventIds(eventIds);
    }

    /**
     * 删除日历事件信息
     *
     * @param eventId 日历事件主键
     * @return 结果
     */
    @Override
    public int deleteCalendarEventByEventId(Long eventId)
    {
        return calendarEventMapper.deleteCalendarEventByEventId(eventId);
    }
}
