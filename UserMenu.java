package advisor;

import java.util.ArrayList;
import java.util.Scanner;

public class UserMenu<T> {
    Scanner input;
    String userChoice;
    boolean isAuthorized;
    WebConnection web;
    String json;
    NewAlbums albums;
    PlaylistParser featured;
    CategoryArchive categories;
    int numItemsPage = 5;
    ArrayList<T> recommendList;
    int currentPageNum = 1;
    int itemCounter = 0;

    UserMenu() {
        input = new Scanner(System.in);
        isAuthorized = false;
    }

    public void runCLI(String[] args) {
        if (args.length > 1) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-page")) {
                    numItemsPage = Integer.parseInt(args[i + 1]);
                }
            }
        }

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
                //printRecommendations(categories.getList());
                break;
            case("exit"):
                exitApp();
                break;
            case("next"):
                currentPageNum++;
                itemCounter += numItemsPage;
                printRecommendations();
                break;
            case("prev"):
                currentPageNum--;
                itemCounter -= numItemsPage;
                printRecommendations();
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
        resetPagination();
        recommendList = (ArrayList<T>) albums.getList();
        printRecommendations();
    }
    private void featured() {
        json = web.apiRequest("/v1/browse/featured-playlists");
        featured = new PlaylistParser(json);
        //printRecommendations(featured.getList());
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
        //printRecommendations(detailedPlaylist.getList());
    }
    private void resetPagination() {
        currentPageNum = 1;
        itemCounter = 0;
    }


    private void printRecommendations() {
        int totalPages = recommendList.size() / numItemsPage;
        totalPages = recommendList.size() / numItemsPage != 0 ? totalPages++ : totalPages;

        if (currentPageNum > totalPages) {
            System.out.println("No more pages");
            currentPageNum = totalPages;
            itemCounter -= numItemsPage;
            return;
        }
        if (currentPageNum < 1) {
            System.out.println("No more pages");
            currentPageNum = 1;
            itemCounter += numItemsPage;
            return;
        }

        for (int i = itemCounter; i < itemCounter + numItemsPage; i++) {
            System.out.println(recommendList.get(i).toString());
        }
        System.out.printf("---PAGE %d OF %d---\n", currentPageNum , totalPages);

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
