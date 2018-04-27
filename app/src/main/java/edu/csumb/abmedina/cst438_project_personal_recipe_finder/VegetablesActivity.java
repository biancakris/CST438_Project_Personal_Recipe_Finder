package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VegetablesActivity extends AppCompatActivity {

    ImageButton chewsButton;

    ListView listViewItems;

    List<Item> itemList;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetables);

        Bundle data = getIntent().getExtras();
        userId = data.getString("userId");

        listViewItems = findViewById(R.id.listViewItems);

        itemList = new ArrayList<>();

        chewsButton = findViewById(R.id.chewsButton);
        //OnClick(chews)
        chewsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(VegetablesActivity.this, DashboardActivity.class);
                Bundle data =  new Bundle();

                data.putString("userId", userId);
                intent.putExtras(data);
                startActivity(intent);

            }
        });

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = itemList.get(i);

                showItemUpdateDialog(item.getItemId(), item.getItemName(), item.getItemType(), item.getQuantity(), item.getUnit());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference databaseItems = FirebaseDatabase.getInstance().getReference("items").child(userId);

        databaseItems.orderByChild("itemType").equalTo("Vegetable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemList.clear();

                for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item item = itemSnapshot.getValue(Item.class);

                    itemList.add(item);
                }

                ItemList adapter = new ItemList(VegetablesActivity.this, itemList);
                listViewItems.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showItemUpdateDialog(final String itemId, final String itemName, final String itemType, final double itemQuantity, final String itemUnit) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_item_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextQuantity = dialogView.findViewById(R.id.editTextQuantity);
        final Spinner spinnerType = dialogView.findViewById(R.id.spinnerType);
        final Spinner spinnerUnit = dialogView.findViewById(R.id.spinnerUnit);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDelete);

        editTextName.setText(itemName);
        editTextQuantity.setText(String.valueOf(itemQuantity));

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.item_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        if (itemType != null) {
            int spinnerPosition = typeAdapter.getPosition(itemType);
            spinnerType.setSelection(spinnerPosition);
        }

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this, R.array.item_units, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);
        if (itemUnit != null) {
            int spinnerPosition = unitAdapter.getPosition(itemUnit);
            spinnerUnit.setSelection(spinnerPosition);
        }

        dialogBuilder.setTitle("Updating " + itemName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
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

                updateItem(itemId, name, type, quantity, unit);

                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(itemId);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteItem(String itemId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("items").child(userId).child(itemId);

        databaseReference.removeValue();

        Toast.makeText(this, "Item is deleted", Toast.LENGTH_LONG).show();
    }

    private boolean updateItem(String itemId, String itemName, String itemType, double quantity, String unit) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("items").child(userId).child(itemId);

        Item item = new Item(userId, itemId, itemName, itemType, quantity, unit);

        databaseReference.setValue(item);

        Toast.makeText(this, "Item Updated Successfully", Toast.LENGTH_LONG).show();

        return true;
    }
}
