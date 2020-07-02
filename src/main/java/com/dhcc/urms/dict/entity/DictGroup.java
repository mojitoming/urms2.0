package com.dhcc.urms.dict.entity;

import java.util.List;

public class DictGroup {
    private String value;
    private String title;
    private List<Dict> childList;

    public DictGroup() {
    }

    public DictGroup(String value, String title, List<Dict> childList) {
        this.value = value;
        this.title = title;
        this.childList = childList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Dict> getChildList() {
        return childList;
    }

    public void setChildList(List<Dict> childList) {
        this.childList = childList;
    }
}
