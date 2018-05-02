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

    public static void searchRecipes(String userId, String holiday, String duration, String course, String cuisine, Callback callback){ ArrayList<String> ingredients = getItemList(userId);
      ArrayList<String> allergies = getAllergyList(userId);
      ArrayList<String> dietType = getDietType(userId);

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
        if(!dietType.isEmpty())
        {
            for(String diet: dietType) {
                urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_DIET, diet);
            }
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

                    allergyList.add(translate(restriction.getRestriction()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return allergyList;
    }

    public static ArrayList<String> getDietType(String userId) {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        final ArrayList<String> dietTypeList = new ArrayList<>();

        databaseUsers.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dietTypeList.clear();

                User user = dataSnapshot.getValue(User.class);
                dietTypeList.add(translate(user.getDietType()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return dietTypeList;
    }

    public static String translate(String value) {

        switch(value) {
            //Diet Types
            case "Lacto Vegetarian":
                value = "388^Lacto vegetarian";
                break;
            case "Ovo Vegetarian":
                value = "389^Ovo vegetarian";
                break;
            case "Pescetarian":
                value = "390^Pescetarian";
                break;
            case "Vegan":
                value = "386^Vegan";
                break;
            case "Lacto-ovo Vegetarian":
                value = "387^Lacto-ovo vegetarian";
                break;
            case "Paleo":
                value = "403^Paleo";
                break;
            //Allegeries
            case "Gluten":
                value = "393^Gluten-Free";
                break;
            case "Peanut":
                value = "394^Peanut-Free";
                break;
            case "Seafood":
                value = "398^Seafood-Free";
                break;
            case "Sesame":
                value = "399^Sesame-Free";
                break;
            case "Soy":
                value = "400^Soy-Free";
                break;
            case "Dairy":
                value = "396^Dairy-Free";
                break;
            case "Egg":
                value = "397^Egg-Free";
                break;
            case "Sulfite":
                value = "401^Sulfite-Free";
                break;
            case "Tree Nut":
                value = "395^Tree Nut-Free";
                break;
            case "Wheat":
                value = "392^Wheat-Free";
                break;
            //cusines need to implement
            case "American":
                value ="cuisine^cuisine-american";
                break;
            case "Italian":
                value ="cuisine^cuisine-italian";
                break;
            default:
                break;
        }

        return value;
    }
}
