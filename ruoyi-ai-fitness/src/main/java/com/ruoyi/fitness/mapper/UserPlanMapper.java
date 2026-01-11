package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.UserPlan;

import java.util.List;

public interface UserPlanMapper {

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
     * @param userPlan 用户计划
     * @return 用户计划集合
     */
    public List<UserPlan> selectUserPlanList(UserPlan userPlan);

    /**
     * 新增用户计划
     *
     * @param userPlan 用户计划
     * @return 结果
     */
    public int insertUserPlan(UserPlan userPlan);

    /**
     * 修改用户计划
     *
     * @param userPlan 用户计划
     * @return 结果
     */
    public int updateUserPlan(UserPlan userPlan);

    /**
     * 删除用户计划
     *
     * @param id 用户计划主键
     * @return 结果
     */
    public int deleteUserPlanById(Long id);

    /**
     * 批量删除用户计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserPlanByIds(Long[] ids);
}
