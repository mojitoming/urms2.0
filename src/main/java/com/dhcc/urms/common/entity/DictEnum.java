package com.dhcc.urms.common.entity;

import org.thymeleaf.util.StringUtils;

public enum DictEnum {
    STATUS_INIT("INIT", "初始创建"), // 初始创建
    STATUS_SUBMIT("SUBMIT", "提交"), // 启用
    STATUS_ACTIVE("ACTIVE", "启用"), // 提交
    STATUS_INACTIVE("INACTIVE", "停用"), // 停用

    MODULE_TYPE_SYSTEM("SYSTEM", "系统"),
    MODULE_TYPE_PAGE("PAGE", "页面"),
    MODULE_TYPE_FUNCTION("FUNCTION", "功能"),

    PRIVI_TYPE_MODULE("MODULE", "模块"),
    PRIVI_TYPE_DATA("DATA", "数据");
    ;

    private final String code;
    private final String name;

    DictEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DictEnum getDictEnumByCode(String code){
        for(DictEnum dictEnum : DictEnum.values()){
            if(StringUtils.equals(code, dictEnum.getCode())){
                return dictEnum;
            }
        }
        return null;
    }
}
