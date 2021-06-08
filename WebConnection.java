package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class WebConnection {

    private HttpServer server;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;
    private String accessServer = "https://accounts.spotify.com";
    private String accessLink;
    String accessToken;
    private String APIServerPath = "https://api.spotify.com";
    private ArrayList<String> queryHolder;
    private String code = "";
    private final String CLIENT_ID = "6a3cee939e094944a5b8c547da47dba2";
    private final String REDIRECT_URI = "http://localhost:8080";
    private final String CLIENT_SECRET = "5e527e1a1542401c9bb8e44cf189cb38";

    WebConnection(String[] args){
        queryHolder = new ArrayList<>();
        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-access")) {
                    accessServer = args[i + 1];
                }
                if (args[i].equals("-resource")) {
                    APIServerPath = args[i + 1];
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
                            queryHolder.add(query);
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
        return accessServer + "/authorize?client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code";
    }

    public boolean getCode() {
        boolean foundCode = false;

        try {
            server.start();
            Thread.sleep(10000);

            String query = "";
            if (!queryHolder.isEmpty()) {
                query = queryHolder.get(0);
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
    public boolean getToken() {
        Boolean gotToken = false;
        String tokens = "no tokens for some reason";

        StringBuilder body = new StringBuilder();
        body.append("grant_type=");
        body.append("authorization_code");
        body.append("&code=");
        body.append(code);
        body.append("&redirect_uri=");
        body.append(REDIRECT_URI);
        body.append("&client_id=");
        body.append(CLIENT_ID);
        body.append("&client_secret=");
        body.append(CLIENT_SECRET);

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
            JsonObject jo = JsonParser.parseString(tokens).getAsJsonObject();
            accessToken = jo.get("access_token").getAsString();
            gotToken = true;

        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return gotToken;
    }

    public String apiRequest() {





        return "";
    }


}
