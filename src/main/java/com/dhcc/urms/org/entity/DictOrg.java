package com.dhcc.urms.org.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

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
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 顺序号
     */
    @TableField("ODN")
    private BigDecimal odn;

    /**
     * 创建日期
     */
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /**
     * 创建人
     */
    @TableField("CREATOR")
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public BigDecimal getOdn() {
        return odn;
    }

    public void setOdn(BigDecimal odn) {
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
            ", status=" + status +
            ", odn=" + odn +
            ", createDate=" + createDate +
            ", creator=" + creator +
        "}";
    }
}
