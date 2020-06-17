package com.dhcc.urms.index.dto;

import com.mojitoming.casclient.entity.Module;

import java.util.List;

public class IndexDTO {
    private List<Module> pageList;

    public List<Module> getPageList() {
        return pageList;
    }

    public void setPageList(List<Module> pageList) {
        this.pageList = pageList;
    }
}
