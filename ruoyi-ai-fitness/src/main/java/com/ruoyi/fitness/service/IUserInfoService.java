package com.ruoyi.fitness.service;

import com.ruoyi.fitness.domain.UserInfo;

import java.util.List;

public interface IUserInfoService {
    

    /**
     * 查询用户信息
     *
     * @param id 用户信息主键
     * @return 用户信息
     */
    public UserInfo selectUserInfoById(Long id);

    /**
     * 查询用户信息列表
     *
     * @param UserInfo 用户信息
     * @return 用户信息集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo UserInfo);

    /**
     * 新增用户信息
     *
     * @param UserInfo 用户信息
     * @return 结果
     */
    public int insertUserInfo(UserInfo UserInfo);

    /**
     * 修改用户信息
     *
     * @param UserInfo 用户信息
     * @return 结果
     */
    public int updateUserInfo(UserInfo UserInfo);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息主键集合
     * @return 结果
     */
    public int deleteUserInfoByIds(Long[] ids);

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息主键
     * @return 结果
     */
    public int deleteUserInfoById(Long id);

    /**
     * 删除后，用户信息下的历史对话及详情同步删除
     * @param ids
     * @return
     */
    int deleteUserInfo(Long[] ids);
}
