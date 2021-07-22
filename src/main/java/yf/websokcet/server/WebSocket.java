package yf.websokcet.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import yf.websokcet.config.WebSocketConfig;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author Jeff
 * @date 7/20/21 4:51 PM
 */
@ServerEndpoint(value = "/socket")
@Component
@CrossOrigin(origins = "*")
public class WebSocket {
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String username;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.put(String
                .valueOf(onlineCount), this); // 加入set中
        addOnlineCount(); // 在线数加1
        System.out.println("有新连接加入！当前在线人数为 : " + getOnlineCount());
        System.out.println(session.getQueryString());
        try {
            sendMessage("caoni amdeni a");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        System.out.println("有一连接关闭！当前在线人数为 : " + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        System.out.println("来自客户端的消息:" + message);
        try{
            sendMessage("i received your message already!");
        } catch (Exception e) {
            //do something later
        }

    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {

    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }


    public Session getSession() {return this.session; }
}
