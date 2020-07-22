package com.dhcc.urms.org.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 机构表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
@TableName("T_DICT_ORG")
public class DictOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构ID
     */
    @TableId("ORG_CODE")
    private String orgCode;

    /**
     * 机构名称
     */
    @TableField("ORG_NAME")
    private String orgName;

    /**
     * 医保结算等级
     */
    @TableField("CIS_LEVEL")
    private Integer cisLevel;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 顺序号
     */
    @TableField(value = "ODN", fill = FieldFill.INSERT)
    private Long odn;

    /**
     * 创建日期
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 创建人
     */
    @TableField(value = "CREATOR", fill = FieldFill.INSERT)
    private String creator;

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

    public Integer getCisLevel() {
        return cisLevel;
    }

    public void setCisLevel(Integer cisLevel) {
        this.cisLevel = cisLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOdn() {
        return odn;
    }

    public void setOdn(Long odn) {
        this.odn = odn;
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
        return "DictOrg{" +
                   "orgCode=" + orgCode +
                   ", orgName=" + orgName +
                   ", cisLevel=" + cisLevel +
                   ", status=" + status +
                   ", odn=" + odn +
                   ", createDate=" + createDate +
                   ", creator=" + creator +
                   "}";
    }
}
