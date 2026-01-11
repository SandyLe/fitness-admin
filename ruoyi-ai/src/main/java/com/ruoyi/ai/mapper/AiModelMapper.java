package com.ruoyi.ai.mapper;

import java.util.List;
import com.ruoyi.ai.domain.AiModel;

/**
 * AI模型配置Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface AiModelMapper 
{
    /**
     * 查询AI模型配置
     * 
     * @param id AI模型配置主键
     * @return AI模型配置
     */
    public AiModel selectAiModelById(Long id);

    /**
     * 查询AI模型配置列表
     * 
     * @param aiModel AI模型配置
     * @return AI模型配置集合
     */
    public List<AiModel> selectAiModelList(AiModel aiModel);

    /**
     * 新增AI模型配置
     * 
     * @param aiModel AI模型配置
     * @return 结果
     */
    public int insertAiModel(AiModel aiModel);

    /**
     * 修改AI模型配置
     * 
     * @param aiModel AI模型配置
     * @return 结果
     */
    public int updateAiModel(AiModel aiModel);

    /**
     * 删除AI模型配置
     * 
     * @param id AI模型配置主键
     * @return 结果
     */
    public int deleteAiModelById(Long id);

    /**
     * 批量删除AI模型配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAiModelByIds(Long[] ids);
}
