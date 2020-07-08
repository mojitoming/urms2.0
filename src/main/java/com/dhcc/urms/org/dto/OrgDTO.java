package com.dhcc.urms.org.dto;

import com.dhcc.urms.common.entity.BaseAbstractDTO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.org.entity.DictOrg;
import com.dhcc.urms.org.entity.DictOrgType;
import com.dhcc.urms.org.entity.DictOrgTypeSub;
import com.dhcc.urms.org.entity.OrgVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Annotation:
 * ORG 数据传输对象
 *
 * @Author: Adam Ming
 * @Date: Jul 6, 2020 at 3:08:57 PM
 */
@Component
public class OrgDTO extends BaseAbstractDTO {
    private static final long serialVersionUID = 5331164776601766139L;

    private long roleId;
    private String roleName;
    private DTreeVO dTreeVO;
    private boolean allTree = false;
    private String orgCode; // 机构代码
    private List<String> orgCodeList;
    private String orgTypeCode; // 机构类型代码
    private List<String> orgTypeCodeList;
    private List<DictOrg> orgList;
    private DictOrg org;
    private DictOrgType orgType;
    private List<DictOrgType> orgTypeList;
    private DictOrgTypeSub orgTypeSub;
    private List<DictOrgTypeSub> orgTypeSubList;
    private OrgVO orgVO;
    private List<OrgVO> orgVOList;

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

    public boolean isAllTree() {
        return allTree;
    }

    public void setAllTree(boolean allTree) {
        this.allTree = allTree;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public List<String> getOrgTypeCodeList() {
        return orgTypeCodeList;
    }

    public void setOrgTypeCodeList(List<String> orgTypeCodeList) {
        this.orgTypeCodeList = orgTypeCodeList;
    }

    public List<DictOrg> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<DictOrg> orgList) {
        this.orgList = orgList;
    }

    public DictOrg getOrg() {
        return org;
    }

    public void setOrg(DictOrg org) {
        this.org = org;
    }

    public DictOrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(DictOrgType orgType) {
        this.orgType = orgType;
    }

    public List<DictOrgType> getOrgTypeList() {
        return orgTypeList;
    }

    public void setOrgTypeList(List<DictOrgType> orgTypeList) {
        this.orgTypeList = orgTypeList;
    }

    public DictOrgTypeSub getOrgTypeSub() {
        return orgTypeSub;
    }

    public void setOrgTypeSub(DictOrgTypeSub orgTypeSub) {
        this.orgTypeSub = orgTypeSub;
    }

    public List<DictOrgTypeSub> getOrgTypeSubList() {
        return orgTypeSubList;
    }

    public void setOrgTypeSubList(List<DictOrgTypeSub> orgTypeSubList) {
        this.orgTypeSubList = orgTypeSubList;
    }

    public OrgVO getOrgVO() {
        return orgVO;
    }

    public void setOrgVO(OrgVO orgVO) {
        this.orgVO = orgVO;
    }

    public List<OrgVO> getOrgVOList() {
        return orgVOList;
    }

    public void setOrgVOList(List<OrgVO> orgVOList) {
        this.orgVOList = orgVOList;
    }
}
