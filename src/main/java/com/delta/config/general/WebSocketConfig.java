package com.delta.config.general;

import com.delta.interceptor.CustomHandshakeInterceptor;
import com.delta.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final CustomHandshakeInterceptor customHandshakeInterceptor;

    private final UserInterceptor userInterceptor;

    @Autowired
    public WebSocketConfig(CustomHandshakeInterceptor customHandshakeInterceptor,
        UserInterceptor userInterceptor) {
        this.customHandshakeInterceptor = customHandshakeInterceptor;
        this.userInterceptor = userInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic/", "/queue/");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
//            .setAllowedOriginPatterns("*")
            .setAllowedOrigins("*", "chrome-extension://ggnhohnkfcpcanfekomdkjffnfcjnjam")
            .addInterceptors(this.customHandshakeInterceptor)
            .withSockJS()
        ;
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(this.userInterceptor);
    }

}
