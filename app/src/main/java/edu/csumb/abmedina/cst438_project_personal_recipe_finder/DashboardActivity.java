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

public class DashboardActivity extends AppCompatActivity {

    ImageButton chefButton;
    ImageButton fridgeButton;
    ImageButton recipeBookButton;
    ImageButton shoppingListButton;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        chefButton = findViewById(R.id.chefButton);
        fridgeButton = findViewById(R.id.fridgeButton);
        recipeBookButton = findViewById(R.id.recipeButton);
        shoppingListButton = findViewById(R.id.shoppingListButton);

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

        //On Click (Fridge Button)
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
    }
}
