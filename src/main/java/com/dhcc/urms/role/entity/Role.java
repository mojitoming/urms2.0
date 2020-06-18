package com.dhcc.urms.role.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-17
 */
@TableName("T_ROLE")
@KeySequence(value = "SEQ_URMS")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ROLE_ID", type = IdType.INPUT)
    private BigDecimal roleId;

    @TableField("ROLE_NAME")
    private String roleName;

    @TableField("ROLE_DESC")
    private String roleDesc;

    @TableField("PRIORITY")
    private Long priority;

    @TableField("STATUS")
    private String status;

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Role{" +
                   "roleId=" + roleId +
                   ", roleName=" + roleName +
                   ", roleDesc=" + roleDesc +
                   ", priority=" + priority +
                   ", status=" + status +
                   "}";
    }
}
