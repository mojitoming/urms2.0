package com.dhcc.urms.module.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 模块表
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-23
 */
@TableName("T_MODULE")
@KeySequence("SEQ_URMS")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模块ID，主键
     */
    @TableId(value = "MODULE_ID", type = IdType.INPUT)
    private Long moduleId;

    /**
     * 模块名称
     */
    @TableField("MODULE_NAME")
    private String moduleName;

    /**
     * 模块类型
     */
    @TableField("MODULE_TYPE")
    private String moduleType;

    /**
     * 模块URL
     */
    @TableField("MODULE_ACTION")
    private String moduleAction;

    /**
     * 父ID
     */
    @TableField("PARENT_ID")
    private Long parentId;

    /**
     * 顺序
     */
    @TableField(value = "ODN", fill = FieldFill.INSERT)
    private Long odn;

    /**
     * 模块图标
     */
    @TableField("MODULE_ICON")
    private String moduleIcon;

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

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getModuleAction() {
        return moduleAction;
    }

    public void setModuleAction(String moduleAction) {
        this.moduleAction = moduleAction;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getOdn() {
        return odn;
    }

    public void setOdn(Long odn) {
        this.odn = odn;
    }

    public String getModuleIcon() {
        return moduleIcon;
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon;
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
        return "Module{" +
                   "moduleId=" + moduleId +
                   ", moduleName=" + moduleName +
                   ", moduleType=" + moduleType +
                   ", moduleAction=" + moduleAction +
                   ", parentId=" + parentId +
                   ", odn=" + odn +
                   ", moduleIcon=" + moduleIcon +
                   ", status=" + status +
                   ", createDate=" + createDate +
                   ", creator=" + creator +
                   "}";
    }
}
