package tk.zlx.websocket.socket;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyStringWebSocketHandler extends TextWebSocketHandler {

    public static void main(String[] args) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap();
        map.add("12","fsa");
        map.add("12","a");
        map.add("12","fh");
        map.set("88","jl");
        map.set("88","fa");
        map.set("88","rtgd");
        System.out.println(map.toString());
    }
    /**
     * 和客户端建立连接成功后触发
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("客户端已连接");
        super.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    /**
     * 和客户端建立连接后 处理客户端发送请求
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        String msg = message.getPayload();
        session.sendMessage(new TextMessage(reposeMessage(msg)));
//        session.close(CloseStatus.NORMAL);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    /**
     * 和客户端连接失败触发
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("连接失败");
        super.handleTransportError(session, exception);
    }

    /**
     * 和客户端断开连接触发
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("断开连接");
        super.afterConnectionClosed(session, status);
    }

    @Override
    public boolean supportsPartialMessages() {
        return super.supportsPartialMessages();
    }

    public static String reposeMessage(String input){
        if (input == null || "".equals(input)) {
            return "pardon?";
        }
        return input.trim()
                .replace("你","我")
                .replace("?","!")
                .replace("？","!")
                .replace("吗","");
    }
}
