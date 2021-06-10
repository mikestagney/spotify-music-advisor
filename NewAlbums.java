package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class NewAlbums {

    ArrayList<Album> newAlbums = new ArrayList<>();

    NewAlbums(String json) {
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonObject albums = jo.getAsJsonObject("albums");
        JsonArray items = albums.getAsJsonArray("items");

        for (JsonElement element : items) {
            JsonObject item = element.getAsJsonObject();

            String albumName = item.get("name").getAsString();
            System.out.println(albumName);

            StringBuilder allArtists = new StringBuilder();
            allArtists.append("[");
            JsonArray artists = item.getAsJsonArray("artists");

            int counterArtists = 0;
            for (JsonElement artist : artists) {
                JsonObject holder = artist.getAsJsonObject();
                String artistName = holder.get("name").getAsString();

                allArtists.append(artistName);
                if (artists.size() > 1 && (counterArtists >= 0) && counterArtists < (artists.size() - 1)) {
                    allArtists.append(", ");
                }
                counterArtists++;

            }
            allArtists.append("]");
            System.out.println(allArtists);

            JsonObject externalLinks = item.getAsJsonObject("external_urls");
            String url = externalLinks.get("spotify").getAsString();
            System.out.println(url);
            System.out.println();

            Album album = new Album(albumName, allArtists.toString(), url);
            newAlbums.add(album);
        }




    }


}
