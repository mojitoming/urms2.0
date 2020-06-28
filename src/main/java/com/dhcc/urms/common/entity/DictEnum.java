package com.dhcc.urms.common.entity;

public enum DictEnum {
    STATUS_INIT("INIT"), // 初始创建
    STATUS_SUBMIT("SUBMIT"), // 启用
    STATUS_ACTIVE("ACTIVE"), // 提交
    STATUS_INACTIVE("INACTIVE"), // 停用
    ;

    private final String value;

    DictEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
