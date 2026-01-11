package com.ruoyi.fitness.mapper;

import com.ruoyi.fitness.domain.UserInfo;

import java.util.List;

public interface UserInfoMapper {

    /**
     * 查询用户计划
     *
     * @param id 用户计划主键
     * @return 用户计划
     */
    public UserInfo selectUserInfoById(Long id);

    /**
     * 查询用户计划列表
     *
     * @param UserInfo 用户计划
     * @return 用户计划集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo UserInfo);

    /**
     * 新增用户计划
     *
     * @param UserInfo 用户计划
     * @return 结果
     */
    public int insertUserInfo(UserInfo UserInfo);

    /**
     * 修改用户计划
     *
     * @param UserInfo 用户计划
     * @return 结果
     */
    public int updateUserInfo(UserInfo UserInfo);

    /**
     * 删除用户计划
     *
     * @param id 用户计划主键
     * @return 结果
     */
    public int deleteUserInfoById(Long id);

    /**
     * 批量删除用户计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserInfoByIds(Long[] ids);
}
