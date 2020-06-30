package com.dhcc.urms.user.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-28
 */
@TableName("T_USER")
@KeySequence("SEQ_URMS")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "USER_ID", type = IdType.INPUT)
    private Long userId;

    /**
     * 用户名
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 密码（加密）
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 昵称
     */
    @TableField("NICKNAME")
    private String nickname;

    /**
     * 密码盐
     */
    @TableField("SALT")
    private String salt;

    /**
     * 用户有效期开始时间
     */
    @TableField("WARRANT_START_DATE")
    private LocalDateTime warrantStartDate;

    /**
     * 用户有效期结束时间
     */
    @TableField("WARRANT_END_DATE")
    private LocalDateTime warrantEndDate;

    /**
     * 状态
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
    private String creator;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getWarrantStartDate() {
        return warrantStartDate;
    }

    public void setWarrantStartDate(LocalDateTime warrantStartDate) {
        this.warrantStartDate = warrantStartDate;
    }

    public LocalDateTime getWarrantEndDate() {
        return warrantEndDate;
    }

    public void setWarrantEndDate(LocalDateTime warrantEndDate) {
        this.warrantEndDate = warrantEndDate;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "User{" +
                   "userId=" + userId +
                   ", username=" + username +
                   ", password=" + password +
                   ", nickname=" + nickname +
                   ", salt=" + salt +
                   ", warrantStartDate=" + warrantStartDate +
                   ", warrantEndDate=" + warrantEndDate +
                   ", status=" + status +
                   ", createDate=" + createDate +
                   ", creator=" + creator +
                   "}";
    }
}
