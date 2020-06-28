package com.dhcc.urms.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Annotation:
 * 兼容 Layui 的返回结果，重写一个 Page
 *
 * @Author: Adam Ming
 * @Date: Jul 4, 2019 at 6:10:26 PM
 */
public class MyPage implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code = 0;
    private String msg = "success";
    private long count;
    private List data;

    public MyPage() {}

    public MyPage(long count, List data) {
        this.count = count;
        this.data = data;
    }

    public MyPage(int code, String msg, long count, List data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
