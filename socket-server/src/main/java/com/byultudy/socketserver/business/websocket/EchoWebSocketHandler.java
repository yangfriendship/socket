package com.byultudy.socketserver.business.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@WebSocketEndpoint(endpoints = "/ws/echo")
@Component
public class EchoWebSocketHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        final String payload = message.getPayload();
        session.sendMessage(new TextMessage(payload, true));
    }

}