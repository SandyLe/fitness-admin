package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.UserNutritionGuidanceDetail;
import com.ruoyi.fitness.mapper.UserNutritionGuidanceDetailMapper;
import com.ruoyi.fitness.service.IUserNutritionGuidanceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户营养指导明细Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class UserNutritionGuidanceDetailServiceImpl implements IUserNutritionGuidanceDetailService {

    @Autowired
    private UserNutritionGuidanceDetailMapper userNutritionGuidanceDetailMapper;

    @Override
    public UserNutritionGuidanceDetail selectUserNutritionGuidanceDetailById(Long id) {
        return userNutritionGuidanceDetailMapper.selectUserNutritionGuidanceDetailById(id);
    }

    @Override
    public List<UserNutritionGuidanceDetail> selectUserNutritionGuidanceDetailList(UserNutritionGuidanceDetail userNutritionGuidanceDetail) {
        return userNutritionGuidanceDetailMapper.selectUserNutritionGuidanceDetailList(userNutritionGuidanceDetail);
    }

    @Override
    public int insertUserNutritionGuidanceDetail(UserNutritionGuidanceDetail userNutritionGuidanceDetail) {
        return userNutritionGuidanceDetailMapper.insertUserNutritionGuidanceDetail(userNutritionGuidanceDetail);
    }

    @Override
    public int updateUserNutritionGuidanceDetail(UserNutritionGuidanceDetail userNutritionGuidanceDetail) {
        return userNutritionGuidanceDetailMapper.updateUserNutritionGuidanceDetail(userNutritionGuidanceDetail);
    }

    @Override
    public int deleteUserNutritionGuidanceDetailByIds(Long[] ids) {
        return userNutritionGuidanceDetailMapper.deleteUserNutritionGuidanceDetailByIds(ids);
    }

    @Override
    public int deleteUserNutritionGuidanceDetailById(Long id) {
        return userNutritionGuidanceDetailMapper.deleteUserNutritionGuidanceDetailById(id);
    }

    @Override
    public int deleteUserNutritionGuidanceDetail(Long[] ids) {
        return userNutritionGuidanceDetailMapper.deleteUserNutritionGuidanceDetailByIds(ids);
    }
}
