package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.UserNutritionGuidanceDetail;

import java.util.List;

public interface IUserNutritionGuidanceDetailService {
    

    /**
     * 查询营养指导明细
     *
     * @param id 营养指导明细主键
     * @return 营养指导明细
     */
    public UserNutritionGuidanceDetail selectUserNutritionGuidanceDetailById(Long id);

    /**
     * 查询营养指导明细列表
     *
     * @param UserNutritionGuidanceDetail 营养指导明细
     * @return 营养指导明细集合
     */
    public List<UserNutritionGuidanceDetail> selectUserNutritionGuidanceDetailList(UserNutritionGuidanceDetail UserNutritionGuidanceDetail);

    /**
     * 新增营养指导明细
     *
     * @param UserNutritionGuidanceDetail 营养指导明细
     * @return 结果
     */
    public int insertUserNutritionGuidanceDetail(UserNutritionGuidanceDetail UserNutritionGuidanceDetail);

    /**
     * 修改营养指导明细
     *
     * @param UserNutritionGuidanceDetail 营养指导明细
     * @return 结果
     */
    public int updateUserNutritionGuidanceDetail(UserNutritionGuidanceDetail UserNutritionGuidanceDetail);

    /**
     * 批量删除营养指导明细
     *
     * @param ids 需要删除的营养指导明细主键集合
     * @return 结果
     */
    public int deleteUserNutritionGuidanceDetailByIds(Long[] ids);

    /**
     * 删除营养指导明细信息
     *
     * @param id 营养指导明细主键
     * @return 结果
     */
    public int deleteUserNutritionGuidanceDetailById(Long id);

    /**
     * 删除后，营养指导明细下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteUserNutritionGuidanceDetail(Long[] ids);
}
