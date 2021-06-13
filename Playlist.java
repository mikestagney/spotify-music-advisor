package advisor;

public class Playlist extends Recommendation{

    private String name;
    private String url;

    Playlist(String name, String url) {
        this.name = name;
        this.url = url;

    }

    @Override
    public String toString() {
        return name + "\n" + url + "\n";
    }
}
