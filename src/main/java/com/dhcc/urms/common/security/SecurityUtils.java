package com.dhcc.urms.common.security;

import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Annotation:
 * 安全工具
 *
 * @Author: Adam Ming
 * @Date: Jun 28, 2020 at 4:20:24 PM
 */
public class SecurityUtils {

    /*
     * Annotation:
     * MD5 加密
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 4:20:03 PM
     */
    private static String md5(String rawStr) {
        return DigestUtils.md5DigestAsHex(rawStr.getBytes());
    }

    /*
     * Annotation:
     * 生成盐：时间毫秒 + 密码 + 密码
     * 并编码
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 4:19:05 PM
     */
    public static String getSalt(String password) {
        Long timeStamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();

        return Base64Utils.encodeToString((timeStamp + password + password).getBytes());
    }

    /*
     * Annotation:
     * 密码加密：盐 + 密码
     *
     * @Author: Adam Ming
     * @Date: Jun 28, 2020 at 4:19:42 PM
     */
    public static String encryptPassword(String salt, String password) {
        return md5(salt + password);
    }
}
