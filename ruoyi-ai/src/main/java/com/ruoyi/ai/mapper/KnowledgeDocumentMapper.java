package com.ruoyi.ai.mapper;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ruoyi.ai.domain.KnowledgeDocument;
import org.apache.ibatis.annotations.Param;

/**
 * 知识库文档Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface KnowledgeDocumentMapper 
{
    /**
     * 查询知识库文档
     * 
     * @param id 知识库文档主键
     * @return 知识库文档
     */
    public KnowledgeDocument selectKnowledgeDocumentById(Long id);

    /**
     * 查询知识库文档列表
     * 
     * @param knowledgeDocument 知识库文档
     * @return 知识库文档集合
     */
    public List<KnowledgeDocument> selectKnowledgeDocumentList(KnowledgeDocument knowledgeDocument);

    /**
     * 新增知识库文档
     * 
     * @param knowledgeDocument 知识库文档
     * @return 结果
     */
    public int insertKnowledgeDocument(KnowledgeDocument knowledgeDocument);

    /**
     * 修改知识库文档
     * 
     * @param knowledgeDocument 知识库文档
     * @return 结果
     */
    public int updateKnowledgeDocument(KnowledgeDocument knowledgeDocument);

    /**
     * 删除知识库文档
     * 
     * @param id 知识库文档主键
     * @return 结果
     */
    public int deleteKnowledgeDocumentById(Long id);

    /**
     * 批量删除知识库文档
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKnowledgeDocumentByIds(Long[] ids);


    /**
     * 根据用户ID统计文档数量
     */
    Long countByUserId(@Param("userId") Long userId);
}
