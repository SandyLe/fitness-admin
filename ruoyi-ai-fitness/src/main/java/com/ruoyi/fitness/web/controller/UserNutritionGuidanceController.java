package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.UserNutritionGuidance;
import com.ruoyi.fitness.service.IUserNutritionGuidanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户营养指导Controller
 *
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/userNutritionGuidance")
public class UserNutritionGuidanceController extends BaseController
{
    @Autowired
    private IUserNutritionGuidanceService iUserNutritionGuidanceservice;

    /**
     * 查询用户营养指导列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidance:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserNutritionGuidance userNutritionGuidance)
    {
        startPage();
        List<UserNutritionGuidance> list = iUserNutritionGuidanceservice.selectUserNutritionGuidanceList(userNutritionGuidance);
        return getDataTable(list);
    }

    /**
     * 获取用户营养指导详细信息
     */
    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(iUserNutritionGuidanceservice.selectUserNutritionGuidanceById(id));
    }

    /**
     * 新增用户营养指导
     */
    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidance:add')")
    @Log(title = "用户营养指导", code = "log.ai.UserNutritionGuidance", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserNutritionGuidance userNutritionGuidance)
    {
        return toAjax(iUserNutritionGuidanceservice.insertUserNutritionGuidance(userNutritionGuidance));
    }

    /**
     * 修改用户营养指导
     */
    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidance:edit')")
    @Log(title = "用户营养指导", code = "log.ai.UserNutritionGuidance", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserNutritionGuidance userNutritionGuidance)
    {
        return toAjax(iUserNutritionGuidanceservice.updateUserNutritionGuidance(userNutritionGuidance));
    }

    /**
     * 删除用户营养指导
     */
    @PreAuthorize("@ss.hasPermi('fitness:userNutritionGuidance:remove')")
    @Log(title = "用户营养指导", code = "log.ai.UserNutritionGuidance", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(iUserNutritionGuidanceService.deleteUserNutritionGuidanceByIds(ids));
        return toAjax(iUserNutritionGuidanceservice.deleteUserNutritionGuidance(ids));
    }
}
