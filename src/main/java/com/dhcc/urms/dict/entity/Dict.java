package com.dhcc.urms.dict.entity;

import java.io.Serializable;

/**
 * Annotation:
 * 字典实体
 *
 * @Author: Adam Ming
 * @Date: Mar 24, 2020 at 3:51:01 PM
 */
public class Dict implements Serializable {
    private static final long serialVersionUID = 6896736854568131571L;

    private String value;
    private String title;
    private String valueChild;
    private String titleChild;

    public Dict() {
    }

    public Dict(String value, String title, String valueChild, String titleChild) {
        this.value = value;
        this.title = title;
        this.valueChild = valueChild;
        this.titleChild = titleChild;
    }

    public Dict(String value, String title) {
        this.value = value;
        this.title = title;
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

    public String getValueChild() {
        return valueChild;
    }

    public void setValueChild(String valueChild) {
        this.valueChild = valueChild;
    }

    public String getTitleChild() {
        return titleChild;
    }

    public void setTitleChild(String titleChild) {
        this.titleChild = titleChild;
    }
}
