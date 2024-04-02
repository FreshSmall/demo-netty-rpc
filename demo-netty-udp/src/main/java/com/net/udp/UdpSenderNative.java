package com.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * @author: yinchao
 * @ClassName: UdpSenderNative
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/3/28 11:09
 */
public class UdpSenderNative {


    public static void main(String[] args) {

        try {
            String hostname = "phone1.icsoc.net";
            int port = 5091;
            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
//            while (true) {
                /*String msg = "REGISTER sip:phone1.icsoc.net:5091 SIP/2.0\r\n" +
                        "Via: SIP/2.0/UDP 192.168.1.9:52770;rport;branch=z9hG4bKiHwSB5wtZ\r\n" +
                        "Max-Forwards: 70\r\n" +
                        "To: <sip:ss2003510ss5003@phone1.icsoc.net:5091>\r\n" +
                        "From: <sip:ss2003510ss5003@phone1.icsoc.net:5091>;tag=7thG0bNI\r\n" +
                        "Call-ID: xfMPVYgc-1711638279511@yinchaodeMacBook-Pro-2.local\r\n" +
                        "CSeq: 1 REGISTER\r\n" +
                        "Expires: 3600\r\n" +
                        "Contact: <sip:ss2003510ss5003@192.168.1.9:52770;transport=UDP\r\n"+
                        "\r\n";*/
            String msg = "REGISTER sip:phone1.icsoc.net:5091 SIP/2.0\r\n" +
                    "Via: SIP/2.0/UDP 192.168.1.9:65112;rport;branch=z9hG4bKIyMVxvwTu\r\n" +
                    "Max-Forwards: 70\r\n" +
                    "To: <sip:ss2003510ss5005@phone1.icsoc.net:5091>\r\n" +
                    "From: <sip:ss2003510ss5005@phone1.icsoc.net:5091>;tag=PXzOHl0O\r\n" +
                    "Call-ID: hWSEAiZH-1711641361726@yinchaodeMacBook-Pro-2.local\r\n" +
                    "CSeq: 6 REGISTER\r\n" +
                    "Expires: 3600\r\n" +
                    "Contact: <sip:ss2003510ss5005@192.168.1.9:65112;transport=UDP>\r\n" +
                    "Authorization: Digest username=\"ss2003510ss5005\", realm=\"phone1.icsoc.net\", nonce=\"ZgWUPWYFkxEKMwqj8ZpyS0qdUGCHp9J0\", uri=\"sip:phone1.icsoc.net:5091\", response=\"ee85f46cd818191e852ef761d8fd4d9f\"\r\n";
            byte[] send = msg.getBytes();
            DatagramPacket request = new DatagramPacket(send, send.length, address, port);
            socket.send(request);
            System.out.println("Sent: " + msg);

            //接收服务器反馈数据
            byte[] backbuf = new byte[1024];
            DatagramPacket backPacket = new DatagramPacket(backbuf, backbuf.length);
            socket.receive(backPacket);  //接收返回数据
            String backMsg = new String(backbuf, 0, backPacket.getLength());
            System.out.println("服务器返回的数据为:" + backMsg);
            msg = "REGISTER sip:phone1.icsoc.net:5091 SIP/2.0\r\n" +
                    "Via: SIP/2.0/UDP 192.168.1.9:65112;rport;branch=z9hG4bKIyMVxvwTu\r\n" +
                    "Max-Forwards: 70\r\n" +
                    "To: <sip:ss2003510ss5005@phone1.icsoc.net:5091>\r\n" +
                    "From: <sip:ss2003510ss5005@phone1.icsoc.net:5091>;tag=PXzOHl0O\r\n" +
                    "Call-ID: hWSEAiZH-1711641361726@yinchaodeMacBook-Pro-2.local\r\n" +
                    "CSeq: 6 REGISTER\r\n" +
                    "Expires: 3600\r\n" +
                    "Contact: <sip:ss2003510ss5005@192.168.1.9:65112;transport=UDP>\r\n" +
                    "Authorization: Digest username=\"ss2003510ss5005\", realm=\"phone1.icsoc.net\", nonce=\"ZgWUPWYFkxEKMwqj8ZpyS0qdUGCHp9J0\", uri=\"sip:phone1.icsoc.net:5091\", response=\"ee85f46cd818191e852ef761d8fd4d9f\"\r\n" +
                    "\r\n";
            send = msg.getBytes();
            request = new DatagramPacket(send, send.length, address, port);
            socket.send(request);
            System.out.println("Sent: " + msg);
            //}

        } catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
