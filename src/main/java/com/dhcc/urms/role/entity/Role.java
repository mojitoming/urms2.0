package com.dhcc.urms.role.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-18
 */
@TableName("T_ROLE")
@KeySequence("SEQ_URMS")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID，主键
     */
    @TableId(value = "ROLE_ID", type = IdType.INPUT)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("ROLE_DESC")
    private String roleDesc;

    /**
     * 角色优先级
     */
    @TableField("PRIORITY")
    private Long priority;

    /**
     * 角色状态，启用-ACTIVE, 停用-INACTIVE
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 创建人
     */
    @TableField(value = "CREATOR", fill = FieldFill.INSERT)
    private Long creator;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
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
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Role{" +
            "roleId=" + roleId +
            ", roleName=" + roleName +
            ", roleDesc=" + roleDesc +
            ", priority=" + priority +
            ", status=" + status +
            ", createDate=" + createDate +
            ", creator=" + creator +
        "}";
    }
}
