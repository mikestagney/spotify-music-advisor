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

public class WebConnection {

    HttpServer server;
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;
    String accessServer = "https://accounts.spotify.com";
    String accessLink;

    WebConnection(String[] args){
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
                        }
                    }
            );
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public String getAccessLink() {
        return accessServer
        + "/authorize?client_id=6a3cee939e094944a5b8c547da47dba2&redirect_uri=http://localhost:8080&response_type=code";
        //+ "waiting for code...";
    }
    public String getCode() {
        try {
            HttpClient client = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String holdQuery;
            HttpHandler handler = new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    final String query = exchange.getRequestURI().getQuery();
                }
            };



            Thread.sleep(10000);
            /*
            while (response.body().isEmpty()) {
                Thread.sleep(10);
            }*/
            //server.stop(10);
            return response.body();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        return "error";
    }


}
