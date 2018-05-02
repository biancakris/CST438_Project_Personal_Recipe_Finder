package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import java.util.ArrayList;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import edu.csumb.abmedina.cst438_project_personal_recipe_finder.Constants;
import edu.csumb.abmedina.cst438_project_personal_recipe_finder.Recipe;
import okhttp3.Callback;
import okhttp3.Call;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YummlyAPI {

    public static void searchRecipes(ArrayList<String> ingredients, ArrayList<String> allergies, String dietType, String holiday, String duration, String course, String cuisine, Callback callback){

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YUMMLY_BASE_URL).newBuilder();

        //add the ingredietants to the URL
        if(!ingredients.isEmpty()) {
            for(String ingredient: ingredients) {
                urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_INGREDIENT, ingredient);
            }
        }

        //adds diet type to the URL
        if(!dietType.equals("None"))
        {
            urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_DIET, dietType);
        }

        //add allegery list to the URL
        if(!allergies.isEmpty())
        {
            for(String allergy: allergies)
            {
                urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_ALLERGY, allergy);
            }
        }

        //adds holiday to the URL need to set up holiday in the translate function
        if(!(holiday == ""))
        {
           // urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_HOLIDAY, translate(holiday));
        }

        //adds duration to the URL need to set up duration in its own function
        if(!(duration == ""))
        {
            //urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_MAX_TIME, "");
        }

        //adds course to the URL need to set up in the translate function
        if(!(course == ""))
        {
            //urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_COURSE, "");
        }

        //adds cuisine to the URK need to set up in the translate function
        if(!(cuisine == ""))
        {
            //urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_CUISINE,"");
        }

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
