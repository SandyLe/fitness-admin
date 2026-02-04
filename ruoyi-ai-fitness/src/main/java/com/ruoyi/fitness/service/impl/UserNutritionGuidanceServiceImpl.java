package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.UserNutritionGuidance;
import com.ruoyi.fitness.mapper.UserNutritionGuidanceMapper;
import com.ruoyi.fitness.service.IUserNutritionGuidanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class UserNutritionGuidanceServiceImpl implements IUserNutritionGuidanceService {

    @Autowired
    private UserNutritionGuidanceMapper userNutritionGuidanceMapper;

    @Override
    public UserNutritionGuidance selectUserNutritionGuidanceById(Long id) {
        return userNutritionGuidanceMapper.selectUserNutritionGuidanceById(id);
    }

    @Override
    public List<UserNutritionGuidance> selectUserNutritionGuidanceList(UserNutritionGuidance userNutritionGuidance) {
        return userNutritionGuidanceMapper.selectUserNutritionGuidanceList(userNutritionGuidance);
    }

    @Override
    public int insertUserNutritionGuidance(UserNutritionGuidance userNutritionGuidance) {
        return userNutritionGuidanceMapper.insertUserNutritionGuidance(userNutritionGuidance);
    }

    @Override
    public int updateUserNutritionGuidance(UserNutritionGuidance userNutritionGuidance) {
        return userNutritionGuidanceMapper.updateUserNutritionGuidance(userNutritionGuidance);
    }

    @Override
    public int deleteUserNutritionGuidanceByIds(Long[] ids) {
        return userNutritionGuidanceMapper.deleteUserNutritionGuidanceByIds(ids);
    }

    @Override
    public int deleteUserNutritionGuidanceById(Long id) {
        return userNutritionGuidanceMapper.deleteUserNutritionGuidanceById(id);
    }

    @Override
    public int deleteUserNutritionGuidance(Long[] ids) {
        return userNutritionGuidanceMapper.deleteUserNutritionGuidanceByIds(ids);
    }
}
