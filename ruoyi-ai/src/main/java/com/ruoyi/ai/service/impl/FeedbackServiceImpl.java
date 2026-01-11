package com.ruoyi.ai.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.FeedbackMapper;
import com.ruoyi.ai.domain.Feedback;
import com.ruoyi.ai.service.IFeedbackService;

/**
 * 问题反馈Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-11
 */
@Service
public class FeedbackServiceImpl implements IFeedbackService 
{
    @Autowired
    private FeedbackMapper feedbackMapper;

    /**
     * 查询问题反馈
     * 
     * @param feedbackId 问题反馈主键
     * @return 问题反馈
     */
    @Override
    public Feedback selectFeedbackByFeedbackId(Long feedbackId)
    {
        return feedbackMapper.selectFeedbackByFeedbackId(feedbackId);
    }

    /**
     * 查询问题反馈列表
     * 
     * @param feedback 问题反馈
     * @return 问题反馈
     */
    @Override
    public List<Feedback> selectFeedbackList(Feedback feedback)
    {
        return feedbackMapper.selectFeedbackList(feedback);
    }

    /**
     * 新增问题反馈
     * 
     * @param feedback 问题反馈
     * @return 结果
     */
    @Override
    public int insertFeedback(Feedback feedback)
    {
        feedback.setCreateTime(DateUtils.getNowDate());
        feedback.setCreateBy(SecurityUtils.getUsername());
        return feedbackMapper.insertFeedback(feedback);
    }

    /**
     * 修改问题反馈
     * 
     * @param feedback 问题反馈
     * @return 结果
     */
    @Override
    public int updateFeedback(Feedback feedback)
    {
        feedback.setUpdateTime(DateUtils.getNowDate());
        feedback.setUpdateBy(SecurityUtils.getUsername());
        return feedbackMapper.updateFeedback(feedback);
    }

    /**
     * 批量删除问题反馈
     * 
     * @param feedbackIds 需要删除的问题反馈主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackByFeedbackIds(Long[] feedbackIds)
    {
        return feedbackMapper.deleteFeedbackByFeedbackIds(feedbackIds);
    }

    /**
     * 删除问题反馈信息
     * 
     * @param feedbackId 问题反馈主键
     * @return 结果
     */
    @Override
    public int deleteFeedbackByFeedbackId(Long feedbackId)
    {
        return feedbackMapper.deleteFeedbackByFeedbackId(feedbackId);
    }
}
