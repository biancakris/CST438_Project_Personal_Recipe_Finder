import java.util.ArrayList;

import android.provider.SyncStateContract;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.csumb.abmedina.cst438_project_personal_recipe_finder.Recipe;
import okhttp3.Callback;
import okhttp3.Call;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YummlyAPI {

    public static  void searchRecipes(ArrayList<String> ingredients, String dietRestrictions, int maxTime, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YUMMLY_BASE_URL).newBuilder();

        //here we use the urlBuilder;
        //add for later
        //for testing purposes
        urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_INGREDIENT,"Cheese");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header(Constants.API_ID_QUERY_PARAMETER, Constants.YUMMLY_APP_ID)
                .header(Constants.API_KEY_QUERY_PARAMETER, Constants.YUMMLY_API_KEY)
                .build();

        Log.d("url", url);

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    //using the search results call the website json add it to the ArrayList of Recipes
    public ArrayList<Recipe> processResults(Response response){
        ArrayList<Recipe> recipes = new ArrayList<>();

        try {
            if (response.isSuccessful()) {
                String jsonData = response.body().string();

                JSONObject yummlyJSON = new JSONObject(jsonData);
                JSONArray matchesJSON = yummlyJSON.getJSONArray("matches");
                for (int i = 0; i < matchesJSON.length(); i++) {
                    JSONObject recipeJSON = matchesJSON.getJSONObject(i);
                    String name = recipeJSON.getString("recipeName");

                    ArrayList<String> ingredients = new ArrayList<>();
                    JSONArray ingredientsJSON = recipeJSON.getJSONArray("ingredients");

                    for (int y = 0; y < ingredientsJSON.length(); y++) {
                        ingredients.add(ingredientsJSON.get(y).toString());
                    }

                    String imgUrl = recipeJSON.getJSONObject("imageUrlsBySize").getString("90");
                    String rating = recipeJSON.getString("rating");
                    String source = recipeJSON.getString("sourceDisplayName");
                    String id = recipeJSON.getString("id");

                    Recipe recipe = new Recipe(name, ingredients, imgUrl, rating, source, id);
                    recipes.add(recipe);

                }
            }

    } catch (IOException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    }
        return recipes;
    }

}
