package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.NutritionGuidanceTemplate;
import com.ruoyi.fitness.mapper.NutritionGuidanceTemplateMapper;
import com.ruoyi.fitness.service.INutritionGuidanceTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 营养指导模板Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class NutritionGuidanceTemplateServiceImpl implements INutritionGuidanceTemplateService {

    @Autowired
    private NutritionGuidanceTemplateMapper nutritionGuidanceTemplateMapper;

    @Override
    public NutritionGuidanceTemplate selectNutritionGuidanceTemplateById(Long id) {
        return nutritionGuidanceTemplateMapper.selectNutritionGuidanceTemplateById(id);
    }

    @Override
    public NutritionGuidanceTemplate selectNutritionGuidanceTemplateByCode(String templateCode) {
        return nutritionGuidanceTemplateMapper.selectNutritionGuidanceTemplateByCode(templateCode);
    }

    @Override
    public List<NutritionGuidanceTemplate> selectNutritionGuidanceTemplateList(NutritionGuidanceTemplate nutritionGuidanceTemplate) {
        return nutritionGuidanceTemplateMapper.selectNutritionGuidanceTemplateList(nutritionGuidanceTemplate);
    }

    @Override
    public int insertNutritionGuidanceTemplate(NutritionGuidanceTemplate nutritionGuidanceTemplate) {
        return nutritionGuidanceTemplateMapper.insertNutritionGuidanceTemplate(nutritionGuidanceTemplate);
    }

    @Override
    public int updateNutritionGuidanceTemplate(NutritionGuidanceTemplate nutritionGuidanceTemplate) {
        return nutritionGuidanceTemplateMapper.updateNutritionGuidanceTemplate(nutritionGuidanceTemplate);
    }

    @Override
    public int deleteNutritionGuidanceTemplateByIds(Long[] ids) {
        return nutritionGuidanceTemplateMapper.deleteNutritionGuidanceTemplateByIds(ids);
    }

    @Override
    public int deleteNutritionGuidanceTemplateById(Long id) {
        return nutritionGuidanceTemplateMapper.deleteNutritionGuidanceTemplateById(id);
    }

    @Override
    public int deleteNutritionGuidanceTemplate(Long[] ids) {
        return nutritionGuidanceTemplateMapper.deleteNutritionGuidanceTemplateByIds(ids);
    }
}
