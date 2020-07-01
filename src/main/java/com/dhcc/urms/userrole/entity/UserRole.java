package com.dhcc.urms.userrole.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
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
@KeySequence("SEQ_URMS")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "USER_ID")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;

    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(value = "UPDATER", fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 是否默认登录角色 1：是 ,0：否（默认1）用户分配多个角色时优先级高的角色默认1，低的默认0
     */
    @TableField("IS_DEFAULT")
    private String isDefault;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
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
                   "id=" + id +
                   ", userId=" + userId +
                   ", roleId=" + roleId +
                   ", updateTime=" + updateTime +
                   ", updater=" + updater +
                   ", isDefault=" + isDefault +
                   "}";
    }
}
