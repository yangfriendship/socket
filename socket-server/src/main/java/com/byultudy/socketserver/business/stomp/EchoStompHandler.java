package com.byultudy.socketserver.business.stomp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EchoStompHandler {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/echo")     // /pub/echo
    @SendTo("/sub/echo")
    public String handleEcho(byte[] payload) {
        if (payload == null || payload.length == 0) {
            return "...";
        }
        return new String(payload) + "~!!!";
    }

}
