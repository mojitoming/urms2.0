package com.dhcc.urms.module.entity;

import java.io.Serializable;

/**
 * Annotation:
 * Module Value Object
 *
 * @Author: Adam Ming
 * @Date: Jul 2, 2020 at 8:02:50 PM
 */
public class ModuleVO implements Serializable {
    private static final long serialVersionUID = 4554116568743382770L;

    private String statusName;
    private String moduleTypeName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getModuleTypeName() {
        return moduleTypeName;
    }

    public void setModuleTypeName(String moduleTypeName) {
        this.moduleTypeName = moduleTypeName;
    }
}
