package com.dhcc.urms.org.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author Adam Ming
 * @since 2020-07-06
 */
@Controller
@RequestMapping("/org")
public class DictOrgController {
    private final String PREFIX = "org/";

    @RequestMapping(value = {"", "/"})
    public String org() {
        return PREFIX + "org";
    }

    @RequestMapping("/modify")
    public String modify() {
        return PREFIX + "orgModify";
    }

    @RequestMapping("/type/modify")
    public String typeModify() {
        return PREFIX + "orgTypeModify";
    }
}
