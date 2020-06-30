package com.dhcc.urms.user.entity;

/**
 * Annotation:
 * User 数据对象
 *
 * @Author: Adam Ming
 * @Date: Jun 28, 2020 at 10:54:17 AM
 */
public class UserVO extends User {
    private static final long serialVersionUID = 2657522829606208720L;

    private String statusName; // 状态名称
    private String resetPassword; // 是否重置密码

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String resetPassword) {
        this.resetPassword = resetPassword;
    }
}
