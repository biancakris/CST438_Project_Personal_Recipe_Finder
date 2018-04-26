package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ListRecipesFoundActivity extends AppCompatActivity {

    ImageButton chewsButton;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes_found);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        chewsButton = findViewById(R.id.chewsButton);

        chewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ListRecipesFoundActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });
    }
}
