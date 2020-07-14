package com.dhcc.urms.org.entity;

import java.io.Serializable;

/**
 * Annotation:
 * ORG Value Object
 *
 * @Author: Adam Ming
 * @Date: Jul 6, 2020 at 3:30:04 PM
 */
public class OrgVO implements Serializable {
    private static final long serialVersionUID = -425605940452696685L;

    private String orgCode;
    private String orgName;
    private String cisLevel;
    private String cisLevelName;
    private String status;
    private String statusName;
    private String orgTypeCode;
    private String orgTypeName;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCisLevel() {
        return cisLevel;
    }

    public void setCisLevel(String cisLevel) {
        this.cisLevel = cisLevel;
    }

    public String getCisLevelName() {
        return cisLevelName;
    }

    public void setCisLevelName(String cisLevelName) {
        this.cisLevelName = cisLevelName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }

    public String getOrgTypeName() {
        return orgTypeName;
    }

    public void setOrgTypeName(String orgTypeName) {
        this.orgTypeName = orgTypeName;
    }
}
