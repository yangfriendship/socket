package com.byultudy.socketserver.infra.config;

import com.byultudy.socketserver.business.websocket.WebSocketEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.Map;

@EnableWebSocket
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final Map<String, WebSocketHandler> webSocketHandlers;

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        for (final WebSocketHandler webSocketHandler : webSocketHandlers.values()) {
            final WebSocketEndpoint webSocketEndpoint = AnnotationUtils.findAnnotation(
                    webSocketHandler.getClass(), WebSocketEndpoint.class);
            if (dontHaveEndpoints(webSocketEndpoint)) {
                continue;
            }
            registry.addHandler(webSocketHandler, webSocketEndpoint.endpoints())
                    .setAllowedOrigins("*");
        }
    }

    private boolean dontHaveEndpoints(final WebSocketEndpoint webSocketEndpoint) {
        return webSocketEndpoint == null
                || webSocketEndpoint.endpoints() == null
                || webSocketEndpoint.endpoints().length == 0;
    }


}
