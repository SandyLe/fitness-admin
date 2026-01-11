package com.ruoyi.ai.web.controller;

import java.util.List;

import com.ruoyi.ai.service.IKnowledgeQuotaService;
import com.ruoyi.common.exception.GlobalException;
import com.ruoyi.common.utils.StringUtils;
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
import com.ruoyi.ai.domain.KnowledgeDocument;
import com.ruoyi.ai.service.IKnowledgeDocumentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 知识库文档Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/ai/knowdoc")
public class KnowledgeDocumentController extends BaseController
{
    @Autowired
    private IKnowledgeDocumentService knowledgeDocumentService;

    @Autowired
    private IKnowledgeQuotaService knowledgeQuotaService;
    /**
     * 查询知识库文档列表
     */
    @PreAuthorize("@ss.hasPermi('ai:knowdoc:list')")
    @GetMapping("/list")
    public TableDataInfo list(KnowledgeDocument knowledgeDocument)
    {
        if(null == knowledgeDocument.getKbId()) throw new GlobalException("kbId is null!");
        startPage();
        List<KnowledgeDocument> list = knowledgeDocumentService.selectKnowledgeDocumentList(knowledgeDocument);
        return getDataTable(list);
    }

    /**
     * 导出知识库文档列表
     */
    @PreAuthorize("@ss.hasPermi('ai:knowdoc:export')")
    @Log(title = "知识库文档", code = "log.ai.knowledgeDocument", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KnowledgeDocument knowledgeDocument)
    {
        if(null == knowledgeDocument.getKbId()) throw new GlobalException("kbId is null!");
        List<KnowledgeDocument> list = knowledgeDocumentService.selectKnowledgeDocumentList(knowledgeDocument);
        ExcelUtil<KnowledgeDocument> util = new ExcelUtil<KnowledgeDocument>(KnowledgeDocument.class);
        util.exportExcel(response, list, "知识库文档数据");
    }

    /**
     * 获取知识库文档详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:knowdoc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(knowledgeDocumentService.selectKnowledgeDocumentById(id));
    }

    /**
     * 新增知识库文档
     */
    @PreAuthorize("@ss.hasPermi('ai:knowdoc:add')")
    @Log(title = "知识库文档", code = "log.ai.knowledgeDocument", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KnowledgeDocument knowledgeDocument)
    {
        knowledgeQuotaService.verifyKnowledgeQuota(); //验证知识库是否超过限额
        return success(knowledgeDocumentService.addKnowledgeDocument(knowledgeDocument).getId());
    }

    /**
     * 修改知识库文档
     */
    @PreAuthorize("@ss.hasPermi('ai:knowdoc:edit')")
    @Log(title = "知识库文档", code = "log.ai.knowledgeDocument", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KnowledgeDocument knowledgeDocument)
    {
        return toAjax(knowledgeDocumentService.updateKnowledgeDocument(knowledgeDocument));
    }

    /**
     * 删除知识库文档
     */
    @PreAuthorize("@ss.hasPermi('ai:knowdoc:remove')")
    @Log(title = "知识库文档", code = "log.ai.knowledgeDocument", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(knowledgeDocumentService.delKnowledgeDocument(ids));
    }
}
