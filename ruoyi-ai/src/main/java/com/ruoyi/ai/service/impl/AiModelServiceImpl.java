package com.ruoyi.ai.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.AiModelMapper;
import com.ruoyi.ai.domain.AiModel;
import com.ruoyi.ai.service.IAiModelService;

/**
 * AI模型配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@Service
public class AiModelServiceImpl implements IAiModelService 
{
    private static final Logger log = LoggerFactory.getLogger(AiModelServiceImpl.class);

    @Autowired
    private AiModelMapper aiModelMapper;

    /**
     * 查询AI模型配置
     * 
     * @param id AI模型配置主键
     * @return AI模型配置
     */
    @Override
    public AiModel selectAiModelById(Long id)
    {
        return aiModelMapper.selectAiModelById(id);
    }

    /**
     * 查询AI模型配置列表
     * 
     * @param aiModel AI模型配置
     * @return AI模型配置
     */
    @Override
    public List<AiModel> selectAiModelList(AiModel aiModel)
    {
        return aiModelMapper.selectAiModelList(aiModel);
    }

    /**
     * 新增AI模型配置
     * 
     * @param aiModel AI模型配置
     * @return 结果
     */
    @Override
    public int insertAiModel(AiModel aiModel)
    {
//        aiModel.setStatus(1);
        aiModel.setCreateBy(SecurityUtils.getUsername());
        aiModel.setCreateTime(new Date());
        return aiModelMapper.insertAiModel(aiModel);
    }

    /**
     * 修改AI模型配置
     * 
     * @param aiModel AI模型配置
     * @return 结果
     */
    @Override
    public int updateAiModel(AiModel aiModel)
    {
        aiModel.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        aiModel.setUpdateTime(new Date());
        return aiModelMapper.updateAiModel(aiModel);
    }

    /**
     * 批量删除AI模型配置
     * 
     * @param ids 需要删除的AI模型配置主键
     * @return 结果
     */
    @Override
    public int deleteAiModelByIds(Long[] ids)
    {
        return aiModelMapper.deleteAiModelByIds(ids);
    }

    /**
     * 删除AI模型配置信息
     * 
     * @param id AI模型配置主键
     * @return 结果
     */
    @Override
    public int deleteAiModelById(Long id)
    {
        return aiModelMapper.deleteAiModelById(id);
    }
}
