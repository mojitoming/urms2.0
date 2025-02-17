package com.dhcc.urms.module.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 模块表 前端控制器
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-23
 */
@Controller
@RequestMapping("/module")
public class ModuleController {
    private final String PREFIX = "module/";

    @RequestMapping(value = {"", "/"})
    public String module() {
        return PREFIX + "module";
    }

    @RequestMapping("/modify")
    public String modify() {
        return PREFIX + "moduleModify";
    }
}
