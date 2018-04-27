package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FridgeDashboardActivity extends AppCompatActivity {

    ImageButton meatButton;
    ImageButton grainButton;
    ImageButton veggieButton;
    ImageButton fruitButton;
    ImageButton dairyButton;
    ImageButton otherButton;
    ImageButton chewsButton;

    Button addButton;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_dashboard);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        chewsButton = findViewById(R.id.chewsButton);
        meatButton = findViewById(R.id.meatButton);
        grainButton = findViewById(R.id.grainsButton);
        veggieButton = findViewById(R.id.veggieButton);
        fruitButton = findViewById(R.id.fruitButton);
        dairyButton = findViewById(R.id.dairyButton);
        otherButton = findViewById(R.id.otherButton);
        addButton = findViewById(R.id.addButton);

        //OnClick(chews)
        chewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick (meat)
        meatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, MeatActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //Onclick (grains)
        grainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, GrainsActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //Onclick (Veggies)
        veggieButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, VegetablesActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick (fruit)
        fruitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, FruitsActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //Onclick (dairy)
        dairyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, DairyActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        //OnClick (other)
        otherButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(FridgeDashboardActivity.this, OtherFoodActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                showItemAddDialog();
            }
        });
    }

    private void showItemAddDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.activity_add_item, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextQuantity = dialogView.findViewById(R.id.editTextQuantity);
        final Spinner spinnerType = dialogView.findViewById(R.id.spinnerType);
        final Spinner spinnerUnit = dialogView.findViewById(R.id.spinnerUnit);
        final Button buttonAdd = dialogView.findViewById(R.id.buttonAddItem);

        dialogBuilder.setTitle("New Item");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                double quantity = Double.parseDouble(editTextQuantity.getText().toString().trim());
                String type = spinnerType.getSelectedItem().toString();
                String unit = spinnerUnit.getSelectedItem().toString();

                if(TextUtils.isEmpty(name)) {
                    editTextName.setError("Name Required");
                    return;
                }

                addItem(userId, name, type, quantity, unit);

                alertDialog.dismiss();
            }
        });
    }

    private void addItem(String userId, String itemName, String itemType, double quantity, String unit) {
        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items").child(userId);
        String itemId = databaseItems.push().getKey();

        //should I just add or update items here?

        Item newItem = new Item(userId, itemId, itemName, itemType, quantity, unit);

        databaseItems.child(itemId).setValue(newItem);

        Toast.makeText(this, "Item Added", Toast.LENGTH_LONG).show();
    }
}
