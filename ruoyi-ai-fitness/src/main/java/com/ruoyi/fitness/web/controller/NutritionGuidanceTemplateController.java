package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.NutritionGuidanceTemplate;
import com.ruoyi.fitness.service.INutritionGuidanceTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 营养指导模板Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/nutritionGuidanceTemplate")
public class NutritionGuidanceTemplateController extends BaseController
{
    @Autowired
    private INutritionGuidanceTemplateService iNutritionGuidanceTemplateService;

    /**
     * 查询营养指导模板列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(NutritionGuidanceTemplate nutritionGuidanceTemplate)
    {
        startPage();
        List<NutritionGuidanceTemplate> list = iNutritionGuidanceTemplateService.selectNutritionGuidanceTemplateList(nutritionGuidanceTemplate);
        return getDataTable(list);
    }

    /**
     * 获取营养指导模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceTemplate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(iNutritionGuidanceTemplateService.selectNutritionGuidanceTemplateById(id));
    }

    /**
     * 新增营养指导模板
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceTemplate:add')")
    @Log(title = "营养指导模板", code = "log.ai.NutritionGuidanceTemplate", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NutritionGuidanceTemplate nutritionGuidanceTemplate)
    {
        return toAjax(iNutritionGuidanceTemplateService.insertNutritionGuidanceTemplate(nutritionGuidanceTemplate));
    }

    /**
     * 修改营养指导模板
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceTemplate:edit')")
    @Log(title = "营养指导模板", code = "log.ai.NutritionGuidanceTemplate", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NutritionGuidanceTemplate nutritionGuidanceTemplate)
    {
        return toAjax(iNutritionGuidanceTemplateService.updateNutritionGuidanceTemplate(nutritionGuidanceTemplate));
    }

    /**
     * 删除营养指导模板
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceTemplate:remove')")
    @Log(title = "营养指导模板", code = "log.ai.NutritionGuidanceTemplate", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(iNutritionGuidanceTemplateService.deleteNutritionGuidanceTemplateByIds(ids));
        return toAjax(iNutritionGuidanceTemplateService.deleteNutritionGuidanceTemplate(ids));
    }
}
