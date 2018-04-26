package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    Spinner dietarySpinner;
    //Spinner allergySpinner;
    EditText editTextName;
    Button adminButton;
    Button saveButton;
    Button addRestriction;
    String userId;

    ListView listViewRestrictions;

    List<Restriction> restrictionList;

    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        adminButton = findViewById(R.id.admin_button);
        dietarySpinner = (Spinner) findViewById(R.id.dietarySpinner);
        //allergySpinner = (Spinner) findViewById(R.id.allergySpinner);
        editTextName = findViewById(R.id.editTextName);
        saveButton = findViewById(R.id.saveButton);
        addRestriction = findViewById(R.id.buttonAddRestriction);

        //set array adapter for spinner using string array and default spinner layout
        ArrayAdapter<CharSequence> dietAdapter = ArrayAdapter.createFromResource(
                this, R.array.dietary_array, android.R.layout.simple_spinner_item);
        dietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietarySpinner.setAdapter(dietAdapter);

        //set array adapter for spinner using string array and default spinner layout
//        ArrayAdapter<CharSequence> allergyAdapter = ArrayAdapter.createFromResource(
//                this, R.array.allergy_array, android.R.layout.simple_spinner_item);
//        allergyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        allergySpinner.setAdapter(allergyAdapter);

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

        listViewRestrictions = findViewById(R.id.listViewRestrictions);

        restrictionList = new ArrayList<>();

        listViewRestrictions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Restriction restriction = restrictionList.get(i);

                showItemUpdateDialog(restriction.getResId(), restriction.getRestriction());
            }
        });

        addRestriction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        addRestriction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewRestriction();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("restrictions").child(userId);

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                restrictionList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Restriction restriction = itemSnapshot.getValue(Restriction.class);

                    restrictionList.add(restriction);
                }

                RestrictionList adapter = new RestrictionList(ProfileActivity.this, restrictionList);
                listViewRestrictions.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showItemUpdateDialog(final String resId, final String restriction) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.activity_delete_restriction, null);

        dialogBuilder.setView(dialogView);

        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteRestriction);

        dialogBuilder.setTitle("Deleting " + restriction);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRestriction(resId);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteRestriction(String resId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("restrictions").child(userId).child(resId);

        databaseReference.removeValue();

        Toast.makeText(this, "Restriction is deleted", Toast.LENGTH_LONG).show();
    }

    private void addNewRestriction() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.activity_add_restriction, null);

        dialogBuilder.setView(dialogView);

        final Button buttonAdd = dialogView.findViewById(R.id.buttonAddRestriction);
        final Spinner spinnerRestriction = dialogView.findViewById(R.id.spinnerRestrictions);

        dialogBuilder.setTitle("New Restriction");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseRestrictions = FirebaseDatabase.getInstance().getReference("restrictions").child(userId);
                String resId = databaseRestrictions.push().getKey();

                //check if its already there

                Restriction newRes = new Restriction(resId, userId, spinnerRestriction.getSelectedItem().toString());

                databaseRestrictions.child(resId).setValue(newRes);

                Toast.makeText(ProfileActivity.this, "Restriction Added", Toast.LENGTH_LONG).show();

                alertDialog.dismiss();
            }
        });
    }
}
