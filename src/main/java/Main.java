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
    /*HttpServer server = HttpServer.create(new InetSocketAddress( Integer.parseInt(System.getenv("PORT"))), 0);
    server.createContext("/", new MyHandler());
    server.setExecutor(null); // creates a default executor
    server.start();*/

    Configuration config = new Configuration();
    config.setHostname("localhost");
    config.setOrigin("*");
    config.setPort(Integer.parseInt(System.getenv("PORT")));

    final SocketIOServer ioserver = new SocketIOServer(config);
    ioserver.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {
        @Override
        public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
            // broadcast messages to all clients
            ioserver.getBroadcastOperations().sendEvent("chatevent", data);
        }
    });

    ioserver.start();
    /*Thread.sleep(Integer.MAX_VALUE);

    ioserver.stop();*/
  }

  static class MyHandler implements HttpHandler {
      @Override
      public void handle(HttpExchange t) throws IOException {
          String response = "Hello World!";
          t.sendResponseHeaders(200, response.length());
          OutputStream os = t.getResponseBody();
          os.write(response.getBytes());
          os.close();
      }
  }
}