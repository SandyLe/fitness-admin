package com.ruoyi.ai.domain.dto;

import lombok.Data;

//聊天数据传输对象
@Data
public class ChatFormDTO {
    //会话id
    private String memoryId;
    //用户消息
    private String message;

    private Long agentId;

}
