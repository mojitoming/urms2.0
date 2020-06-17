package com.dhcc.urms.role.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController {

    @RequestMapping(value = {"", "/"})
    public String role() {
        return "role/role";
    }
}
