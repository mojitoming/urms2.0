package com.dhcc.urms.index.controller;

import com.mojitoming.casclient.config.SpringCasProperties;
import com.mojitoming.casclient.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Annotation:
 * 入口地址
 *
 * @Author: Adam Ming
 * @Date: Jul 3, 2019 at 2:40:57 PM
 */
@Controller
public class IndexController {

    @Resource
    private SpringCasProperties casProperties;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @RequestMapping(value = {"/", "/index"})//新版首页
    public String index() {
        return "index/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);

        String logoutUrl = casProperties.getCasServerUrlPrefix() + "logout";
        String whereToGo = casProperties.getServerName() + contextPath;

        return "redirect:" + logoutUrl + "?service=" + whereToGo;
    }
}
