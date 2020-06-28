package com.dhcc.urms.roleprivilege.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色-权限 映射表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-23
 */
@TableName("T_ROLE_PRIVILEGE")
@KeySequence("SEQ_URMS")
public class RolePrivilege implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    /**
     * 角色ID
     */
    @TableField("ROLE_ID")
    private Long roleId;

    /**
     * 权限ID——对应 T_MODULE 和 机构、科室等
     */
    @TableField("PRIVI_ID")
    private String priviId;

    /**
     * 权限类型
     */
    @TableField("PRIVI_TYPE_CODE")
    private String priviTypeCode;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPriviId() {
        return priviId;
    }

    public void setPriviId(String priviId) {
        this.priviId = priviId;
    }

    public String getPriviTypeCode() {
        return priviTypeCode;
    }

    public void setPriviTypeCode(String priviTypeCode) {
        this.priviTypeCode = priviTypeCode;
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

    @Override
    public String toString() {
        return "RolePrivilege{" +
                   "id=" + id +
                   ", roleId=" + roleId +
                   ", priviId=" + priviId +
                   ", priviTypeCode=" + priviTypeCode +
                   ", updateTime=" + updateTime +
                   ", updater=" + updater +
                   "}";
    }
}
