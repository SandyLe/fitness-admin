package com.ruoyi.ai.factory;

import com.ruoyi.ai.config.ModelType;
import com.ruoyi.ai.domain.AiModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicModelFactoryManager {

    private static final Logger log = LoggerFactory.getLogger(DynamicModelFactoryManager.class);

    @Autowired
    private List<AIModelFactory> modelFactories;

    private final Map<ModelType, AIModelFactory> factoryMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 初始化工厂映射
        for (AIModelFactory factory : modelFactories) {
            factoryMap.put(factory.supportedModelType(), factory);
        }
        log.info("大模型工厂管理器初始化完成");
    }

    /**
     * 根据数据库配置获取聊天模型
     */
//    public ChatLanguageModel getChatModel(String tenantId, ModelType modelType, String modelName) {
//        String cacheKey = buildCacheKey(tenantId, modelType, modelName, "chat");
//        return (ChatLanguageModel) modelCache.computeIfAbsent(cacheKey,
//                key -> createChatModel(tenantId, modelType, modelName));
//    }

    /**
     * 获取默认聊天模型
     */
//    public ChatLanguageModel getDefaultChatModel(String tenantId, ModelType modelType) {
//        AIModelConfig config = getDefaultModelConfig(tenantId, modelType);
//        return getChatModel(tenantId, modelType, config.getModelName());
//    }

    /**
     * 根据数据库配置获取流式模型
     */
//    public StreamingChatLanguageModel getStreamingModel(String tenantId, ModelType modelType, String modelName) {
//        String cacheKey = buildCacheKey(tenantId, modelType, modelName, "streaming");
//        return (StreamingChatLanguageModel) modelCache.computeIfAbsent(cacheKey,
//                key -> createStreamingModel(tenantId, modelType, modelName));
//    }


    /**
     * 创建流式模型
     * @param aiModel 模型信息
     */
    public StreamingChatLanguageModel createStreamingModel(AiModel aiModel) {
        ModelType modelType = ModelType.fromString(aiModel.getModelType());
        AIModelFactory factory = getFactory(modelType);
        log.info("创建流式模型: ModelType:{}\n ModelName:{} \n BaseUrl:{}", modelType, aiModel.getModelName(),aiModel.getBaseUrl());
        return factory.createStreamingModel(aiModel.getBaseUrl(),aiModel.getApiKey(),aiModel.getModelName());
    }


    /**
     * 获取所有支持的模型类型
     */
//    public List<ModelType> getSupportedModelTypes(String tenantId) {
//        List<AIModelConfig> configs = getModelConfigs(tenantId);
//        return configs.stream()
//                .map(AIModelConfig::getModelType)
//                .distinct()
//                .collect(Collectors.toList());
//    }

    /**
     * 清理租户的模型缓存
     */
//    public void clearTenantCache(String tenantId) {
//        modelCache.keySet().removeIf(key -> key.startsWith(tenantId + ":"));
//        log.info("清理租户 {} 的模型缓存", tenantId);
//    }

    /**
     * 重新加载租户配置
     */
//    public void reloadTenantConfig(String tenantId) {
//        clearTenantCache(tenantId);
//        refreshConfigCache(tenantId);
//    }

    private AIModelFactory getFactory(ModelType modelType) {
        AIModelFactory factory = factoryMap.get(modelType);
        if (factory == null) {
            throw new RuntimeException("不支持的模型类型: " + modelType);
        }
        return factory;
    }

    private String buildCacheKey(String tenantId, ModelType modelType, String modelName, String type) {
        return String.format("%s:%s-%s-%s", tenantId, modelType, modelName, type);
    }


//    private List<AIModelConfig> getModelConfigs(String tenantId) {
//        AiModel aiModel = new AiModel();
//        aiModel.setRemark(tenantId);
//        aiModel.setStatus(1);
//        List<AiModel> entities = aiModelService.selectAiModelList(aiModel);
//        return entities.stream()
//                .map(AiModel::toConfig)
//                .collect(Collectors.toList());
//    }


    /**
     * 获取特定模型配置
     */
//    public AIModelConfig getModelConfig(String tenantId, ModelType modelType, String modelName) {
//        AiModel aiModel = new AiModel();
//        aiModel.setModelName(modelName);
//        aiModel.setModelType(modelType.name());
//        aiModel.setStatus(1);
//        aiModel.setRemark(tenantId);
//        List<AiModel> entitys = aiModelService.selectAiModelList(aiModel);
//        Optional<AiModel> entity = entitys.stream()
//                .filter(e -> e.getModelName().equals(modelName))
//                .findFirst();
//        return entity.map(AiModel::toConfig)
//                .orElseThrow(() -> new RuntimeException("模型配置不存在: " + modelType + "-" + modelName));
//    }

    /**
     * 获取默认模型配置
     */
//    public AIModelConfig getDefaultModelConfig(String tenantId, ModelType modelType) {
//        AiModel aiModel = new AiModel();
//        aiModel.setModelType(modelType.name());
//        aiModel.setStatus(1);
//        aiModel.setRemark(tenantId);
//        List<AiModel> entities = aiModelService.selectAiModelList(aiModel);
//        if (entities.isEmpty()) {
//            throw new RuntimeException("没有找到 " + modelType + " 的模型配置");
//        }
//        // 返回第一个配置，或根据优先级选择
//        return entities.get(0).toConfig();
//    }

    /**
     * 刷新模型配置缓存
     */
//    @CacheEvict(value = "modelConfigs", key = "#tenantId")
//    public void refreshConfigCache(String tenantId) {
//        log.info("刷新租户 {} 的模型配置缓存", tenantId);
//    }
}
