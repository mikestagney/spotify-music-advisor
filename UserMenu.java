package advisor;

import java.util.Scanner;

public class UserMenu {
    Scanner input;
    String userChoice;
    boolean isAuthorized;
    WebConnection web;
    String json;

    UserMenu() {
        input = new Scanner(System.in);
        isAuthorized = false;
    }

    public void runCLI(String[] args) {
        do {
        userChoice = input.nextLine();
        if (!isAuthorized) {
            System.out.println(getAuthorization(args));
            continue;
        }
        switch (userChoice) {
            case("new"):
                json = newReleases();
                NewAlbums albums = new NewAlbums(json);
                break;
            case("featured"):
                json = featured();
                Featured featured = new Featured(json);
                break;
            case("categories"):
                json = categories();
                CategoryArchive cat = new CategoryArchive(json);
                break;
            case("exit"):
                exitApp();
                break;
            default:
                if (userChoice.contains("playlists")) {
                    System.out.println(playlists());
                }
                break;
        }
    } while (true);
}
    private String newReleases() {
        return web.apiRequest("/v1/browse/new-releases");
    }
    private String featured() {
        return web.apiRequest("/v1/browse/featured-playlists");
    }
    private String categories() {
        return web.apiRequest("/v1/browse/categories");
    }
    private String playlists() {


        return userChoice.substring(10);
    }
    private String getAuthorization(String[] args) {
        String message = "Please, provide access for application.";
        if (userChoice.equals("exit")) {
            exitApp();
        }
        if (!userChoice.equals("auth")) return message;

        web = new WebConnection(args);
        System.out.println("use this link to request the access code:");
        System.out.println(web.getAccessLink());
        System.out.println("waiting for code...");
        if (!web.getCode()) {
            return "Authorization code not found. Try again.";
        }
        System.out.println("code received");
        System.out.println("Making http request for access_token...");
        if (web.getToken()) {
            isAuthorized = true;
            message = "Success!";
        }
        return message;
    }
    private void exitApp() {
        //System.out.println("---GOODBYE!---");
        System.exit(0);
    }

}
