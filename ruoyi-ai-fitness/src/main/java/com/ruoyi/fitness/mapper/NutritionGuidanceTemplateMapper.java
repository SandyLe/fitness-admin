package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.NutritionGuidanceTemplate;

import java.util.List;

public interface NutritionGuidanceTemplateMapper {

    /**
     * 查询营养指导模板
     *
     * @param id 营养指导模板主键
     * @return 营养指导模板
     */
    public NutritionGuidanceTemplate selectNutritionGuidanceTemplateById(Long id);
    public NutritionGuidanceTemplate selectNutritionGuidanceTemplateByCode(String templateCode);
    /**
     * 查询营养指导模板列表
     *
     * @param nutritionGuidanceTemplate 营养指导模板
     * @return 营养指导模板集合
     */
    public List<NutritionGuidanceTemplate> selectNutritionGuidanceTemplateList(NutritionGuidanceTemplate nutritionGuidanceTemplate);

    /**
     * 新增营养指导模板
     *
     * @param nutritionGuidanceTemplate 营养指导模板
     * @return 结果
     */
    public int insertNutritionGuidanceTemplate(NutritionGuidanceTemplate nutritionGuidanceTemplate);

    /**
     * 修改营养指导模板
     *
     * @param nutritionGuidanceTemplate 营养指导模板
     * @return 结果
     */
    public int updateNutritionGuidanceTemplate(NutritionGuidanceTemplate nutritionGuidanceTemplate);

    /**
     * 删除营养指导模板
     *
     * @param id 营养指导模板主键
     * @return 结果
     */
    public int deleteNutritionGuidanceTemplateById(Long id);

    /**
     * 批量删除营养指导模板
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNutritionGuidanceTemplateByIds(Long[] ids);
}
