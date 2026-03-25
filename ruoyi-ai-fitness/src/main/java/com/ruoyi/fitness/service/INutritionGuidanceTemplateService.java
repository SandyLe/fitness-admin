package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.NutritionGuidanceTemplate;

import java.util.List;

public interface INutritionGuidanceTemplateService {
    

    /**
     * 查询营养指导
     *
     * @param id 营养指导主键
     * @return 营养指导
     */
    public NutritionGuidanceTemplate selectNutritionGuidanceTemplateById(Long id);
    public NutritionGuidanceTemplate selectNutritionGuidanceTemplateByCode(String code);

    /**
     * 查询营养指导列表
     *
     * @param nutritionGuidanceTemplate 营养指导
     * @return 营养指导集合
     */
    public List<NutritionGuidanceTemplate> selectNutritionGuidanceTemplateList(NutritionGuidanceTemplate nutritionGuidanceTemplate);

    /**
     * 新增营养指导
     *
     * @param nutritionGuidanceTemplate 营养指导
     * @return 结果
     */
    public int insertNutritionGuidanceTemplate(NutritionGuidanceTemplate nutritionGuidanceTemplate);

    /**
     * 修改营养指导
     *
     * @param nutritionGuidanceTemplate 营养指导
     * @return 结果
     */
    public int updateNutritionGuidanceTemplate(NutritionGuidanceTemplate nutritionGuidanceTemplate);

    /**
     * 批量删除营养指导
     *
     * @param ids 需要删除的营养指导主键集合
     * @return 结果
     */
    public int deleteNutritionGuidanceTemplateByIds(Long[] ids);

    /**
     * 删除营养指导信息
     *
     * @param id 营养指导主键
     * @return 结果
     */
    public int deleteNutritionGuidanceTemplateById(Long id);

    /**
     * 删除后，营养指导下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteNutritionGuidanceTemplate(Long[] ids);
}
