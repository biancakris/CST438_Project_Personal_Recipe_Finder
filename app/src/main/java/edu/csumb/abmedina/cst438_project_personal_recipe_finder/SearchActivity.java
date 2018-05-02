package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    Spinner durationSpinner;
    Spinner courseSpinner;
    Spinner cuisineSpinner;
    Spinner holidaySpinner;

    Button searchButton;

    ImageButton chewsButton;

    TextView titleLabel;

    String userId;

    ArrayList<String> itemList = new ArrayList<>();
    ArrayList<String> allergyList = new ArrayList<>();
    String dietType;

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

        titleLabel = findViewById(R.id.titleLabel);

        titleLabel.setPaintFlags(titleLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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
                data.putString("holidayText",HolidayText);
                data.putString("durationText", durationText);
                data.putString("courseText", courseText);
                data.putString("cusineText", cuisineText);
                intent.putExtra("itemList", itemList);
                intent.putExtra("allergyList", allergyList);
                intent.putExtra("dietType", dietType);
                intent.putExtras(data);
                //Log.d("breaks", "breaks" );
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

    public static String translate(String value) {

        switch(value) {
            //Diet Types
            case "Lacto Vegetarian":
                value = "388^Lacto vegetarian";
                break;
            case "Ovo Vegetarian":
                value = "389^Ovo vegetarian";
                break;
            case "Pescetarian":
                value = "390^Pescetarian";
                break;
            case "Vegan":
                value = "386^Vegan";
                break;
            case "Lacto-ovo Vegetarian":
                value = "387^Lacto-ovo vegetarian";
                break;
            case "Paleo":
                value = "403^Paleo";
                break;
            //Allegeries
            case "Gluten":
                value = "393^Gluten-Free";
                break;
            case "Peanut":
                value = "394^Peanut-Free";
                break;
            case "Seafood":
                value = "398^Seafood-Free";
                break;
            case "Sesame":
                value = "399^Sesame-Free";
                break;
            case "Soy":
                value = "400^Soy-Free";
                break;
            case "Dairy":
                value = "396^Dairy-Free";
                break;
            case "Egg":
                value = "397^Egg-Free";
                break;
            case "Sulfite":
                value = "401^Sulfite-Free";
                break;
            case "Tree Nut":
                value = "395^Tree Nut-Free";
                break;
            case "Wheat":
                value = "392^Wheat-Free";
                break;
            //cusines need to implement
            case "American":
                value ="cuisine^cuisine-american";
                break;
            case "Italian":
                value ="cuisine^cuisine-italian";
                break;
            default:
                break;
        }

        return value;
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items").child(userId);

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);

                    itemList.add(item.getItemName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseRestrictions = FirebaseDatabase.getInstance().getReference("restrictions").child(userId);

        databaseRestrictions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allergyList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Restriction restriction = itemSnapshot.getValue(Restriction.class);

                    allergyList.add(translate(restriction.getRestriction()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseUsers.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                dietType = translate(user.getDietType());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
