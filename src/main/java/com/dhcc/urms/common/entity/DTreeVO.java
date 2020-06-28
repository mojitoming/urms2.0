package com.dhcc.urms.common.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Annotation:
 * dtree 组件对象
 *
 * @Author: Adam Ming
 * @Date: Jun 1, 2020 at 4:37:27 PM
 */
public class DTreeVO implements Serializable {
    private static final long serialVersionUID = 4258003983501223667L;

    private Status status;
    private List<DTreeNodeVO> data;

    public DTreeVO() {
        status = new Status();
        data = new ArrayList<>();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<DTreeNodeVO> getData() {
        return data;
    }

    public void setData(List<DTreeNodeVO> data) {
        this.data = data;
    }
}

class Status {
    int code = 200;
    String message = "操作成功";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
