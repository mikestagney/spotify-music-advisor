package advisor;

import java.util.Scanner;

public class UserMenu {
    Scanner input;
    String userChoice;
    boolean isAuthorized;

    UserMenu() {
        input = new Scanner(System.in);
        isAuthorized = false;
    }

    public void runCLI() {
        do {
        userChoice = input.nextLine();
        if (!isAuthorized) {
            System.out.println(getAuthorization());
            continue;
        }
        switch (userChoice) {
            case("new"):
                System.out.println(newReleases());
                break;
            case("featured"):
                System.out.println(featured());
                break;
            case("categories"):
                System.out.println(categories());
                break;
            case("playlists Mood"):
                System.out.println(moodPlaylists());
                break;
            case("exit"):
                exitApp();
                break;
        }
    } while (true);
}
    private String newReleases() {
        return "---NEW RELEASES---\n" +
                "Clockwork Angels [Rush]\n" +
                "Fear Inoculum [Tool]\n" +
                "Apex [Unleash The Archers]\n" +
                "Liquid Tension Experiment 3 [Liquid Tension Experiment]";
    }
    private String featured() {
        return "---FEATURED---\n" +
                "Songs to wake up to\n" +
                "Songs you don't want to wake up to\n" +
                "None of the junk you hear on the radio\n" +
                "Pop (radio junk)";
    }
    private String categories() {
        return "---CATEGORIES---\n" +
                "Rock\n" +
                "Rap\n" +
                "Funk\n" +
                "No Country for old men";
    }
    private String moodPlaylists() {
        return "---MOOD PLAYLISTS---\n" +
                "Workout Time\n" +
                "Before the Game\n" +
                "Love in the Air\n" +
                "Last line of the project";
    }
    private String getAuthorization() {
        String message = "Please, provide access for application.";
        if (userChoice.equals("exit")) {
            exitApp();
        }
        if (userChoice.equals("auth")) {
            isAuthorized = true;
            message = "https://accounts.spotify.com/authorize?client_id=YOURCLIENT&redirect_uri=https://www.example.com&response_type=code\n" +
                    "---SUCCESS---";
        }
        return message;
    }
    private void exitApp() {
        System.out.println("---GOODBYE!---");
        System.exit(0);
    }

}
