package com.dhcc.urms.org.dto;

import com.dhcc.urms.common.entity.BaseAbstractDTO;
import com.dhcc.urms.common.entity.DTreeVO;

/**
 * Annotation:
 * ORG 数据传输对象
 *
 * @Author: Adam Ming
 * @Date: Jul 6, 2020 at 3:08:57 PM
 */
public class OrgDTO extends BaseAbstractDTO {
    private static final long serialVersionUID = 5331164776601766139L;

    private long roleId;
    private String roleName;
    private DTreeVO dTreeVO;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public DTreeVO getDTreeVO() {
        return dTreeVO;
    }

    public void setDTreeVO(DTreeVO dTreeVO) {
        this.dTreeVO = dTreeVO;
    }
}
