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
        //addItem("-LAK-Iyy8Ipff0jDr5Ou", "Cheese", "Dairy", 1, "pound");
        //addRestriction("-LAK-Iyy8Ipff0jDr5Ou", "gluten allergy");
    }

    private void addUser(String username, String dietType) {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        String userId = databaseUsers.push().getKey();

        //check if already registered

        User newUser = new User(userId, username, dietType);

        databaseUsers.child(userId).setValue(newUser);

        Toast.makeText(this, "User Added", Toast.LENGTH_LONG).show();
    }

    private void addItem(String userId, String itemName, String itemType, int quantity, String unit) {
        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items");
        String itemId = databaseItems.push().getKey();

        //should I just add or update items here?

        Item newItem = new Item(userId, itemId, itemName, itemType, quantity, unit);

        databaseItems.child(itemId).setValue(newItem);

        Toast.makeText(this, "Item Added", Toast.LENGTH_LONG).show();
    }

    private void addRestriction(String userId, String restriction) {
        DatabaseReference databaseRestrictions = FirebaseDatabase.getInstance().getReference("restrictions");
        String resId = databaseRestrictions.push().getKey();

        //check if its already there

        Restriction newRes = new Restriction(resId, userId, restriction);

        databaseRestrictions.child(resId).setValue(newRes);

        Toast.makeText(this, "Restriction Added", Toast.LENGTH_LONG).show();
    }
}