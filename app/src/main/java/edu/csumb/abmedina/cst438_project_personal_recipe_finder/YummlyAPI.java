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

    public static void searchRecipes(ArrayList<String> ingredients, String dietRestrictions, int maxTime, Callback callback){
//        ArrayList<String> ingredients = getItemList(userId);
//        ArrayList<String> allergies = getAllergyList(userId);
//        String dietType = getDietType(userId);

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YUMMLY_BASE_URL).newBuilder();

        //for each ingredient add to the url
        if(!ingredients.isEmpty()) {
            for (String ingredient : ingredients) {
                urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_INGREDIENT, ingredient);
            }
        }

        //if diet Retrictions is not empty
        if(!dietRestrictions.isEmpty() || !(dietRestrictions == ""))
        {
            urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_DIET, dietRestrictions);
        }

        //if max time is selected
        if(maxTime > 0)
        {
            urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_MAX_TIME, Integer.toString(maxTime));
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

    public static ArrayList<String> getItemList(String userId) {
        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items").child(userId);
        final ArrayList<String> itemList = new ArrayList<>();

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);

                    itemList.add(item.getItemName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return itemList;
    }

    public static ArrayList<String> getAllergyList(String userId) {
        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("restrictions").child(userId);
        final ArrayList<String> allergyList = new ArrayList<>();

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allergyList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Restriction restriction = itemSnapshot.getValue(Restriction.class);

                    allergyList.add(restriction.getRestriction());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return allergyList;
    }

    public static String getDietType(String userId) {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        final ArrayList<String> dietTypeList = new ArrayList<>();

        databaseUsers.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dietTypeList.clear();

                User user = dataSnapshot.getValue(User.class);
                dietTypeList.add(user.getDietType());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return dietTypeList.get(0);
    }
}
