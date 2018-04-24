package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemList extends ArrayAdapter<Item> {

    private Activity context;
    private List<Item> itemList;

    public ItemList(Activity context, List<Item> itemList) {
        super(context, R.layout.item_list_layout, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.item_list_layout, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        TextView textViewType = listViewItem.findViewById(R.id.textViewType);
        TextView textViewQuantity = listViewItem.findViewById(R.id.textViewQuantity);
        TextView textViewUnit = listViewItem.findViewById(R.id.textViewUnit);

        Item item = itemList.get(position);

        textViewName.setText(item.getItemName());
        textViewType.setText(item.getItemType());
        textViewQuantity.setText(String.valueOf(item.getQuantity()));
        textViewUnit.setText(item.getUnit());

        return listViewItem;
    }
}
