package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.UserNutritionGuidanceDetail;
import com.ruoyi.fitness.service.IUserNutritionGuidanceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户营养明细Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/userNutritionGuidanceDetail")
public class UserNutritionGuidanceDetailController extends BaseController
{
    @Autowired
    private IUserNutritionGuidanceDetailService iUserNutritionGuidanceDetailservice;

    /**
     * 查询用户营养明细列表
     */
//    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidanceDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserNutritionGuidanceDetail userNutritionGuidanceDetail)
    {
        startPage();
        List<UserNutritionGuidanceDetail> list = iUserNutritionGuidanceDetailservice.selectUserNutritionGuidanceDetailList(userNutritionGuidanceDetail);
        return getDataTable(list);
    }

    /**
     * 获取用户营养明细详细信息
     */
//    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidanceDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(iUserNutritionGuidanceDetailservice.selectUserNutritionGuidanceDetailById(id));
    }

    /**
     * 新增用户营养明细
     */
//    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidanceDetail:add')")
    @Log(title = "用户营养明细", code = "log.ai.UserNutritionGuidanceDetail", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserNutritionGuidanceDetail userNutritionGuidanceDetail)
    {
        return toAjax(iUserNutritionGuidanceDetailservice.insertUserNutritionGuidanceDetail(userNutritionGuidanceDetail));
    }

    /**
     * 修改用户营养明细
     */
//    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidanceDetail:edit')")
    @Log(title = "用户营养明细", code = "log.ai.UserNutritionGuidanceDetail", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserNutritionGuidanceDetail userNutritionGuidanceDetail)
    {
        return toAjax(iUserNutritionGuidanceDetailservice.updateUserNutritionGuidanceDetail(userNutritionGuidanceDetail));
    }

    /**
     * 删除用户营养明细
     */
//    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidanceDetail:remove')")
    @Log(title = "用户营养明细", code = "log.ai.UserNutritionGuidanceDetail", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(iUserNutritionGuidanceDetailService.deleteUserNutritionGuidanceDetailByIds(ids));
        return toAjax(iUserNutritionGuidanceDetailservice.deleteUserNutritionGuidanceDetail(ids));
    }
}
