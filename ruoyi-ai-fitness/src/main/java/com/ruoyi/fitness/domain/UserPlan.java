package com.ruoyi.fitness.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户计划表
 * @author lixt
 * @created
 */
public class UserPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 计划ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 计划名称 */
    @Excel(name = "计划名称")
    private String plan;

    /** 计划描述 */
    @Excel(name = "计划描述")
    private String context;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
