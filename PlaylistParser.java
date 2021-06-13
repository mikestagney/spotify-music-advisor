package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class PlaylistParser {

    ArrayList<Playlist> featuredPlaylists = new ArrayList<>();

    PlaylistParser(String json) {
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();

        JsonObject playlists = jo.getAsJsonObject("playlists");
        JsonArray items = playlists.getAsJsonArray("items");

        for (JsonElement element : items) {
            JsonObject item = element.getAsJsonObject();
            String name = item.get("name").getAsString();

            JsonObject externalLinks = item.getAsJsonObject("external_urls");

            String url = externalLinks.get("spotify").getAsString();

            Playlist playlist = new Playlist(name, url);
            featuredPlaylists.add(playlist);
        }

    }

    public ArrayList<Playlist> getList() {
        return featuredPlaylists;
    }
}
