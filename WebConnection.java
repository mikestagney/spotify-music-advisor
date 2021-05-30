package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WebConnection {

    HttpServer server;
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;

    WebConnection(){
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8080), 0);
            server.createContext("/",
                    new HttpHandler() {
                        @Override
                        public void handle(HttpExchange exchange) throws IOException{
                        String hello = "Hello world";
                        exchange.sendResponseHeaders(200, hello.length());
                        exchange.getResponseBody().write(hello.getBytes());
                        exchange.getResponseBody().close();
                        }
                    }
            );


        } catch (IOException e) {
            e.printStackTrace();
        }


    }



}
