package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivity extends AppCompatActivity {

    ImageButton chefButton;
    ImageButton chewsButton;
    ImageButton fridgeButton;
    Button recipeBookButton;
    Button shoppingListButton;
    Button searchButton;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        chefButton = findViewById(R.id.chefButton);
        chewsButton = findViewById(R.id.chewsButton);
        fridgeButton = findViewById(R.id.fridgeButton);
        recipeBookButton = findViewById(R.id.recipeButton);
        shoppingListButton = findViewById(R.id.shoppingListButton);
        searchButton = findViewById(R.id.searchButton);

        //On Click (Profile Button)
        chefButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick(chews)
        chewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, FridgeDashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick (search)
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, SearchActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        // OnClick(recipe)
        recipeBookButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, MyRecipesActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick(list)
        shoppingListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(DashboardActivity.this, ShoppingListActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });
    }
}
