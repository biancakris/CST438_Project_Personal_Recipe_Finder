package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    Spinner dietarySpinner;
    Spinner allergySpinner;
    Button adminButton;
    Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        adminButton = findViewById(R.id.admin_button);
        dietarySpinner = (Spinner) findViewById(R.id.dietarySpinner);
        allergySpinner = (Spinner) findViewById(R.id.allergySpinner);
        saveButton = findViewById(R.id.saveButton);

        //set array adapter for spinner using string array and default spinner layout
        ArrayAdapter<CharSequence> dietAdapter = ArrayAdapter.createFromResource(
                this, R.array.dietary_array, android.R.layout.simple_spinner_item);
        dietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietarySpinner.setAdapter(dietAdapter);

        //set array adapter for spinner using string array and default spinner layout
        ArrayAdapter<CharSequence> allergyAdapter = ArrayAdapter.createFromResource(
                this, R.array.allergy_array, android.R.layout.simple_spinner_item);
        allergyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allergySpinner.setAdapter(allergyAdapter);



        //On Click (Save Button)
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                startActivity(intent);

            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = getIntent().getExtras();

                Intent intent = new Intent(ProfileActivity.this, Admin.class);
                intent.putExtras(data);

                startActivity(intent);
            }
        });
    }

}
