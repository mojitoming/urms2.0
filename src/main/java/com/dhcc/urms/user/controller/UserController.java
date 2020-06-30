package com.dhcc.urms.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Adam Ming
 * @since 2020-06-28
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = {"", "/"})
    public String role() {
        return "user/user";
    }

    @RequestMapping("/modify")
    public String modify() {
        return "user/userModify";
    }
}
