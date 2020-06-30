package com.dhcc.urms.index.controller;

import com.dhcc.urms.index.dto.IndexDTO;
import com.mojitoming.casclient.entity.Privilege;
import com.mojitoming.casclient.entity.PrivilegeBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index-api")
public class IndexRest {

    @GetMapping("/privilege/page")
    public IndexDTO getMenus(IndexDTO dto) {
        Privilege thePrivilege = PrivilegeBean.getThePrivilege();

        dto.setPageList(thePrivilege.getPage());

        return dto;
    }
}
