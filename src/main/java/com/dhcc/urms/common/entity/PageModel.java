package com.dhcc.urms.common.entity;

import java.io.Serializable;

/**
 * Annotation:
 * 分页Bean
 *
 * @Author: Adam Ming
 * @Date: Jun 18, 2020 at 11:29:02 AM
 */
public class PageModel implements Serializable {
    private static final long serialVersionUID = 3405028335709753631L;

    private int pageNo = 0; // 页码
    private int pageSize = 10; // 一页数量

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
