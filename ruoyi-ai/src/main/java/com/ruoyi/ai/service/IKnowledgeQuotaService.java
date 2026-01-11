package com.ruoyi.ai.service;

import java.util.List;

import com.ruoyi.ai.domain.KnowledgeBase;
import com.ruoyi.ai.domain.KnowledgeDocument;
import com.ruoyi.ai.domain.KnowledgeQuota;

/**
 * 用户知识库配额Service接口
 * 
 * @author ruoyi
 * @date 2025-12-11
 */
public interface IKnowledgeQuotaService 
{

    /**
     * 验证当前用户的知识库是否超出最大限额
     */
    void verifyKnowledgeQuota();

    /**
     * 根据文档更新用户知识库配额信息（知识库使用大小）
     * @param knowledgeDocument 文档对象
     * @param operation 操作类型：1 表示新增文档（加存储），-1 表示删除文档（减存储）
     */
    void refreshKnowledgeQuota(KnowledgeDocument knowledgeDocument,int operation);

    /**
     * 根据知识库更新用户知识库配额信息（知识库使用数量）
     * @param operation 操作类型：1 添加知识库（加数量），-1 删除知识库（减数量）
     * @param count 删除数量，最小值为0
     */
    void refreshKnowledgeQuota(int operation,int count);
    /**
     * 查询用户知识库配额
     * 
     * @param quotaId 用户知识库配额主键
     * @return 用户知识库配额
     */
    public KnowledgeQuota selectKnowledgeQuotaByQuotaId(Long quotaId);

    /**
     * 查询用户知识库配额列表
     * 
     * @param knowledgeQuota 用户知识库配额
     * @return 用户知识库配额集合
     */
    public List<KnowledgeQuota> selectKnowledgeQuotaList(KnowledgeQuota knowledgeQuota);

    /**
     * 新增用户知识库配额
     * 
     * @param knowledgeQuota 用户知识库配额
     * @return 结果
     */
    public int insertKnowledgeQuota(KnowledgeQuota knowledgeQuota);

    /**
     * 修改用户知识库配额
     * 
     * @param knowledgeQuota 用户知识库配额
     * @return 结果
     */
    public int updateKnowledgeQuota(KnowledgeQuota knowledgeQuota);

    /**
     * 批量删除用户知识库配额
     * 
     * @param quotaIds 需要删除的用户知识库配额主键集合
     * @return 结果
     */
    public int deleteKnowledgeQuotaByQuotaIds(Long[] quotaIds);

    /**
     * 删除用户知识库配额信息
     * 
     * @param quotaId 用户知识库配额主键
     * @return 结果
     */
    public int deleteKnowledgeQuotaByQuotaId(Long quotaId);
}
