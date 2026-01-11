package com.ruoyi.ai.web.controller;

import java.util.List;

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
import com.ruoyi.ai.domain.KnowledgeQuota;
import com.ruoyi.ai.service.IKnowledgeQuotaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户知识库配额Controller
 * 
 * @author ruoyi
 * @date 2025-12-11
 */
@RestController
@RequestMapping("/ai/knowquota")
public class KnowledgeQuotaController extends BaseController
{
    @Autowired
    private IKnowledgeQuotaService knowledgeQuotaService;

    /**
     * 查询当前用户的配额信息
     */
    @PreAuthorize("@ss.hasPermi('ai:knowbase:list')")
    @GetMapping(value = "/getInfoByUser")
    public AjaxResult getInfoByUser()
    {
        KnowledgeQuota knowledgeQuota = new KnowledgeQuota();
        knowledgeQuota.setUserId(getUserId());
        List<KnowledgeQuota> list = knowledgeQuotaService.selectKnowledgeQuotaList(knowledgeQuota);
        if(null == list || list.isEmpty()){
            //没有当前用户的配额信息就新增一条
             knowledgeQuotaService.insertKnowledgeQuota(knowledgeQuota);
             knowledgeQuota = knowledgeQuotaService.selectKnowledgeQuotaByQuotaId(knowledgeQuota.getQuotaId());
        }else {
            knowledgeQuota = list.get(0);
        }
        return success(knowledgeQuota);
    }

    /**
     * 查询用户知识库配额列表
     */
    @PreAuthorize("@ss.hasPermi('ai:knowquota:list')")
    @GetMapping("/list")
    public TableDataInfo list(KnowledgeQuota knowledgeQuota)
    {
        startPage();
        List<KnowledgeQuota> list = knowledgeQuotaService.selectKnowledgeQuotaList(knowledgeQuota);
        return getDataTable(list);
    }

    /**
     * 导出用户知识库配额列表
     */
    @PreAuthorize("@ss.hasPermi('ai:knowquota:export')")
    @Log(title = "用户知识库配额", code = "log.ai.knowledgeQuota", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KnowledgeQuota knowledgeQuota)
    {
        List<KnowledgeQuota> list = knowledgeQuotaService.selectKnowledgeQuotaList(knowledgeQuota);
        ExcelUtil<KnowledgeQuota> util = new ExcelUtil<KnowledgeQuota>(KnowledgeQuota.class);
        util.exportExcel(response, list, "用户知识库配额数据");
    }

    /**
     * 获取用户知识库配额详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:knowquota:query')")
    @GetMapping(value = "/{quotaId}")
    public AjaxResult getInfo(@PathVariable("quotaId") Long quotaId)
    {
        return success(knowledgeQuotaService.selectKnowledgeQuotaByQuotaId(quotaId));
    }

    /**
     * 新增用户知识库配额
     */
    @PreAuthorize("@ss.hasPermi('ai:knowquota:add')")
    @Log(title = "用户知识库配额", code = "log.ai.knowledgeQuota", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KnowledgeQuota knowledgeQuota)
    {
        return toAjax(knowledgeQuotaService.insertKnowledgeQuota(knowledgeQuota));
    }

    /**
     * 修改用户知识库配额
     */
    @PreAuthorize("@ss.hasPermi('ai:knowquota:edit')")
    @Log(title = "用户知识库配额", code = "log.ai.knowledgeQuota", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KnowledgeQuota knowledgeQuota)
    {
        return toAjax(knowledgeQuotaService.updateKnowledgeQuota(knowledgeQuota));
    }

    /**
     * 删除用户知识库配额
     */
    @PreAuthorize("@ss.hasPermi('ai:knowquota:remove')")
    @Log(title = "用户知识库配额", code = "log.ai.knowledgeQuota", businessType = BusinessType.DELETE)
	@DeleteMapping("/{quotaIds}")
    public AjaxResult remove(@PathVariable Long[] quotaIds)
    {
        return toAjax(knowledgeQuotaService.deleteKnowledgeQuotaByQuotaIds(quotaIds));
    }
}
