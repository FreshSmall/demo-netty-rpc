package com.net.udp.netty_client;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author: yinchao
 * @ClassName: Md5Util
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 15:27
 */
public class Md5Util {
    /**
     * MD5方法
     *
     * @param text 明文
     * @param key  密钥
     * @return 密文
     */
    public static String md5(String text, String key) {
        //加密后的字符串
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key  密钥
     * @param md5  密文
     * @return true/false
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if (md5Text.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }
}
