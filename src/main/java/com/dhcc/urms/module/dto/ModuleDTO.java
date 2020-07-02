package com.dhcc.urms.module.dto;

import com.dhcc.urms.common.entity.BaseAbstractDTO;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.module.entity.Module;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Annotation:
 * 模块
 * 系统、页面
 *
 * @Author: Adam Ming
 * @Date: Jun 23, 2020 at 11:17:11 AM
 */
@Component
public class ModuleDTO extends BaseAbstractDTO {
    private static final long serialVersionUID = 5431706795162868260L;

    private DTreeVO dTreeVO;
    private long roleId; // 角色ID
    private String roleName; // 角色名称
    private long moduleId; // 模块ID
    private List<Module> moduleList; // 模块对象 List
    private Module module; // 模块对象

    public DTreeVO getDTreeVO() {
        return dTreeVO;
    }

    public void setDTreeVO(DTreeVO dTreeVO) {
        this.dTreeVO = dTreeVO;
    }

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

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
