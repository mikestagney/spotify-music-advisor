package advisor;

import java.util.Scanner;

public class UserMenu {
    Scanner input;
    String userChoice;
    boolean isAuthorized;
    WebConnection web;
    String json;
    NewAlbums albums;
    PlaylistParser featured;
    CategoryArchive categories;

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
        if (categories == null) {
            categories();
        }
        switch (userChoice) {
            case("new"):
                newReleases();
                break;
            case("featured"):
                featured();
                break;
            case("categories"):
                System.out.println(categories.toString());
                break;
            case("exit"):
                exitApp();
                break;
            default:
                if (userChoice.contains("playlists")) {
                    playlists();
                }
                break;
        }
    } while (true);
}
    private void newReleases() {
        json = web.apiRequest("/v1/browse/new-releases");
        albums = new NewAlbums(json);
    }
    private void featured() {
        json = web.apiRequest("/v1/browse/featured-playlists");
        featured = new PlaylistParser(json);
    }
    private void categories() {
        json = web.apiRequest("/v1/browse/categories");
        categories = new CategoryArchive(json);
    }
    private void playlists() {
        String playlist = userChoice.substring(10);
        String categoryID = categories.getCategoryID(playlist);
        if (categoryID.equals("")) {
            System.out.println("Unknown category name.");
            return;
        }
        json = web.apiRequest("/v1/browse/categories/" + categoryID + "/playlists");
        if (json.contains("error")) {
            System.out.println(web.parseError(json));
            return;
        }
        PlaylistParser detailedPlaylist = new PlaylistParser(json);

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
        System.exit(0);
    }

}
