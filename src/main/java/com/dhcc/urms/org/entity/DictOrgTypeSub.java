package com.dhcc.urms.org.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 机构-机构类型 对应表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
@TableName("T_DICT_ORG_TYPE_SUB")
public class DictOrgTypeSub implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构类型ID
     */
    @TableField("ORG_TYPE_CODE")
    private String orgTypeCode;

    /**
     * 机构ID
     */
    @TableField("ORG_CODE")
    private String orgCode;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    private String creator;

    public String getOrgTypeCode() {
        return orgTypeCode;
    }

    public void setOrgTypeCode(String orgTypeCode) {
        this.orgTypeCode = orgTypeCode;
    }
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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
        return "DictOrgTypeSub{" +
            "orgTypeCode=" + orgTypeCode +
            ", orgCode=" + orgCode +
            ", createDate=" + createDate +
            ", creator=" + creator +
        "}";
    }
}
