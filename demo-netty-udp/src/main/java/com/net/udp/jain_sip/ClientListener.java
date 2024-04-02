package com.net.udp.jain_sip;

import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.address.AddressFactory;
import javax.sip.header.HeaderFactory;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

/**
 * @author: yinchao
 * @ClassName: ClientListener
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/1 15:08
 */
public class ClientListener  implements SipListener {
    private AddressFactory addressFactory;

    private HeaderFactory headerFactory;

    private MessageFactory messageFactory;

    private SipProvider sipProvider;

    public ClientListener(AddressFactory addressFactory, HeaderFactory headerFactory, MessageFactory messageFactory,
                          SipProvider sipProvider) {
        super();
        this.addressFactory = addressFactory;
        this.headerFactory = headerFactory;
        this.messageFactory = messageFactory;
        this.sipProvider = sipProvider;
    }

    @Override
    public void processRequest(RequestEvent requestEvent) {
        System.out.println("processRequest执行");
        Request request = requestEvent.getRequest();
        if(null == request) {
            System.out.println("requestEvent.getRequest() is null.");
            return ;
        }

        System.out.println("request内容是\n"+request);
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        System.out.println("processResponse执行");
        Response response = responseEvent.getResponse();
        if(null == response) {
            System.out.println("response is null.");
            return ;
        }
        System.out.println("返回码:"+response.getStatusCode());
        System.out.println("返回的数据 :"+response);
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {

    }

    @Override
    public void processIOException(IOExceptionEvent ioExceptionEvent) {

    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        System.out.println("processTransactionTerminated执行");
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        System.out.println("processDialogTerminated执行");
    }

}
