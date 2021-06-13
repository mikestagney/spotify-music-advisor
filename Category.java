package advisor;

public class Category {
    private String url;
    private String name;
    private String id;

    Category(String name, String id, String url) {
        this.url = url;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
