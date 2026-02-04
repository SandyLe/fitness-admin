package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.NutritionGuidanceDetail;
import com.ruoyi.fitness.mapper.NutritionGuidanceDetailMapper;
import com.ruoyi.fitness.service.INutritionGuidanceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 营养指导模板明细Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class NutritionGuidanceDetailServiceImpl implements INutritionGuidanceDetailService {

    @Autowired
    private NutritionGuidanceDetailMapper nutritionGuidanceDetailMapper;

    @Override
    public NutritionGuidanceDetail selectNutritionGuidanceDetailById(Long id) {
        return nutritionGuidanceDetailMapper.selectNutritionGuidanceDetailById(id);
    }

    @Override
    public List<NutritionGuidanceDetail> selectNutritionGuidanceDetailList(NutritionGuidanceDetail nutritionGuidanceDetail) {
        return nutritionGuidanceDetailMapper.selectNutritionGuidanceDetailList(nutritionGuidanceDetail);
    }

    @Override
    public int insertNutritionGuidanceDetail(NutritionGuidanceDetail nutritionGuidanceDetail) {
        return nutritionGuidanceDetailMapper.insertNutritionGuidanceDetail(nutritionGuidanceDetail);
    }

    @Override
    public int updateNutritionGuidanceDetail(NutritionGuidanceDetail nutritionGuidanceDetail) {
        return nutritionGuidanceDetailMapper.updateNutritionGuidanceDetail(nutritionGuidanceDetail);
    }

    @Override
    public int deleteNutritionGuidanceDetailByIds(Long[] ids) {
        return nutritionGuidanceDetailMapper.deleteNutritionGuidanceDetailByIds(ids);
    }

    @Override
    public int deleteNutritionGuidanceDetailById(Long id) {
        return nutritionGuidanceDetailMapper.deleteNutritionGuidanceDetailById(id);
    }

    @Override
    public int deleteNutritionGuidanceDetail(Long[] ids) {
        return nutritionGuidanceDetailMapper.deleteNutritionGuidanceDetailByIds(ids);
    }
}
