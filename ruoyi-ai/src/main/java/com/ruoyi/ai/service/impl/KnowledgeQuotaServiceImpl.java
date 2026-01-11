package com.ruoyi.ai.service.impl;

import java.util.List;
import java.util.Optional;

import com.ruoyi.ai.domain.KnowledgeDocument;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.i18n.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.KnowledgeQuotaMapper;
import com.ruoyi.ai.domain.KnowledgeQuota;
import com.ruoyi.ai.service.IKnowledgeQuotaService;

/**
 * 用户知识库配额Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-12-11
 */
@Service
public class KnowledgeQuotaServiceImpl implements IKnowledgeQuotaService 
{
    private static final Logger log = LoggerFactory.getLogger(KnowledgeQuotaServiceImpl.class);
    @Autowired
    private KnowledgeQuotaMapper knowledgeQuotaMapper;

    @Override
    public void verifyKnowledgeQuota() {
        Long userId = SecurityUtils.getUserId();
        // 1. 获取当前登录用户 ID
        KnowledgeQuota knowledgeQuota = getKnowledgeQuotaByUserId(userId);
        if(knowledgeQuota.getUsedStorageBytes()>= knowledgeQuota.getMaxStorageBytes()){
                throw new ServiceException(MessageUtils.message("bus.ai.knowledgeQuota.maxKb.size",FileUtils.formatBytes(knowledgeQuota.getUsedStorageBytes())));
        }

        if(knowledgeQuota.getUsedKbCount()>= knowledgeQuota.getMaxKbCount()){
            throw new ServiceException(MessageUtils.message("bus.ai.knowledgeQuota.maxKb.count",knowledgeQuota.getUsedKbCount()));
        }

    }

    @Override
    public void refreshKnowledgeQuota(KnowledgeDocument knowledgeDocument,int operation) {
        if (knowledgeDocument == null) {
            return;
        }
        Long fileSize = knowledgeDocument.getFileSize();
        if (fileSize == null || fileSize <= 0) {
            return; // 无效文件大小，跳过
        }
        Long userId = SecurityUtils.getUserId();
        // 1. 获取当前登录用户 ID
        KnowledgeQuota knowledgeQuota = getKnowledgeQuotaByUserId(userId);
        // 3. 安全计算新的已用存储（防止负数和溢出）
        long currentUsed = knowledgeQuota.getUsedStorageBytes() != null ? knowledgeQuota.getUsedStorageBytes() : 0L;
        long newUsedStorage;
        if (operation == 1) {
            // 新增：加 fileSize
            if (currentUsed > Long.MAX_VALUE - fileSize) {
                newUsedStorage = Long.MAX_VALUE; // 防溢出
            } else {
                newUsedStorage = currentUsed + fileSize;
            }
        } else if (operation == -1) {
            // 删除：减 fileSize，但不能低于 0
            if (fileSize >= currentUsed) {
                newUsedStorage = 0L;
            } else {
                newUsedStorage = currentUsed - fileSize;
            }
        } else {
            return; // 不应到达此处
        }
        knowledgeQuota.setUsedStorageBytes(newUsedStorage);
        // 4. 更新配额
        updateKnowledgeQuota(knowledgeQuota);
    }

    @Override
    public void refreshKnowledgeQuota(int operation,int count) {
        // 1. 参数校验
        if (operation != 1 && operation != -1) {
            throw new IllegalArgumentException("operation must be 1 (add) or -1 (remove)");
        }
        if (count < 0) {
            throw new IllegalArgumentException("count must be >= 0");
        }
        Long userId = SecurityUtils.getUserId();
        // 2. 查询当前用户配额
        KnowledgeQuota knowledgeQuota = getKnowledgeQuotaByUserId(userId);
        if (knowledgeQuota == null) {
            log.warn("User knowledgeQuota not found, userId: {}", SecurityUtils.getUserId());
            return;
        }

        long current = Optional.ofNullable(knowledgeQuota.getUsedKbCount()).orElse(0L);
        long delta = operation * count;
        long newUsed = Math.max(0, current + delta);
        knowledgeQuota.setUsedKbCount(newUsed);
        updateKnowledgeQuota(knowledgeQuota);

    }


    /**
     * 根据用户ID查询用户知识库配额
     * @return KnowledgeQuota 用户知识库配额
     */
    private KnowledgeQuota getKnowledgeQuotaByUserId(Long userId){
        KnowledgeQuota knowledgeQuota = new KnowledgeQuota();
        knowledgeQuota.setUserId(userId);
        List<KnowledgeQuota> list = knowledgeQuotaMapper.selectKnowledgeQuotaList(knowledgeQuota);
        if (list == null || list.isEmpty()) {
            // 3. 初始化默认配额（新用户）
            knowledgeQuotaMapper.insertKnowledgeQuota(knowledgeQuota);
            knowledgeQuota = knowledgeQuotaMapper.selectKnowledgeQuotaByQuotaId(knowledgeQuota.getQuotaId());
        } else {
            knowledgeQuota = list.get(0);
        }
        return knowledgeQuota;
    }
    /**
     * 查询用户知识库配额
     * 
     * @param quotaId 用户知识库配额主键
     * @return 用户知识库配额
     */
    @Override
    public KnowledgeQuota selectKnowledgeQuotaByQuotaId(Long quotaId)
    {
        return knowledgeQuotaMapper.selectKnowledgeQuotaByQuotaId(quotaId);
    }

    /**
     * 查询用户知识库配额列表
     * 
     * @param knowledgeQuota 用户知识库配额
     * @return 用户知识库配额
     */
    @Override
    public List<KnowledgeQuota> selectKnowledgeQuotaList(KnowledgeQuota knowledgeQuota)
    {
        return knowledgeQuotaMapper.selectKnowledgeQuotaList(knowledgeQuota);
    }

    /**
     * 新增用户知识库配额
     * 
     * @param knowledgeQuota 用户知识库配额
     * @return 结果
     */
    @Override
    public int insertKnowledgeQuota(KnowledgeQuota knowledgeQuota)
    {
        knowledgeQuota.setCreateTime(DateUtils.getNowDate());
        knowledgeQuota.setUserId(SecurityUtils.getUserId());
        knowledgeQuota.setCreateBy(SecurityUtils.getUsername());
        return knowledgeQuotaMapper.insertKnowledgeQuota(knowledgeQuota);
    }

    /**
     * 修改用户知识库配额
     * 
     * @param knowledgeQuota 用户知识库配额
     * @return 结果
     */
    @Override
    public int updateKnowledgeQuota(KnowledgeQuota knowledgeQuota)
    {
        knowledgeQuota.setUpdateTime(DateUtils.getNowDate());
        knowledgeQuota.setUpdateBy(SecurityUtils.getUsername());
        return knowledgeQuotaMapper.updateKnowledgeQuota(knowledgeQuota);
    }

    /**
     * 批量删除用户知识库配额
     * 
     * @param quotaIds 需要删除的用户知识库配额主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeQuotaByQuotaIds(Long[] quotaIds)
    {
        return knowledgeQuotaMapper.deleteKnowledgeQuotaByQuotaIds(quotaIds);
    }

    /**
     * 删除用户知识库配额信息
     * 
     * @param quotaId 用户知识库配额主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeQuotaByQuotaId(Long quotaId)
    {
        return knowledgeQuotaMapper.deleteKnowledgeQuotaByQuotaId(quotaId);
    }
}
