package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.UserPlan;

import java.util.List;

public interface IUserPlanService {
    

    /**
     * 查询用户计划
     *
     * @param id 用户计划主键
     * @return 用户计划
     */
    public UserPlan selectUserPlanById(Long id);

    /**
     * 查询用户计划列表
     *
     * @param UserPlan 用户计划
     * @return 用户计划集合
     */
    public List<UserPlan> selectUserPlanList(UserPlan UserPlan);

    /**
     * 新增用户计划
     *
     * @param UserPlan 用户计划
     * @return 结果
     */
    public int insertUserPlan(UserPlan UserPlan);

    /**
     * 修改用户计划
     *
     * @param UserPlan 用户计划
     * @return 结果
     */
    public int updateUserPlan(UserPlan UserPlan);

    /**
     * 批量删除用户计划
     *
     * @param ids 需要删除的用户计划主键集合
     * @return 结果
     */
    public int deleteUserPlanByIds(Long[] ids);

    /**
     * 删除用户计划信息
     *
     * @param id 用户计划主键
     * @return 结果
     */
    public int deleteUserPlanById(Long id);

    /**
     * 删除后，用户计划下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteUserPlan(Long[] ids);
}
