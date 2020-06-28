package com.dhcc.urms.role.entity;

import java.io.Serializable;

/**
 * Annotation:
 * 授权
 * JSON 对象
 *
 * @Author: Adam Ming
 * @Date: Jun 24, 2020 at 11:20:10 AM
 */
public class GrantJsonVO implements Serializable {
    private static final long serialVersionUID = -9033523483151008153L;

    private String roleId;
    private String roleName;
    private String nodeId;
    private String parentId;
    private String checked;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
