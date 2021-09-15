package tk.zlx.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import tk.zlx.websocket.socket.MyStringWebSocketHandler;

@Configuration
@EnableWebSocket
public class SocketConfig implements WebSocketConfigurer {

    @Autowired
    private MyStringWebSocketHandler myStringWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myStringWebSocketHandler,"/socket").withSockJS();
    }
}
