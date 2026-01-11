package com.ruoyi.ai.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.ruoyi.ai.domain.ChatMessages;
import com.ruoyi.ai.service.IChatMessagesService;
import com.ruoyi.common.utils.i18n.MessageUtils;
import com.ruoyi.common.utils.i18n.MongoCollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChatMessagesServiceImpl implements IChatMessagesService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ChatMessages selectChatMessagesById(String id) {
        //语言处理
        String collectionName = MongoCollectionUtils.getChatMessagesCollectionName("chat_messages");
        Criteria criteria = Criteria.where("memoryId").is(id);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ChatMessages.class,collectionName);
    }

    @Override
    public long deleteChatMessagesById(String id) {
        String collectionName = MongoCollectionUtils.getChatMessagesCollectionName("chat_messages");
        Criteria criteria = Criteria.where("memoryId").is(id);
        Query query=new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, ChatMessages.class,collectionName);
        return result.getDeletedCount();
    }

    @Override
    public long deleteChatMessagesByIds(List<String> ids) {
        String collectionName = MongoCollectionUtils.getChatMessagesCollectionName("chat_messages");
        Criteria criteria = Criteria.where("memoryId").in(ids);
        Query query=new Query(criteria);
        DeleteResult result = mongoTemplate.remove(query, ChatMessages.class,collectionName);
        return result.getDeletedCount();
    }
}
