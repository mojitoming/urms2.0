package com.dhcc.urms.module.controller;

import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.module.blh.ModuleBLH;
import com.dhcc.urms.module.dto.ModuleDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/module-api")
public class ModuleRest {

    @Resource
    private ModuleBLH moduleBLH;

    /*
     * Annotation:
     * 模块树
     *
     * @Author: Adam Ming
     * @Date: Jun 23, 2020 at 11:19:41 AM
     */
    @GetMapping("/module-tree")
    public DTreeVO moduleTree(ModuleDTO dto) {
        moduleBLH.moduleTree(dto);

        return dto.getDTreeVO();
    }
}
