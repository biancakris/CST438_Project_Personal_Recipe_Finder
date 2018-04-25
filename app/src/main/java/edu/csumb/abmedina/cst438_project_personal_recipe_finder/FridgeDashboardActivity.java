package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FridgeDashboardActivity extends AppCompatActivity {

    ImageButton meatButton;
    ImageButton chewsButton;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_dashboard);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        chewsButton = findViewById(R.id.chewsButton);
        meatButton = findViewById(R.id.meatButton);

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
    }
}
