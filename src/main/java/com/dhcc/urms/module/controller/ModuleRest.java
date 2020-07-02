package com.dhcc.urms.module.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhcc.urms.common.entity.DTreeVO;
import com.dhcc.urms.module.blh.ModuleBLH;
import com.dhcc.urms.module.dto.ModuleDTO;
import com.dhcc.urms.module.entity.Module;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/module-api")
public class ModuleRest {

    @Resource
    private ModuleBLH moduleBLH;

    @Resource
    private ModuleDTO moduleDTO;

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

    /*
     * Annotation:
     * 获取 module
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 4:01:39 PM
     */
    @GetMapping("/module")
    public ModuleDTO findModule(ModuleDTO dto) {
        moduleBLH.findModule(dto);

        return dto;
    }

    /*
     * Annotation:
     * 新建模块
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 5:17:18 PM
     */
    @PostMapping("/module/modify")
    public ModuleDTO addModule(@RequestBody JSONObject jsonObject) {
        Module module = JSON.toJavaObject(jsonObject, Module.class);

        moduleDTO.setModule(module);

        moduleBLH.addModule(moduleDTO);

        return moduleDTO;
    }

    /*
     * Annotation:
     * 修改模块
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 5:17:18 PM
     */
    @PutMapping("/module/modify")
    public ModuleDTO updateModule(@RequestBody JSONObject jsonObject) {
        Module module = JSON.toJavaObject(jsonObject, Module.class);

        moduleDTO.setModule(module);

        moduleBLH.updateModule(moduleDTO);

        return moduleDTO;
    }

    /*
     * Annotation:
     * 删除模块
     *
     * @Author: Adam Ming
     * @Date: Jul 2, 2020 at 5:17:18 PM
     */
    @DeleteMapping("/module/modify")
    public ModuleDTO deleteModule(@RequestBody JSONObject jsonObject) {
        Module module = JSON.toJavaObject(jsonObject, Module.class);

        moduleDTO.setModule(module);

        moduleBLH.deleteModule(moduleDTO);

        return moduleDTO;
    }
}
