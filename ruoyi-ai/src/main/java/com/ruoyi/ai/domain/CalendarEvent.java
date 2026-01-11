package com.ruoyi.ai.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 日历事件对象 ai_calendar_event
 *
 * @author ruoyi
 * @date 2025-12-12
 */
public class CalendarEvent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 事件ID */
    private Long eventId;

    /** 事件标题 */
    @Excel(name = "事件标题")
    private String title;

    /** 提醒时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "提醒时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date reminderTime;

    /** 预约描述 */
    @Excel(name = "预约描述")
    private String description;

    /** 是否提醒（0=否，1=是） */
    @Excel(name = "是否提醒", readConverterExp = "0==否，1=是")
    private String isReminder;

    /** 状态（0=正常，1=已完成，2=已取消） */
    @Excel(name = "状态", readConverterExp = "0==正常，1=已完成，2=已取消")
    private String status;

    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    public Long getEventId()
    {
        return eventId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setReminderTime(Date reminderTime)
    {
        this.reminderTime = reminderTime;
    }

    public Date getReminderTime()
    {
        return reminderTime;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setIsReminder(String isReminder)
    {
        this.isReminder = isReminder;
    }

    public String getIsReminder()
    {
        return isReminder;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("eventId", getEventId())
                .append("title", getTitle())
                .append("reminderTime", getReminderTime())
                .append("description", getDescription())
                .append("isReminder", getIsReminder())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
