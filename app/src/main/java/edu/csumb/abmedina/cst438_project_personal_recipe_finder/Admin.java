package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //addUser("Abraham", "Standard");
        //addItem("-LAK-Iyy8Ipff0jDr5Ou", "Chicken", "Meat", 5, "pound");
        //addRestriction("-LAK-Iyy8Ipff0jDr5Ou", "gluten allergy");
    }
}
