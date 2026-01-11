package com.ruoyi.ai.web.controller;

import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.ai.domain.ConversationSession;
import com.ruoyi.ai.service.IConversationSessionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 对话会话Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/ai/consession")
public class ConversationSessionController extends BaseController
{
    @Autowired
    private IConversationSessionService conversationSessionService;


    @GetMapping("/list")
    public TableDataInfo list(ConversationSession conversationSession)
    {
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            conversationSession.setUserId(getUserId());
        }
        List<ConversationSession> list = conversationSessionService.selectConversationSessionList(conversationSession);
        return getDataTable(list);
    }

    /**
     * 导出对话会话列表
     */
    @PreAuthorize("@ss.hasPermi('ai:consession:export')")
    @Log(title = "对话会话", code = "log.ai.conversationSession", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConversationSession conversationSession)
    {
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            conversationSession.setUserId(getUserId());
        }
        List<ConversationSession> list = conversationSessionService.selectConversationSessionList(conversationSession);
        ExcelUtil<ConversationSession> util = new ExcelUtil<ConversationSession>(ConversationSession.class);
        util.exportExcel(response, list, "对话会话数据");
    }

    /**
     * 获取对话会话详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:consession:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(conversationSessionService.selectConversationSessionById(id));
    }

    /**
     * 新增对话会话
     */
    @PreAuthorize("@ss.hasPermi('ai:consession:add')")
    @Log(title = "对话会话", code = "log.ai.conversationSession", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConversationSession conversationSession)
    {
        return toAjax(conversationSessionService.insertConversationSession(conversationSession));
    }

    /**
     * 修改对话会话
     */
    @PreAuthorize("@ss.hasPermi('ai:consession:edit')")
    @Log(title = "对话会话", code = "log.ai.conversationSession", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConversationSession conversationSession)
    {
        return toAjax(conversationSessionService.updateConversationSession(conversationSession));
    }

    /**
     * 删除对话会话
     */
    @PreAuthorize("@ss.hasPermi('ai:consession:remove')")
    @Log(title = "对话会话", code = "log.ai.conversationSession", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(conversationSessionService.deleteConversationSessionByIds(ids));
    }
}
