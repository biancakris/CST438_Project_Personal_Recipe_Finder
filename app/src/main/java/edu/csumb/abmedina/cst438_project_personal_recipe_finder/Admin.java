package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {

    ListView listViewItems;

    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Bundle data = getIntent().getExtras();
        String userId = data.getString("userId");

        //addUser(userId,"Abraham", "Standard");
        //addItem(userId, "Chicken", "Meat", 5, "pound");
        //addRestriction(userId, "peanut allergy");

        listViewItems = findViewById(R.id.listViewItems);

        itemList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items");

        databaseItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);

                    itemList.add(item);
                }

                ItemList adapter = new ItemList(Admin.this, itemList);
                listViewItems.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addUser(String userId, String username, String dietType) {
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference("users");

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