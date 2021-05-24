package advisor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean keepGoing = true;
        String userChoice;

        do {
            userChoice = input.nextLine();
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
    public static String newReleases() {
        return "---NEW RELEASES---\n" +
                        "Clockwork Angels [Rush]\n" +
                        "Fear Inoculum [Tool]\n" +
                        "Apex [Unleash The Archers]\n" +
                        "Liquid Tension Experiment 3 [Liquid Tension Experiment]";
    }
    public static String featured() {
        return "---FEATURED---\n" +
                "Songs to wake up to\n" +
                "Songs you don't want to wake up to\n" +
                "None of the junk you hear on the radio\n" +
                "Pop (radio junk)";
    }
    public static String categories() {
        return "---CATEGORIES---\n" +
                "Rock\n" +
                "Rap\n" +
                "Funk\n" +
                "No Country for old men";
    }
    public static String moodPlaylists() {
        return "---MOOD PLAYLISTS---\n" +
                "Workout Time\n" +
                "Before the Game\n" +
                "Love in the Air\n" +
                "Last line of the project";
    }

}
