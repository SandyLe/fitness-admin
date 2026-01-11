package com.ruoyi.ai.web.controller;

import java.util.List;

import com.ruoyi.ai.domain.Agent;
import com.ruoyi.ai.domain.KnowledgeDocument;
import com.ruoyi.ai.service.IAgentService;
import com.ruoyi.ai.service.IKnowledgeDocumentService;
import com.ruoyi.ai.service.IKnowledgeQuotaService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.i18n.MessageUtils;
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
import com.ruoyi.ai.domain.KnowledgeBase;
import com.ruoyi.ai.service.IKnowledgeBaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 知识库Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/ai/knowbase")
public class KnowledgeBaseController extends BaseController
{
    @Autowired
    private IKnowledgeBaseService knowledgeBaseService;

    @Autowired
    private IAgentService agentService;

    @Autowired
    private IKnowledgeDocumentService knowledgeDocumentService;
    @Autowired
    private IKnowledgeQuotaService knowledgeQuotaService;

    /**
     * 查询知识库列表
     */
    @PreAuthorize("@ss.hasPermi('ai:knowbase:list')")
    @GetMapping("/list")
    public TableDataInfo list(KnowledgeBase knowledgeBase)
    {
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            knowledgeBase.setUserId(getUserId());
        }
        startPage();
        List<KnowledgeBase> list = knowledgeBaseService.selectKnowledgeBaseList(knowledgeBase);
        return getDataTable(list);
    }

    /**
     * 导出知识库列表
     */
    @PreAuthorize("@ss.hasPermi('ai:knowbase:export')")
    @Log(title = "知识库", code = "log.ai.knowledgeBase", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KnowledgeBase knowledgeBase)
    {
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            knowledgeBase.setUserId(getUserId());
        }
        List<KnowledgeBase> list = knowledgeBaseService.selectKnowledgeBaseList(knowledgeBase);
        ExcelUtil<KnowledgeBase> util = new ExcelUtil<KnowledgeBase>(KnowledgeBase.class);
        util.exportExcel(response, list, "知识库数据");
    }

    /**
     * 获取知识库详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:knowbase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(knowledgeBaseService.selectKnowledgeBaseById(id));
    }

    /**
     * 新增知识库
     */
    @PreAuthorize("@ss.hasPermi('ai:knowbase:add')")
    @Log(title = "知识库", code = "log.ai.knowledgeBase", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KnowledgeBase knowledgeBase)
    {
        knowledgeQuotaService.verifyKnowledgeQuota(); //验证知识库是否超过限额
        return toAjax(knowledgeBaseService.insertKnowledgeBase(knowledgeBase));
    }

    /**
     * 修改知识库
     */
    @PreAuthorize("@ss.hasPermi('ai:knowbase:edit')")
    @Log(title = "知识库", code = "log.ai.knowledgeBase", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KnowledgeBase knowledgeBase)
    {
        return toAjax(knowledgeBaseService.updateKnowledgeBase(knowledgeBase));
    }

    /**
     * 删除知识库 删除知识库需要先清空向量存储，同时确认没有智能体引用这个知识库
     */
    @PreAuthorize("@ss.hasPermi('ai:knowbase:remove')")
    @Log(title = "知识库", code = "log.ai.knowledgeBase", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (ids == null || ids.length == 0) {
            return toAjax(0);
        }
        //删除之前，先验证是否有智能体引用，如果有，提示用户先删除智能体
        int delSuccess = 0;
        if(null != ids && ids.length>0){
            for(Long kbId:ids){
                // 1. 检查是否被 Agent 引用（安全匹配）
                Agent agent = new Agent();
                agent.setKnowledgeBaseIds(String.valueOf(kbId));
                if(!Constants.SUPER_ADMIN.equals(getUsername())){
                    agent.setUserId(getUserId());
                }
                List<Agent> list = agentService.selectAgentList(agent);
                if(null != list && !list.isEmpty()){
                    return error(MessageUtils.message("bus.ai.knowledgeBase.no.delete",list.get(0).getAgentName()));
                }
                // 2. 检查是否有文档
                KnowledgeDocument knowledgeDocument = new KnowledgeDocument();
                knowledgeDocument.setKbId(kbId);
                //删除之前验证知识库是否有文件
                List<KnowledgeDocument> knowledgeDocuments = knowledgeDocumentService.selectKnowledgeDocumentList(knowledgeDocument);
                if(null != knowledgeDocuments && !knowledgeDocuments.isEmpty()){
                    return error(MessageUtils.message("bus.ai.knowledgeBase.doc.no.delete",knowledgeDocuments.size()));
                }
                delSuccess += knowledgeBaseService.deleteKnowledgeBaseById(kbId); //删除数据
            }
        }
        return toAjax(delSuccess);
    }
}
