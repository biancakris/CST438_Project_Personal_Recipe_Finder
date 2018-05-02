package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeListActivity extends AppCompatActivity {

    public static final String TAG = RecipeListActivity.class.getSimpleName();

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    private RecipeListAdapter mAdapter;

    private SharedPreferences mSharedPreferences;
    private String mRecentAddress;
    //public static final String TAG = RecipeListActivity.class.getSimpleName();


    public ArrayList<Recipe> mRecipes = new ArrayList<>();

    public String userId;
    public String holiday;
    public String duration;
    public String course;
    public String cuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipes);
        ButterKnife.bind(this);
        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");
        holiday = data.getString("holidayText");
        duration = data.getString("durationText");
        course = data.getString("courseText");
        cuisine = data.getString("cuisineText");

        getRecipes();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);
        if (mRecentAddress != null) {
            getRecipes();
        }
    }

    private void getRecipes() {
        final YummlyAPI yummlyService = new YummlyAPI();
        yummlyService.searchRecipes(userId, holiday, duration, course, cuisine, new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response)  {
                mRecipes = yummlyService.processResults(response);

                RecipeListActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mAdapter = new RecipeListAdapter(RecipeListActivity.this, mRecipes);
                        mRecyclerView.setAdapter(mAdapter);
                        RecyclerView.LayoutManager layoutManager =
                                new LinearLayoutManager(RecipeListActivity.this);
                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setHasFixedSize(true);
                    }
                });
            }
        });
    }
}