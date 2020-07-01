package com.dhcc.urms.common.entity;

import java.io.Serializable;

/**
 * Annotation:
 * 继承 BaseAbstractDto 封装公共参数
 *
 * @Author: Adam Ming
 * @Date: May 20, 2020 at 4:33:49 PM
 */
public abstract class BaseAbstractDTO implements Serializable {
    private static final long serialVersionUID = 6218367470505686397L;

    private PageModel pageModel;
    private String msg; // 说明信息
    private String status;

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
