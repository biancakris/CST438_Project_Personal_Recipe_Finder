package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    Spinner dietarySpinner;
    Spinner allergySpinner;
    EditText editTextName;
    Button adminButton;
    Button saveButton;
    String userId;

    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        adminButton = findViewById(R.id.admin_button);
        dietarySpinner = (Spinner) findViewById(R.id.dietarySpinner);
        allergySpinner = (Spinner) findViewById(R.id.allergySpinner);
        editTextName = findViewById(R.id.editTextName);
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

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseUsers.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                editTextName.setText(user.getUsername());

                String dietType = user.getDietType();

                ArrayAdapter<CharSequence> dietAdapter = ArrayAdapter.createFromResource(ProfileActivity.this, R.array.dietary_array, android.R.layout.simple_spinner_item);
                dietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dietarySpinner.setAdapter(dietAdapter);
                if (dietType != null) {
                    int spinnerPosition = dietAdapter.getPosition(dietType);
                    dietarySpinner.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //On Click (Save Button)
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                databaseUsers = FirebaseDatabase.getInstance().getReference("users");

                User newUser = new User(userId, editTextName.getText().toString().trim(),
                        dietarySpinner.getSelectedItem().toString());

                databaseUsers.child(userId).setValue(newUser);

                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);

                startActivity(intent);

            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Admin.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);

                startActivity(intent);
            }
        });
    }

}
