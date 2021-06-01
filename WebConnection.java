package advisor;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class WebConnection {

    HttpServer server;
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;
    String accessServer = "https://accounts.spotify.com";
    String accessLink;
    ArrayList<String> queryholder;
    String code = "";

    WebConnection(String[] args){
        queryholder = new ArrayList<>();
        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-access")) {
                    accessServer = args[i + 1];
                }
            }
        }
        accessLink = getAccessLink();

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
                        String query = exchange.getRequestURI().getQuery();
                        if (query != null) {
                            queryholder.add(query);
                        }
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAccessLink() {
        return accessServer
        + "/authorize?client_id=6a3cee939e094944a5b8c547da47dba2&redirect_uri=http://localhost:8080&response_type=code";
    }

    public boolean getCode() {
        boolean foundCode = false;
        String message = "Authorization code not found. Try again.";
        try {
            server.start();
            //Thread.sleep(1);  // was 10000

            String query = "";
            if (!queryholder.isEmpty()) {
                query = queryholder.get(0);
            }
            if(query.contains("code")) {
                String[] holder = query.split("=");

                for (int i = 0; i < holder.length; i++) {
                    if (holder[i].equals("code")) {
                        code = holder[i + 1];
                        foundCode = true;
                        message = "Got the code. Return back to your program.";
                        break;
                    }
                }
            }
            client = HttpClient.newBuilder().build();
            // need to figure out how to send this message to the browser
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080"))
                    //.GET(HttpRequest.BodyPublishers.ofString(message))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {  // InterruptedException e  for thread.sleep
            e.printStackTrace();

        } finally {
            server.stop(1);
        }
        return foundCode;
    }
    public String getToken() {
        String tokens = "no tokens for some reason";
        StringBuilder body = new StringBuilder();
        body.append("grant_type=");
        body.append("authorization_code");
        body.append("&code=");
        body.append(code);
        body.append("&redirect_uri=http://localhost:8080");
        body.append("&client_id=");
        body.append("6a3cee939e094944a5b8c547da47dba2");
        body.append("&client_secret=");
        body.append("5e527e1a1542401c9bb8e44cf189cb38");

        try {
            //client = HttpClient.newBuilder().build();
            request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            tokens = response.body();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

}
