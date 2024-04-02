package com.net.udp.jain_sip;

import javax.sip.ListeningPoint;
import javax.sip.SipFactory;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author: yinchao
 * @ClassName: MySipTest
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 15:07
 */
public class MySipTest {
    public static void main(String[] args) {
        String username = "local";
        String ip = "192.168.0.92";
        int port = 5060;

        try {
            Properties prop = new Properties();
            prop.setProperty("javax.sip.STACK_NAME", "100");
            prop.setProperty("javax.sip.IP_ADDRESS", ip);
            prop.setProperty("javax.sip.OUTBOUND_PROXY", ip + ":" + port + "/udp");
            prop.setProperty("javax.sip.HEADER_EXTENSIONS", "Supported");

            SipFactory sipFactory = SipFactory.getInstance();
            //sipFactory.setPathName("gov.nist");

            SipStack sipStack = sipFactory.createSipStack(prop);
            HeaderFactory headerFactory = sipFactory.createHeaderFactory();
            AddressFactory addressFactory = sipFactory.createAddressFactory();
            MessageFactory messageFactory = sipFactory.createMessageFactory();
            ListeningPoint listeningPoint_udp = sipStack.createListeningPoint(5061, "udp");
            SipProvider sipProvider = sipStack.createSipProvider(listeningPoint_udp);
            ClientListener listener = new ClientListener(addressFactory, headerFactory, messageFactory, sipProvider);
            sipProvider.addSipListener(listener);
            //requestURI
            SipURI requestSipURI = addressFactory.createSipURI(username, ip + ":" + port);
            requestSipURI.setTransportParam("udp");
            SipURI localUrl = addressFactory.createSipURI(username, ip + ":" + port);
            SipURI serverUrl = addressFactory.createSipURI(username, ip + ":" + port);
            //from
            FromHeader fromHeader = headerFactory.createFromHeader(addressFactory.createAddress(localUrl), "caller");
            //to
            ToHeader toHeader = headerFactory.createToHeader(addressFactory.createAddress(serverUrl), null);
            //via
            ViaHeader viaHeader = headerFactory.createViaHeader(ip, port, "udp", "branchingbranching");
            //List
            List<ViaHeader> viaHeaderList = new ArrayList<ViaHeader>();
            viaHeaderList.add(viaHeader);
            //callid,cseq,maxforwards
            CallIdHeader callIdHeader = sipProvider.getNewCallId();
            CSeqHeader cSeqHeader = headerFactory.createCSeqHeader(1L, Request.INVITE);
            MaxForwardsHeader maxForwardsHeader = headerFactory.createMaxForwardsHeader(70);
            Request request = messageFactory.createRequest(requestSipURI, Request.INVITE, callIdHeader, cSeqHeader,
                    fromHeader, toHeader, viaHeaderList, maxForwardsHeader);
            //contact
            SipURI contactURI = addressFactory.createSipURI("100", ip + ":" + port);
            contactURI.setPort(port);
            Address contactAddress = addressFactory.createAddress(contactURI);
            contactAddress.setDisplayName("100");
            ContactHeader contactHeader = headerFactory.createContactHeader(contactAddress);
            request.addHeader(contactHeader);
            // expires
            ExpiresHeader expiresHeader = headerFactory.createExpiresHeader(3600);
            request.addHeader(expiresHeader);
            sipProvider.sendRequest(request);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
