package com.ruoyi.ai.service;

import java.util.List;
import com.ruoyi.ai.domain.KnowledgeDocument;

/**
 * 知识库文档Service接口
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
public interface IKnowledgeDocumentService 
{


    /**
     * 上传知识库文档，文档加载到向量数据库
     * @param knowledgeDocument
     * @return
     */
    KnowledgeDocument addKnowledgeDocument(KnowledgeDocument knowledgeDocument);


    /**
     * 删除用户文档
     * @param ids
     * @return
     */
    int delKnowledgeDocument(Long[] ids);
    int delKnowledgeDocument(Long id);


    /**
     * 根据用户ID统计文档数量
     */
    Long countDocumentByUserId(Long userId);
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
     * 批量删除知识库文档
     * 
     * @param ids 需要删除的知识库文档主键集合
     * @return 结果
     */
    public int deleteKnowledgeDocumentByIds(Long[] ids);

    /**
     * 删除知识库文档信息
     * 
     * @param id 知识库文档主键
     * @return 结果
     */
    public int deleteKnowledgeDocumentById(Long id);
}
