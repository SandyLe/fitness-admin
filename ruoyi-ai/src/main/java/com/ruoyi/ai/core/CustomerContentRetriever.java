package com.ruoyi.ai.core;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.ContentMetadata;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerContentRetriever implements ContentRetriever {

    private final  KnowledgeVectorStoreService knowledgeVectorStoreService;
    // 配置参数
    private final List<Long> knowledgeBaseIds;
    private Integer maxResults = 10;
    private Double minScore = 0.5;
    private boolean includeScoreInContent = true;

    /**
     * 私有构造方法，使用Builder创建
     */
    private CustomerContentRetriever(KnowledgeVectorStoreService knowledgeVectorStoreService,
                                     List<Long> knowledgeBaseIds,
                                     Integer maxResults,
                                     Double minScore,
                                     boolean includeScoreInContent) {
        this.knowledgeVectorStoreService = knowledgeVectorStoreService;
        this.knowledgeBaseIds = knowledgeBaseIds;
        this.maxResults = maxResults;
        this.minScore = minScore;
        this.includeScoreInContent = includeScoreInContent;
    }

    @Override
    public List<Content> retrieve(Query query) {
        // 1. 检查知识库ID
        if (knowledgeBaseIds == null || knowledgeBaseIds.isEmpty()) {
            return List.of();
        }
        // 2. 执行向量检索
        EmbeddingSearchResult<TextSegment> searchResult = knowledgeVectorStoreService.search(knowledgeBaseIds, query.text());
        // 3. 过滤和转换结果
        return searchResult.matches().stream().map((embeddingMatch) -> Content.from(embeddingMatch.embedded(), Map.of(ContentMetadata.SCORE, embeddingMatch.score(), ContentMetadata.EMBEDDING_ID, embeddingMatch.embeddingId()))).collect(Collectors.toList());
    }


    /**
     * Builder类 - 用于创建配置好的CustomerContentRetriever
     */
    public static class Builder {
        private final KnowledgeVectorStoreService knowledgeVectorStoreService;
        private List<Long> knowledgeBaseIds;
        private Integer maxResults = 10;
        private Double minScore = 0.5;
        private boolean includeScoreInContent = true;

        public Builder(KnowledgeVectorStoreService knowledgeVectorStoreService) {
            this.knowledgeVectorStoreService = knowledgeVectorStoreService;
        }

        public Builder withKnowledgeBaseIds(List<Long> knowledgeBaseIds) {
            this.knowledgeBaseIds = knowledgeBaseIds;
            return this;
        }

        public Builder withMaxResults(Integer maxResults) {
            this.maxResults = maxResults;
            return this;
        }

        public Builder withMinScore(Double minScore) {
            this.minScore = minScore;
            return this;
        }

        public Builder includeScoreInContent(boolean include) {
            this.includeScoreInContent = include;
            return this;
        }

        public CustomerContentRetriever build() {
            return new CustomerContentRetriever(
                    knowledgeVectorStoreService,
                    knowledgeBaseIds,
                    maxResults,
                    minScore,
                    includeScoreInContent
            );
        }
    }
}
