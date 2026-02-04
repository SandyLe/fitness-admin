package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.UserNutritionGuidance;

import java.util.List;

public interface IUserNutritionGuidanceService {


    /**
     * 查询营养指导
     *
     * @param id 营养指导主键
     * @return 营养指导
     */
    public UserNutritionGuidance selectUserNutritionGuidanceById(Long id);

    /**
     * 查询营养指导列表
     *
     * @param userNutritionGuidance 营养指导
     * @return 营养指导集合
     */
    public List<UserNutritionGuidance> selectUserNutritionGuidanceList(UserNutritionGuidance userNutritionGuidance);

    /**
     * 新增营养指导
     *
     * @param userNutritionGuidance 营养指导
     * @return 结果
     */
    public int insertUserNutritionGuidance(UserNutritionGuidance userNutritionGuidance);

    /**
     * 修改营养指导
     *
     * @param userNutritionGuidance 营养指导
     * @return 结果
     */
    public int updateUserNutritionGuidance(UserNutritionGuidance userNutritionGuidance);

    /**
     * 批量删除营养指导
     *
     * @param ids 需要删除的营养指导主键集合
     * @return 结果
     */
    public int deleteUserNutritionGuidanceByIds(Long[] ids);

    /**
     * 删除营养指导信息
     *
     * @param id 营养指导主键
     * @return 结果
     */
    public int deleteUserNutritionGuidanceById(Long id);

    /**
     * 删除后，营养指导下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteUserNutritionGuidance(Long[] ids);
}
