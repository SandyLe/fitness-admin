package com.ruoyi.ai.mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ruoyi.ai.domain.KnowledgeBase;
import org.apache.ibatis.annotations.Param;

/**
 * 知识库Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface KnowledgeBaseMapper 
{
    /**
     * 查询知识库
     * 
     * @param id 知识库主键
     * @return 知识库
     */
    public KnowledgeBase selectKnowledgeBaseById(Long id);

    /**
     * 查询知识库列表
     * 
     * @param knowledgeBase 知识库
     * @return 知识库集合
     */
    public List<KnowledgeBase> selectKnowledgeBaseList(KnowledgeBase knowledgeBase);

    /**
     * 新增知识库
     * 
     * @param knowledgeBase 知识库
     * @return 结果
     */
    public int insertKnowledgeBase(KnowledgeBase knowledgeBase);

    /**
     * 修改知识库
     * 
     * @param knowledgeBase 知识库
     * @return 结果
     */
    public int updateKnowledgeBase(KnowledgeBase knowledgeBase);

    /**
     * 删除知识库
     * 
     * @param id 知识库主键
     * @return 结果
     */
    public int deleteKnowledgeBaseById(Long id);

    /**
     * 批量删除知识库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKnowledgeBaseByIds(Long[] ids);

}
