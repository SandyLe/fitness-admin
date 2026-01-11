package com.ruoyi.ai.store;


import com.ruoyi.ai.domain.ChatMessages;
import com.ruoyi.common.utils.i18n.MessageUtils;
import com.ruoyi.common.utils.i18n.MongoCollectionUtils;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String collectionName = MongoCollectionUtils.getChatMessagesCollectionName("chat_messages");
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class,collectionName);
        if (chatMessages == null) {
            return new LinkedList<>();
        }
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String collectionName = MongoCollectionUtils.getChatMessagesCollectionName("chat_messages");
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query=new Query(criteria);
        Update update=new Update();
        update.set("content", ChatMessageSerializer.messagesToJson(messages));
        // 使用 upsert 方法，如果不存在则插入新文档
        mongoTemplate.upsert(query, update, ChatMessages.class,collectionName);

    }

    @Override
    public void deleteMessages(Object memoryId) {
        String collectionName = MongoCollectionUtils.getChatMessagesCollectionName("chat_messages");
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query=new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class,collectionName);

    }
}
