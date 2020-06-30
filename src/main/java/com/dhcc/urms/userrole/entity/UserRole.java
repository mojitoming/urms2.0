package com.dhcc.urms.userrole.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户-角色 映射表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-30
 */
@TableName("T_USER_ROLE")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    @TableField("USER_ID")
    private BigDecimal userId;

    /**
     * 表主键
     */
    @TableField("ROLE_ID")
    private BigDecimal roleId;

    /**
     * 更新时间
     */
    @TableField("UPDATE_TIME")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField("UPDATER")
    private String updater;

    /**
     * 是否默认登录角色 1：是 ,0：否（默认1）用户分配多个角色时优先级高的角色默认1，低的默认0
     */
    @TableField("IS_DEFAULT")
    private String isDefault;

    public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }
    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "UserRole{" +
            "userId=" + userId +
            ", roleId=" + roleId +
            ", updateTime=" + updateTime +
            ", updater=" + updater +
            ", isDefault=" + isDefault +
        "}";
    }
}
