package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.NutritionGuidanceDetail;
import com.ruoyi.fitness.service.INutritionGuidanceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 营养指导明细Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/nutritionGuidanceDetail")
public class NutritionGuidanceDetailController extends BaseController
{
    @Autowired
    private INutritionGuidanceDetailService iNutritionGuidanceDetailService;

    /**
     * 查询营养指导明细列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(NutritionGuidanceDetail nutritionGuidanceDetail)
    {
        startPage();
        List<NutritionGuidanceDetail> list = iNutritionGuidanceDetailService.selectNutritionGuidanceDetailList(nutritionGuidanceDetail);
        return getDataTable(list);
    }

    /**
     * 获取营养指导明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(iNutritionGuidanceDetailService.selectNutritionGuidanceDetailById(id));
    }

    /**
     * 新增营养指导明细
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceDetail:add')")
    @Log(title = "营养指导明细", code = "log.ai.NutritionGuidanceDetail", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NutritionGuidanceDetail nutritionGuidanceDetail)
    {
        return toAjax(iNutritionGuidanceDetailService.insertNutritionGuidanceDetail(nutritionGuidanceDetail));
    }

    /**
     * 修改营养指导明细
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceDetail:edit')")
    @Log(title = "营养指导明细", code = "log.ai.NutritionGuidanceDetail", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NutritionGuidanceDetail nutritionGuidanceDetail)
    {
        return toAjax(iNutritionGuidanceDetailService.updateNutritionGuidanceDetail(nutritionGuidanceDetail));
    }

    /**
     * 删除营养指导明细
     */
    @PreAuthorize("@ss.hasPermi('fitness:nutritionGuidanceDetail:remove')")
    @Log(title = "营养指导明细", code = "log.ai.NutritionGuidanceDetail", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(iNutritionGuidanceDetailService.deleteNutritionGuidanceDetailByIds(ids));
        return toAjax(iNutritionGuidanceDetailService.deleteNutritionGuidanceDetail(ids));
    }
}
