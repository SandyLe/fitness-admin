package com.ruoyi.fitness.web.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.fitness.domain.UserPlan;
import com.ruoyi.fitness.service.IUserPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 计划Controller
 * 
 * @author ruoyi
 * @date 2025-11-24
 */
@RestController
@RequestMapping("/fitness/plan")
public class PlanController extends BaseController
{
    @Autowired
    private IUserPlanService IUserPlanService;

    /**
     * 查询智能体列表
     */
    @PreAuthorize("@ss.hasPermi('fitness:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserPlan userPlan)
    {
        //列表查询设置用户
        if(!Constants.SUPER_ADMIN.equals(getUsername())){
            userPlan.setUserId(getUserId());
        }
        startPage();
        List<UserPlan> list = IUserPlanService.selectUserPlanList(userPlan);
        return getDataTable(list);
    }

}
