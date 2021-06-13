package advisor;

public class Album extends Recommendation {
    private String url;
    private String albumName;
    private String artists;

    Album(String name, String artist, String url) {
        albumName = name;
        artists = artist;
        this.url = url;
    }

    @Override
    public String toString() {
        return albumName + "\n" +
               artists + "\n" +
               url + "\n";
    }
}
