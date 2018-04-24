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

    Spinner dietarySpiner;
    Button adminButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        adminButton = findViewById(R.id.admin_button);
        dietarySpiner = (Spinner) findViewById(R.id.dietarySpinner);

        //set array adapter for spinner using string array and default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.dietary_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietarySpiner.setAdapter(adapter);



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
