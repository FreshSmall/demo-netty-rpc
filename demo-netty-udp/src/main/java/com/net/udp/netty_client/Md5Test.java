package com.net.udp.netty_client;

/**
 * @author: yinchao
 * @ClassName: Md5Test
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 15:26
 */
public class Md5Test {

    public static void main(String[] args) {
        String ha1 = Md5Util.md5("34020000001320000002" + ":" + "3402000000" + ":" + "admin123", "");  //HA1=MD5(username:realm:passwd)
//        String ha2 = md5("REGISTER" + ":" + "sip:34020000002000000001@172.24.20.26:5060", ""); //HA2=MD5(Method:Uri)
        String ha2 = Md5Util.md5("REGISTER" + ":" + "sip:34020000002000000001@127.0.0.1:5060", ""); //HA2=MD5(Method:Uri)

        String response = ha1 + ":" + "326d59f91b6e448fa461fcacd9161abe" + ":" + ha2; // response = MD5(HA1:nonce:HA2)
        System.out.println("MD5加密后的字符串为:encodeStr="+Md5Util.md5(response, ""));
    }
}
