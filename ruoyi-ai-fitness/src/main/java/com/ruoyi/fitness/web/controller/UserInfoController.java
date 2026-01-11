package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.fitness.domain.UserInfo;
import com.ruoyi.fitness.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/userInfo")
public class UserInfoController extends BaseController
{
    @Autowired
    private IUserInfoService iUserInfoService; 

    /**
     * 查询用户列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:userInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserInfo userInfo)
    {
        startPage();
        List<UserInfo> list = iUserInfoService.selectUserInfoList(userInfo);
        return getDataTable(list);
    }

    /**
     * 获取用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('ai:userInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(iUserInfoService.selectUserInfoById(id));
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('ai:userInfo:add')")
    @Log(title = "用户", code = "log.ai.userInfo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserInfo userInfo)
    {
        return toAjax(iUserInfoService.insertUserInfo(userInfo));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('ai:userInfo:edit')")
    @Log(title = "用户", code = "log.ai.userInfo", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserInfo userInfo)
    {
        return toAjax(iUserInfoService.updateUserInfo(userInfo));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('ai:userInfo:remove')")
    @Log(title = "用户", code = "log.ai.userInfo", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
//        return toAjax(iUserInfoService.deleteuserInfoByIds(ids));
        return toAjax(iUserInfoService.deleteUserInfo(ids));
    }
}
