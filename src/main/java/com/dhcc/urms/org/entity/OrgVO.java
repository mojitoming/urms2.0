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
