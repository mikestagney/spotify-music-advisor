package advisor;

import com.sun.net.httpserver.*;

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
                    exchange -> {
                        String query = exchange.getRequestURI().getQuery();
                        String message = "Authorization code not found. Try again.";
                        if (query != null && query.contains("code")) {
                            message = "Got the code. Return back to your program.";
                            queryholder.add(query);
                        }
                        exchange.sendResponseHeaders(200, message.length());
                        exchange.getResponseBody().write(message.getBytes());
                        exchange.getResponseBody().close();
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

        try {
            server.start();
            Thread.sleep(10000);

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
                        break;
                    }
                }
            }

        } catch (InterruptedException e) {
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
            client = HttpClient.newBuilder().build();
            request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .uri(URI.create(accessServer + "/api/token"))
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.body() != null) {
                tokens = response.body();
            }

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

}
