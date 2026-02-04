package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.UserNutritionGuidanceDetail;

import java.util.List;

public interface UserNutritionGuidanceDetailMapper {

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
     * @param userNutritionGuidanceDetail 营养指导明细
     * @return 营养指导明细集合
     */
    public List<UserNutritionGuidanceDetail> selectUserNutritionGuidanceDetailList(UserNutritionGuidanceDetail userNutritionGuidanceDetail);

    /**
     * 新增营养指导明细
     *
     * @param userNutritionGuidanceDetail 营养指导明细
     * @return 结果
     */
    public int insertUserNutritionGuidanceDetail(UserNutritionGuidanceDetail userNutritionGuidanceDetail);

    /**
     * 修改营养指导明细
     *
     * @param userNutritionGuidanceDetail 营养指导明细
     * @return 结果
     */
    public int updateUserNutritionGuidanceDetail(UserNutritionGuidanceDetail userNutritionGuidanceDetail);

    /**
     * 删除营养指导明细
     *
     * @param id 营养指导明细主键
     * @return 结果
     */
    public int deleteUserNutritionGuidanceDetailById(Long id);

    /**
     * 批量删除营养指导明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserNutritionGuidanceDetailByIds(Long[] ids);
}
