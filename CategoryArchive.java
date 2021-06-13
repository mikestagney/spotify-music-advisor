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
            String id = item.get("id").getAsString();
            String name = item.get("name").getAsString();
            Category category = new Category(name, id);
            categoryList.add(category);
        }

    }
    public String getCategoryID(String name) {
        String id = "";
        for (Category current: categoryList) {
            if (name.equals(current.getName())) {
                id = current.getId();
                break;
            }
        }
        return id;
    }

    public String toString() {
        StringBuilder categories = new StringBuilder();
        for (Category current: categoryList) {
            categories.append(current.toString());
            /*
            categories.append(current.getName());
            categories.append("\n");*/
        }
        return categories.toString();
    }
}
