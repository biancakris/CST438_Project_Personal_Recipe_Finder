package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;


public class SearchActivity extends AppCompatActivity {

    Spinner durationSpinner;
    Spinner courseSpinner;
    Spinner cuisineSpinner;
    Spinner holidaySpinner;

    Button searchButton;

    ImageButton chewsButton;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        durationSpinner = findViewById(R.id.durationSpinner);
        courseSpinner = findViewById(R.id.courseSpinner);
        cuisineSpinner = findViewById(R.id.cuisineSpinner);
        holidaySpinner = findViewById(R.id.holidaySpinner);

        searchButton = findViewById(R.id.searchButton);
        chewsButton = findViewById(R.id.chewsButton);

        //set array adapter for spinner using string array and default spinner layout
        ArrayAdapter<CharSequence> durationAdapter = ArrayAdapter.createFromResource(
                this, R.array.duration_array, android.R.layout.simple_spinner_item);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(durationAdapter);

        ArrayAdapter<CharSequence> courseAdapter = ArrayAdapter.createFromResource(
                this, R.array.courses_array, android.R.layout.simple_spinner_item);
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseAdapter);

        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(
                this, R.array.cuisines_array, android.R.layout.simple_spinner_item);
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineSpinner.setAdapter(cuisineAdapter);

        ArrayAdapter<CharSequence> holidayAdapter = ArrayAdapter.createFromResource(
                this, R.array.holiday_array, android.R.layout.simple_spinner_item);
        holidayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holidaySpinner.setAdapter(holidayAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String HolidayText = holidaySpinner.getSelectedItem().toString();
                String durationText = durationSpinner.getSelectedItem().toString();
                String courseText = courseSpinner.getSelectedItem().toString();
                String cuisineText = cuisineSpinner.getSelectedItem().toString();


                Intent intent = new Intent(SearchActivity.this, RecipeListActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                Log.d("breaks", "breaks" );
                startActivity(intent);
                
            }
        });

        chewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(SearchActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

    }
}
