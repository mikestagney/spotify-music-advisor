package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class CategoryArchive {

    ArrayList<Category> categoryList = new ArrayList<>();

    CategoryArchive(String json) {
        JsonObject jo = JsonParser.parseString(json).getAsJsonObject();
        JsonObject categories = jo.getAsJsonObject("categories");
        JsonArray items = categories.getAsJsonArray("items");

        for (JsonElement element : items) {
            JsonObject item = element.getAsJsonObject();
            String url = item.get("href").getAsString();
            String id = item.get("id").getAsString();
            String name = item.get("name").getAsString();
            
            System.out.println(name);
            Category category = new Category(name, id, url);
            categoryList.add(category);
        }


    }


}
