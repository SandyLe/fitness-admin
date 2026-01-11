package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 注册用户信息
 * @author lixt
 * @date 2026年1月6日09:50:28
 */
public class UserInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 计划ID */
    private Long id;

    /** 体重 */
    private BigDecimal weight;
    /** 身高 */
    private BigDecimal height;

    /** 用户名称  */
    @Excel(name = "用户名称")
    private String userName;
    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;
    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;
    /** 电话 */
    @Excel(name = "电话")
    private String phone;
    /** 密码 */
    @Excel(name = "密码")
    private String password;
    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;
    /** 状态 */
    @Excel(name = "状态")
    private Integer status;
    /** 个人介绍 */
    @Excel(name = "个人介绍")
    private String introduce;
    /** 特殊字段 */
    @Excel(name = "特殊字段")
    private String extJson;
    /**  性别 */
    @Excel(name = "性别")
    private String gender;
    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;
    /** 目标 */
    @Excel(name = "目标")
    private String fitnessGoal;
    /** 状态 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(String fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

}
