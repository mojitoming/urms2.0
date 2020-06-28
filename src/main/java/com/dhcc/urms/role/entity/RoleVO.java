package com.dhcc.urms.role.entity;

public class RoleVO extends Role {
    private static final long serialVersionUID = 5387975830471529370L;

    private String statusName; // 状态名称

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
