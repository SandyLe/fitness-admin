package com.ruoyi.fitness.service.impl;

import com.ruoyi.fitness.domain.UserPlan;
import com.ruoyi.fitness.mapper.UserPlanMapper;
import com.ruoyi.fitness.service.IUserPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户计划Service
 * @author lixt
 * @date 2026-01-04
 */
@Service
public class UserPlanServiceImpl implements IUserPlanService {

    @Autowired
    private UserPlanMapper userPlanMapper;
    @Override
    public UserPlan selectUserPlanById(Long id) {
        return null;
    }

    @Override
    public List<UserPlan> selectUserPlanList(UserPlan userPlan) {
        return userPlanMapper.selectUserPlanList(userPlan);
    }

    @Override
    public int insertUserPlan(UserPlan userPlan) {
        return 0;
    }

    @Override
    public int updateUserPlan(UserPlan userPlan) {
        return 0;
    }

    @Override
    public int deleteUserPlanByIds(Long[] ids) {
        return 0;
    }

    @Override
    public int deleteUserPlanById(Long id) {
        return 0;
    }

    @Override
    public int deleteUserPlan(Long[] ids) {
        return 0;
    }
}
