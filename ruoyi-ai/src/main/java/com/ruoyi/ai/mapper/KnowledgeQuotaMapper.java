package com.ruoyi.ai.mapper;

import java.util.List;
import com.ruoyi.ai.domain.KnowledgeQuota;

/**
 * 用户知识库配额Mapper接口
 * 
 * @author ruoyi
 * @date 2025-12-11
 */
public interface KnowledgeQuotaMapper 
{
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
     * 删除用户知识库配额
     * 
     * @param quotaId 用户知识库配额主键
     * @return 结果
     */
    public int deleteKnowledgeQuotaByQuotaId(Long quotaId);

    /**
     * 批量删除用户知识库配额
     * 
     * @param quotaIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKnowledgeQuotaByQuotaIds(Long[] quotaIds);
}
