package advisor;

import java.util.Scanner;

public class UserMenu {
    Scanner input;
    boolean keepGoing;
    String userChoice;
    boolean isAuthorized;

    UserMenu() {
        input = new Scanner(System.in);
        keepGoing = true;
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
                System.out.println("---GOODBYE!---");
                keepGoing = false;
                break;
        }
    } while (keepGoing);
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
        if (userChoice.equals("auth")) {
            isAuthorized = true;
            message = "url to Oath authorization\n" +
                    "---SUCCESS---";
        }
        return message;
    }

}
