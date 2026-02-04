package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.UserNutritionGuidance;

import java.util.List;

public interface UserNutritionGuidanceMapper {

    /**
     * 查询营养指导模板
     *
     * @param id 营养指导模板主键
     * @return 营养指导模板
     */
    public UserNutritionGuidance selectUserNutritionGuidanceById(Long id);

    /**
     * 查询营养指导模板列表
     *
     * @param userNutritionGuidance 营养指导模板
     * @return 营养指导模板集合
     */
    public List<UserNutritionGuidance> selectUserNutritionGuidanceList(UserNutritionGuidance userNutritionGuidance);

    /**
     * 新增营养指导模板
     *
     * @param userNutritionGuidance 营养指导模板
     * @return 结果
     */
    public int insertUserNutritionGuidance(UserNutritionGuidance userNutritionGuidance);

    /**
     * 修改营养指导模板
     *
     * @param userNutritionGuidance 营养指导模板
     * @return 结果
     */
    public int updateUserNutritionGuidance(UserNutritionGuidance userNutritionGuidance);

    /**
     * 删除营养指导模板
     *
     * @param id 营养指导模板主键
     * @return 结果
     */
    public int deleteUserNutritionGuidanceById(Long id);

    /**
     * 批量删除营养指导模板
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserNutritionGuidanceByIds(Long[] ids);
}
