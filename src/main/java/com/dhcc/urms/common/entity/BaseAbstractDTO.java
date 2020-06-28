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

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }
}
