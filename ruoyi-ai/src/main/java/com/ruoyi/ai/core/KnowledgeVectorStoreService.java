package com.ruoyi.ai.core;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.ai.domain.KnowledgeDocument;
import com.ruoyi.common.exception.GlobalException;
import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.filter.MetadataFilterBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * 向量数据库操作
 */
@Component("knowledgeVectorStoreService")
@Slf4j
public class KnowledgeVectorStoreService {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private RedisEmbeddingStore redisEmbeddingStore; // 共享索引，向量数据库


    private final DocumentSplitter splitter = DocumentSplitters.recursive(1500, 150);

    /**
     * 加载本地文档到向量数据库
     * @param knowledgeDocument
     * @param userId
     * @return 返回文档向量ID
     */
    public KnowledgeDocument processUserDocument(KnowledgeDocument knowledgeDocument, Long userId){
        //验证容量，文档核验 TODO
//        capacityManagementService.validateFileSize(userId, knowledgeDocument.getFileSize());
        Document document = getDocument(knowledgeDocument, userId);
        //使用默认的文档分割器
        List<String>  vectorIds = ingestWithIdReturn(document);
        if(null != vectorIds && !vectorIds.isEmpty()){
            knowledgeDocument.setVectorId(JSON.toJSONString(vectorIds));
            knowledgeDocument.setStatus(1);//成功
        }else {
            knowledgeDocument.setStatus(2);//失败
        }
        return knowledgeDocument;
    }

    /**
     * 处理文档并返回向量ID
     */
    private List<String> ingestWithIdReturn(Document document) {
        List<String> vectorIds = new ArrayList<>();
        List<TextSegment> segments = splitter.split(document);
        try {
            // 处理每个分块
            for (int i = 0; i < segments.size(); i++) {
                TextSegment segment = segments.get(i);
                // 生成向量
                Response<Embedding> embeddingResponse = embeddingModel.embed(segment.text());
                Embedding embedding = embeddingResponse.content();
                // 存储并获取向量ID
                String vectorId = redisEmbeddingStore.add(embedding, segment);
                vectorIds.add(vectorId);
                log.debug("分块 {} 存储完成, 向量ID: {}", i, vectorId);
            }
            log.info("文档处理完成: 分块数={}, 向量ID数={}", segments.size(), vectorIds.size());
            return vectorIds;

        } catch (Exception e) {
            log.error("处理文档失败", e);
            throw new RuntimeException("文档处理失败: " + e.getMessage());
        }
    }

    /**
     * 批量加载文档到向量数据库
     * @param list
     * @param userId
     */
    public void processUserDocuments(List<KnowledgeDocument> list, Long userId){
        if(null == list || list.isEmpty()){
            throw new GlobalException("error:KnowledgeDocument list is null");
        }

        if(list.size() > 10 ){
            throw new GlobalException("error:KnowledgeDocument list size is not 10");
        }
        for (KnowledgeDocument knowledgeDocument:list){
            try {
                processUserDocument(knowledgeDocument, userId);
            } catch (Exception e) {
                log.error("处理文档 {} 失败: {}", knowledgeDocument.getId(), e.getMessage());
            }
        }
    }


    /**
     * 处理文档
     * @param knowledgeDocument
     * @param userId
     * @return
     */
    private Document getDocument(KnowledgeDocument knowledgeDocument,Long userId){
        Document document = FileSystemDocumentLoader.loadDocument(knowledgeDocument.getFilePath());
        // 关键：在元数据中标记用户
        document.metadata().put("userId", String.valueOf(userId));
        document.metadata().put("tenant", "user_" + userId);
        document.metadata().put("timestamp", String.valueOf(System.currentTimeMillis()));
        document.metadata().put("kbId", String.valueOf(knowledgeDocument.getKbId()));
        document.metadata().put("docId", String.valueOf(knowledgeDocument.getId()));
        return document;
    }

    /**
     * 根据条件删除
     * @param vectorIds 根据向量ID删除
     */
    public void deleteUserDocument(List<String> vectorIds) {
        redisEmbeddingStore.removeAll(vectorIds);
    }

    /**
     * 多知识库检索
     * @param kbIds
     * @param query
     * @return
     */
    public EmbeddingSearchResult<TextSegment> search(List<Long> kbIds,String query){
        Filter current = MetadataFilterBuilder.metadataKey("kbId")
                .isIn(kbIds);
        return searchWithFilter(query, current, 20); //默认20个结果;
    }

    /**
     * 单知识库检索
     * @param kbId
     * @param query
     * @return
     */
    public EmbeddingSearchResult<TextSegment> search(Long kbId,String query){
        Filter current = MetadataFilterBuilder.metadataKey("kbId")
                .isEqualTo(kbId);
        return searchWithFilter(query, current, 20); //默认20个结果;
    }

    /**
     * 通用的带过滤搜索
     * @param query
     * @param filter
     * @param maxResults
     * @return
     */
    private EmbeddingSearchResult<TextSegment> searchWithFilter(String query, Filter filter, int maxResults) {
        try {
            Embedding queryEmbedding = embeddingModel.embed(query).content();
            EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                    .queryEmbedding(queryEmbedding)
                    .maxResults(maxResults)
                    .filter(filter)  // 添加过滤条件
                    .minScore(0.75)   // 默认阈值，太低会导致检索度太高，测试过0.75最合适
                    .build();
            return redisEmbeddingStore.search(request);
        } catch (Exception e) {
            log.error("过滤搜索失败", e);
            throw new RuntimeException("搜索失败");
        }
    }



}
