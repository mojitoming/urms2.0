package com.dhcc.urms.org.controller;

import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.org.blh.DictOrgBLH;
import com.dhcc.urms.org.dto.OrgDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Annotation:
 * ORG REST
 *
 * @Author: Adam Ming
 * @Date: Jul 6, 2020 at 4:13:14 PM
 */
@RestController
@RequestMapping("/org-api")
public class DictOrgRest {

    @Resource
    private DictOrgBLH orgBLH;

    /*
     * Annotation:
     * 获取机构树
     *
     * @Author: Adam Ming
     * @Date: Jul 6, 2020 at 4:16:11 PM
     */
    @GetMapping("/org-tree")
    public DTreeVO orgTree(OrgDTO dto) {
        orgBLH.OrgTree(dto);

        return dto.getDTreeVO();
    }
}
