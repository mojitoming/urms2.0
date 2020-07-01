package com.dhcc.urms.role.dto;

import com.dhcc.urms.common.entity.BaseAbstractDTO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.role.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Annotation:
 * 角色
 * 数据传输对象
 *
 * @Author: Adam Ming
 * @Date: Jun 18, 2020 at 11:32:22 AM
 */
@Component
public class RoleDTO extends BaseAbstractDTO {
    private static final long serialVersionUID = 8837562718149473975L;

    private Role role;
    private List<Role> roleList;
    private DTreeVO dTreeVO;
    private long userId;
    private String nickname;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public DTreeVO getdTreeVO() {
        return dTreeVO;
    }

    public void setdTreeVO(DTreeVO dTreeVO) {
        this.dTreeVO = dTreeVO;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
