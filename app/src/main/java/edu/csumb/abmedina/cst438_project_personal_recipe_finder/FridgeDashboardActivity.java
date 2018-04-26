package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FridgeDashboardActivity extends AppCompatActivity {

    ImageButton meatButton;
    ImageButton grainButton;
    ImageButton veggieButton;
    ImageButton fruitButton;
    ImageButton dairyButton;
    ImageButton otherButton;
    ImageButton chewsButton;

    Button addButton;
    Button deleteButton;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_dashboard);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        chewsButton = findViewById(R.id.chewsButton);
        meatButton = findViewById(R.id.meatButton);
        grainButton = findViewById(R.id.grainsButton);
        veggieButton = findViewById(R.id.veggieButton);
        fruitButton = findViewById(R.id.fruitButton);
        dairyButton = findViewById(R.id.dairyButton);
        otherButton = findViewById(R.id.otherButton);
        addButton = findViewById(R.id.addButton);
        deleteButton = findViewById(R.id.deleteButton);

        //OnClick(chews)
        chewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick (meat)
        meatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, MeatActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //Onclick (grains)
        grainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, GrainsActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //Onclick (Veggies)
        veggieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, VegetablesActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick (fruit)
        fruitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, FruitsActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //Onclick (dairy)
        dairyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, DairyActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick (other)
        otherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, OtherFoodActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });
    }
}
