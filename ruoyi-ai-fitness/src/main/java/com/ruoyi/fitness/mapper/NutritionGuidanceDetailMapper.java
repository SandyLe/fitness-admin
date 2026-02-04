package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.NutritionGuidanceDetail;

import java.util.List;

public interface NutritionGuidanceDetailMapper {

    /**
     * 查询营养指导明细
     *
     * @param id 营养指导明细主键
     * @return 营养指导明细
     */
    public NutritionGuidanceDetail selectNutritionGuidanceDetailById(Long id);

    /**
     * 查询营养指导明细列表
     *
     * @param nutritionGuidanceDetail 营养指导明细
     * @return 营养指导明细集合
     */
    public List<NutritionGuidanceDetail> selectNutritionGuidanceDetailList(NutritionGuidanceDetail nutritionGuidanceDetail);

    /**
     * 新增营养指导明细
     *
     * @param nutritionGuidanceDetail 营养指导明细
     * @return 结果
     */
    public int insertNutritionGuidanceDetail(NutritionGuidanceDetail nutritionGuidanceDetail);

    /**
     * 修改营养指导明细
     *
     * @param nutritionGuidanceDetail 营养指导明细
     * @return 结果
     */
    public int updateNutritionGuidanceDetail(NutritionGuidanceDetail nutritionGuidanceDetail);

    /**
     * 删除营养指导明细
     *
     * @param id 营养指导明细主键
     * @return 结果
     */
    public int deleteNutritionGuidanceDetailById(Long id);

    /**
     * 批量删除营养指导明细
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNutritionGuidanceDetailByIds(Long[] ids);
}
