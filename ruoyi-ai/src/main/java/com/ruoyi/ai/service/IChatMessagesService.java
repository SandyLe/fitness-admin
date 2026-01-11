package com.ruoyi.ai.service;


import com.ruoyi.ai.domain.ChatMessages;

import java.util.List;

public interface IChatMessagesService {

    ChatMessages selectChatMessagesById(String id);

    long deleteChatMessagesById(String id);

    long deleteChatMessagesByIds(List<String> ids);
}
