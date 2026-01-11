package com.ruoyi.ai.web.controller;


import com.ruoyi.ai.core.DynamicAiServiceCreator;
import com.ruoyi.ai.core.UniversalChatService;
import com.ruoyi.ai.domain.ConversationSession;
import com.ruoyi.ai.domain.dto.ChatFormDTO;
import com.ruoyi.ai.service.IChatMessagesService;
import com.ruoyi.ai.service.IConversationSessionService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/ai/chat")
public class AiChatController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(AiChatController.class);
    @Autowired
    private DynamicAiServiceCreator dynamicAiServiceCreator;
    @Autowired
    private IChatMessagesService chatMessagesService;
    @Autowired
    private IConversationSessionService conversationSessionService;

    @PostMapping(value = "/stream",produces = "text/stream;charset=utf-8")
    @PreAuthorize("@ss.hasPermi('ai:user:chat')")
    public Flux<String> stream(@RequestBody ChatFormDTO chatFormDTO) {
        log.info("chatFormDTO:{}",chatFormDTO);
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        // 记录会话开始
        conversationSessionService.recordSessionStart(chatFormDTO,userId,username);
        UniversalChatService service = dynamicAiServiceCreator.createChatService(UniversalChatService.class, chatFormDTO.getAgentId());
        return service.streamChatWithMemory(
                        chatFormDTO.getMemoryId(),
                        chatFormDTO.getMessage())
                .doOnComplete(() -> {
                    // 记录会话完成
                    conversationSessionService.recordSessionEnd(chatFormDTO,username);
                })
                .doOnError(error -> {
                    // 记录失败会话
                    conversationSessionService.recordSessionError(chatFormDTO, error,username);
                });
    }

    /**
     * 返回对话历史
     */
    @GetMapping(value = "/history/{memoryId}")
    @PreAuthorize("@ss.hasPermi('ai:user:history:chat')")
    public AjaxResult streamChatHistory(@PathVariable String memoryId) {
        return AjaxResult.success(chatMessagesService.selectChatMessagesById(memoryId));
    }

    /**
     * 获取历史对话列表
     */
    @GetMapping("/history/list")
    @PreAuthorize("@ss.hasPermi('ai:user:history')")
    public TableDataInfo getHisList(ConversationSession conversationSession)
    {
        conversationSession.setUserId(getUserId());
        startPage();
        List<ConversationSession> list = conversationSessionService.selectConversationSessionList(conversationSession);
        return getDataTable(list);
    }
}
