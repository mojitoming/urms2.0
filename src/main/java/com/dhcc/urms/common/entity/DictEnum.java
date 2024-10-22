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
    PRIVI_TYPE_DATA("DATA", "数据"),

    CIS_LEVEL_1("1", "一级"),
    CIS_LEVEL_2("2", "二级"),
    CIS_LEVEL_3("3", "三级")
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

    public static DictEnum getDictEnumByCode(String code) {
        for (DictEnum dictEnum : DictEnum.values()) {
            if (StringUtils.equals(code, dictEnum.getCode())) {
                return dictEnum;
            }
        }
        return null;
    }

    /*
     * Annotation:
     * 状态转换
     *
     * @Author: Adam Ming
     * @Date: Jul 7, 2020 at 11:09:52 AM
     */
    public static String convertStatus(String status) {
        String result;
        if ("on".equals(status)) {
            result = STATUS_ACTIVE.getCode();
        } else {
            result = STATUS_INACTIVE.getCode();
        }

        return result;
    }
}
