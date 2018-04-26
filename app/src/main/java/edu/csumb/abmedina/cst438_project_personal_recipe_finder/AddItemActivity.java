package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AddItemActivity extends AppCompatActivity {

    ImageButton chewsButton;
    Spinner measurementsSpinner;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        chewsButton = findViewById(R.id.chewsButton);
        measurementsSpinner = findViewById(R.id.measurmentSpinner);

        //set array adapter for spinner using string array and default spinner layout
        ArrayAdapter<CharSequence> measurementAdapter = ArrayAdapter.createFromResource(
                this, R.array.measurements_array, android.R.layout.simple_spinner_item);
        measurementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementsSpinner.setAdapter(measurementAdapter);

        chewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(AddItemActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });
    }
}
