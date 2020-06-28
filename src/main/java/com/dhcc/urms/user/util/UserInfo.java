package com.dhcc.urms.user.util;

import com.dhcc.urms.common.http.RequestContextHolderUtil;
import com.mojitoming.casclient.entity.User;

import javax.servlet.http.HttpSession;

/**
 * Annotation:
 * 用户信息工具类
 *
 * @Author: Adam Ming
 * @Date: Apr 24, 2020 at 11:08:47 AM
 */
public class UserInfo {

    /*
     * 获取用户信息
     */
    public static User getUser() {
        HttpSession session = RequestContextHolderUtil.getRequest().getSession();

        return (User) session.getAttribute("user");
    }
}
