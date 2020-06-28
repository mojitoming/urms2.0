package com.dhcc.urms.common.entity;

import java.io.Serializable;

public class DTreeNodeVO implements Serializable {
    private static final long serialVersionUID = 4258003983501223667L;

    private String id; // 节点ID
    private String title; // 节点名称
    private String parentId = "-1"; // 父节点ID
    private String checkArr = "0"; // 复选框 默认不选中

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }
}
