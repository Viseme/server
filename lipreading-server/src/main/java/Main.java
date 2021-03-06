import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.InputStream;

import com.corundumstudio.socketio.listener.*;
import com.corundumstudio.socketio.*;

public class Main {

  public static void main(String[] args) throws Exception {
    Configuration config = new Configuration();
    config.setHostname("0.0.0.0");
    config.setOrigin("http://localhost");
    int port = System.getenv("PORT") != null ? 
    		Integer.parseInt(System.getenv("PORT")) : 63342;
    config.setPort(port);

    final SocketIOServer ioserver = new SocketIOServer(config);
    ioserver.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
        @Override
        public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
            // broadcast messages to all clients
            ioserver.getBroadcastOperations().sendEvent("chatevent", data);
        }
    });

    ioserver.addEventListener("ping", PingObject.class, new DataListener<PingObject>() {
        @Override
        public void onData(SocketIOClient client, PingObject data, AckRequest ackRequest) {
            System.out.println("Ping: " + data.getMessage());
            client.sendEvent("ping", new PingObject());
        }
    });

    ioserver.addEventListener("frame", FrameObject.class, new DataListener<FrameObject>() {
        @Override
        public void onData(SocketIOClient client, FrameObject data, AckRequest ackRequest) {
            System.out.println("Data: " + data.getFrameData());
        }
    });

    ioserver.start();
  }
}