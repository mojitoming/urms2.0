package com.dhcc.urms.org.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 机构类型表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
@TableName("T_DICT_ORG_TYPE")
public class DictOrgType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构类型ID
     */
    @TableId("ORG_TYPE_CODE")
    private String orgTypeCode;

    /**
     * 机构类型名称
     */
    @TableField("ORG_TYPE_NAME")
    private String orgTypeName;

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
     * 创建时间
     */
    @TableField(value = "CREATE_DATE", fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 创建人
     */
    @TableField(value = "CREATOR", fill = FieldFill.INSERT)
    private String creator;

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
        return "DictOrgType{" +
                   "orgTypeCode=" + orgTypeCode +
                   ", orgTypeName=" + orgTypeName +
                   ", status=" + status +
                   ", odn=" + odn +
                   ", createDate=" + createDate +
                   ", creator=" + creator +
                   "}";
    }
}
