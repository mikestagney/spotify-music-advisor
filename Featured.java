package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class Featured {

    ArrayList<Playlist> featuredPlaylists = new ArrayList<>();

    Featured(String json) {
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();

        JsonObject playlists = jo.getAsJsonObject("playlists");
        System.out.println(playlists.toString());
        JsonArray items = playlists.getAsJsonArray("items");
        System.out.println(items.toString());

        for (JsonElement element : items) {
            JsonObject item = element.getAsJsonObject();
            String name = item.get("name").getAsString();
            System.out.println(name);


            JsonObject externalLinks = item.getAsJsonObject("external_urls");

            String url = externalLinks.get("spotify").getAsString();
            System.out.println(url);
            System.out.println();
            Playlist playlist = new Playlist(name, url);
            featuredPlaylists.add(playlist);
        }


    }


}
