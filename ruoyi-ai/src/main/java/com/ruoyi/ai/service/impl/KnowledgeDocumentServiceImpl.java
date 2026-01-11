package com.ruoyi.ai.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.ai.core.KnowledgeVectorStoreService;
import com.ruoyi.ai.service.IKnowledgeBaseService;
import com.ruoyi.ai.service.IKnowledgeQuotaService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ai.mapper.KnowledgeDocumentMapper;
import com.ruoyi.ai.domain.KnowledgeDocument;
import com.ruoyi.ai.service.IKnowledgeDocumentService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 知识库文档Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@Service
public class KnowledgeDocumentServiceImpl implements IKnowledgeDocumentService 
{
    private static final Logger log = LoggerFactory.getLogger(KnowledgeDocumentServiceImpl.class);
    @Autowired
    private KnowledgeDocumentMapper knowledgeDocumentMapper;
    @Autowired
    private KnowledgeVectorStoreService knowledgeVectorStoreService;
    @Autowired
    private IKnowledgeQuotaService knowledgeQuotaService;

    @Autowired
    private IKnowledgeBaseService knowledgeBaseService;

    @Override
    @Transactional
    public KnowledgeDocument addKnowledgeDocument(KnowledgeDocument knowledgeDocument) {
        Long userId = SecurityUtils.getUserId();

        // 1. 验证文件
        if (!FileUtils.fileExists(knowledgeDocument.getFilePath())) {
            throw new RuntimeException("File does not exist!");
        }
        File file = new File(knowledgeDocument.getFilePath());
        knowledgeDocument.setDocType(FileUtils.getSuffix(knowledgeDocument.getFilePath()));
        knowledgeDocument.setFileSize(file.length());
        // 2. 先处理向量（事务外）
        KnowledgeDocument enrichedDoc = knowledgeVectorStoreService.processUserDocument(knowledgeDocument, userId);
        boolean dbSuccess = false;
        try {
            // 3. 执行所有 DB 操作
            int inserted = insertKnowledgeDocument(enrichedDoc);
            if (inserted != 1) {
                throw new RuntimeException("Insert failed");
            }
            knowledgeQuotaService.refreshKnowledgeQuota(enrichedDoc, 1);
            knowledgeBaseService.refreshKnowledgeBase(1, enrichedDoc.getKbId());
            dbSuccess = true; // 标记 DB 全部成功
            return enrichedDoc;
        } finally {
            // 4. 如果 DB 未完全成功，清理向量数据
            if (!dbSuccess) {
                try {
                    knowledgeVectorStoreService.deleteUserDocument(
                            JSON.parseObject(enrichedDoc.getVectorId(), new TypeReference<>() {})
                    );
                } catch (Exception e) {
                    log.warn("Cleanup vector data failed, vectorId: {}, docId: {}",
                            enrichedDoc.getVectorId(), enrichedDoc.getId(), e);
                }
            }
        }
    }

    @Override
    @Transactional
    public int delKnowledgeDocument(Long[] ids) {
        int successCount = 0;
        for (Long id:ids) {
            try {
                delKnowledgeDocument(id); // 独立事务
                successCount++;
            } catch (Exception e) {
                log.error("删除文档 {} 失败: {}", id, e.getMessage());
            }
        }
        return successCount;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int delKnowledgeDocument(Long id) {
        KnowledgeDocument doc = selectKnowledgeDocumentById(id);
        if (doc == null) return 0;
        // 1. 先删除外部资源（向量、文件）
        knowledgeVectorStoreService.deleteUserDocument(JSON.parseObject(doc.getVectorId(), new TypeReference<>() {}));
        FileUtils.deleteFile(doc.getFilePath());
        int delSuccess = deleteKnowledgeDocumentById(doc.getId());
        knowledgeQuotaService.refreshKnowledgeQuota(doc, -1);
        knowledgeBaseService.refreshKnowledgeBase(-1, doc.getKbId());
        // 2. 再删除/更新数据库（这些操作在一个小事务内）
        return delSuccess;
    }

    @Override
    public Long countDocumentByUserId(Long userId) {
        return knowledgeDocumentMapper.countByUserId(userId);
    }

    /**
     * 查询知识库文档
     * 
     * @param id 知识库文档主键
     * @return 知识库文档
     */
    @Override
    public KnowledgeDocument selectKnowledgeDocumentById(Long id)
    {
        return knowledgeDocumentMapper.selectKnowledgeDocumentById(id);
    }

    /**
     * 查询知识库文档列表
     * 
     * @param knowledgeDocument 知识库文档
     * @return 知识库文档
     */
    @Override
    public List<KnowledgeDocument> selectKnowledgeDocumentList(KnowledgeDocument knowledgeDocument)
    {
        return knowledgeDocumentMapper.selectKnowledgeDocumentList(knowledgeDocument);
    }

    /**
     * 新增知识库文档
     * 
     * @param knowledgeDocument 知识库文档
     * @return 结果
     */
    @Override
    public int insertKnowledgeDocument(KnowledgeDocument knowledgeDocument)
    {
        knowledgeDocument.setCreateBy(SecurityUtils.getUsername());
        knowledgeDocument.setCreateTime(new Date());
        return knowledgeDocumentMapper.insertKnowledgeDocument(knowledgeDocument);
    }

    /**
     * 修改知识库文档
     * 
     * @param knowledgeDocument 知识库文档
     * @return 结果
     */
    @Override
    public int updateKnowledgeDocument(KnowledgeDocument knowledgeDocument)
    {
        knowledgeDocument.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        knowledgeDocument.setUpdateTime(new Date());
        return knowledgeDocumentMapper.updateKnowledgeDocument(knowledgeDocument);
    }

    /**
     * 批量删除知识库文档
     * 
     * @param ids 需要删除的知识库文档主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeDocumentByIds(Long[] ids)
    {
        return knowledgeDocumentMapper.deleteKnowledgeDocumentByIds(ids);
    }

    /**
     * 删除知识库文档信息
     * 
     * @param id 知识库文档主键
     * @return 结果
     */
    @Override
    public int deleteKnowledgeDocumentById(Long id)
    {
        return knowledgeDocumentMapper.deleteKnowledgeDocumentById(id);
    }
}
