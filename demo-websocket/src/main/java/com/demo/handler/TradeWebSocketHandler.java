package com.demo.handler;

import com.demo.modle.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: yinchao
 * @ClassName: TradeWebSocketHandler
 * @Description:
 * @team wuhan operational dev.
 * @date: 2024/4/2 18:17
 */
public class TradeWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    Random r = new Random();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        float oldPrice = 0.0f;

        //Publishing new stock prices every one second for 100 times
        /*for (int i=0; i < 100; i ++){
            //Calculating Random stock price between 12$ to 13$
            float stockPrice = 12 + r.nextFloat() * (13 - 12);
            float roundedPrice = (float) (Math.round(stockPrice * 100.0) / 100.0);

            //Creating a Stock Object
            Stock stock = new Stock("Amazon",
                    "https://cdn.cdnlogo.com/logos/a/77/amazon-dark.svg",
                    roundedPrice);
            //Finding whether the stock pric increased or decreased
            if (roundedPrice > oldPrice){
                stock.setIncreased(true);
            }
            oldPrice = roundedPrice;
            //Sending StockPrice
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(stock));
            session.sendMessage(message);
            Thread.sleep(1000);
        }*/
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        System.out.println("receive msg" + webSocketMessage.getPayload());

        webSocketSession.sendMessage(new TextMessage("收到"));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
