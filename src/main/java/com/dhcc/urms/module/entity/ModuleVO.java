package com.dhcc.urms.module.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Annotation:
 * Module Value Object
 *
 * @Author: Adam Ming
 * @Date: Jul 2, 2020 at 8:02:50 PM
 */
public class ModuleVO implements Serializable {
    private static final long serialVersionUID = 4554116568743382770L;

    /**
     * 模块ID，主键
     */
    private Long moduleId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块类型
     */
    private String moduleType;
    private String moduleTypeName;

    /**
     * 模块URL
     */
    private String moduleAction;

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 顺序
     */
    private Long odn;

    /**
     * 模块图标
     */
    private String moduleIcon;

    /**
     * 状态
     */
    private String status;
    private String statusName;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 创建人
     */
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

    public String getModuleTypeName() {
        return moduleTypeName;
    }

    public void setModuleTypeName(String moduleTypeName) {
        this.moduleTypeName = moduleTypeName;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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
        return "ModuleVO{" +
                   "moduleId=" + moduleId +
                   ", moduleName='" + moduleName + '\'' +
                   ", moduleType='" + moduleType + '\'' +
                   ", moduleTypeName='" + moduleTypeName + '\'' +
                   ", moduleAction='" + moduleAction + '\'' +
                   ", parentId=" + parentId +
                   ", odn=" + odn +
                   ", moduleIcon='" + moduleIcon + '\'' +
                   ", status='" + status + '\'' +
                   ", statusName='" + statusName + '\'' +
                   ", createDate=" + createDate +
                   ", creator='" + creator + '\'' +
                   '}';
    }
}
