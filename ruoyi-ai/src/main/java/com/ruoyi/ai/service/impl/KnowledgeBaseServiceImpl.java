package com.ruoyi.ai.service.impl;

import java.util.*;

import com.ruoyi.ai.domain.*;
import com.ruoyi.ai.service.IKnowledgeDocumentService;
import com.ruoyi.ai.service.IKnowledgeQuotaService;
import com.ruoyi.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.KnowledgeBaseMapper;
import com.ruoyi.ai.service.IKnowledgeBaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 知识库Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@Service
public class KnowledgeBaseServiceImpl implements IKnowledgeBaseService
{
    private static final Logger log = LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);

    @Autowired
    private KnowledgeBaseMapper knowledgeBaseMapper;

    @Autowired
    private IKnowledgeQuotaService knowledgeQuotaService;


    @Override
    public void refreshKnowledgeBase(int operation, Long kbId) {
        // 1. 参数校验
        if (operation != 1 && operation != -1) {
            throw new IllegalArgumentException("operation must be 1 (add) or -1 (remove)");
        }
        KnowledgeBase knowledgeBase = selectKnowledgeBaseById(kbId);
        if (knowledgeBase != null) {
            log.warn("KnowledgeBase not found, kbId: {}", kbId);
            return;
        }
        // 简洁计算
        long current = knowledgeBase.getFileCount() != null ? knowledgeBase.getFileCount() : 0;
        knowledgeBase.setFileCount(Math.max(0, current + operation));
        updateKnowledgeBase(knowledgeBase);
    }

    /**
     * 查询知识库
     * 
     * @param id 知识库主键
     * @return 知识库
     */
    @Override
    public KnowledgeBase selectKnowledgeBaseById(Long id)
    {
        return knowledgeBaseMapper.selectKnowledgeBaseById(id);
    }

    /**
     * 查询知识库列表
     * 
     * @param knowledgeBase 知识库
     * @return 知识库
     */
    @Override
    public List<KnowledgeBase> selectKnowledgeBaseList(KnowledgeBase knowledgeBase)
    {
        return knowledgeBaseMapper.selectKnowledgeBaseList(knowledgeBase);
    }

    /**
     * 新增知识库
     * 
     * @param knowledgeBase 知识库
     * @return 结果
     */
    @Override
    @Transactional
    public int insertKnowledgeBase(KnowledgeBase knowledgeBase)
    {
        knowledgeBase.setUserId(SecurityUtils.getUserId());
        knowledgeBase.setCreateBy(SecurityUtils.getUsername());
        knowledgeBase.setCreateTime(new Date());
        int count = knowledgeBaseMapper.insertKnowledgeBase(knowledgeBase);
        knowledgeQuotaService.refreshKnowledgeQuota(1,count); //知识库加1
        return count;
    }

    /**
     * 修改知识库
     * 
     * @param knowledgeBase 知识库
     * @return 结果
     */
    @Override
    public int updateKnowledgeBase(KnowledgeBase knowledgeBase)
    {
        knowledgeBase.setUpdateBy(SecurityUtils.getUsername());
        knowledgeBase.setUpdateTime(new Date());
        return knowledgeBaseMapper.updateKnowledgeBase(knowledgeBase);
    }

    /**
     * 批量删除知识库
     * 
     * @param ids 需要删除的知识库主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeBaseByIds(Long[] ids)
    {
        int count = knowledgeBaseMapper.deleteKnowledgeBaseByIds(ids);
        knowledgeQuotaService.refreshKnowledgeQuota(-1,count); //知识库减1
        return count;
    }

    /**
     * 删除知识库信息
     * 
     * @param id 知识库主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteKnowledgeBaseById(Long id)
    {
        int count = knowledgeBaseMapper.deleteKnowledgeBaseById(id);
        knowledgeQuotaService.refreshKnowledgeQuota(-1,1); //知识库减1
        return count;
    }
}
