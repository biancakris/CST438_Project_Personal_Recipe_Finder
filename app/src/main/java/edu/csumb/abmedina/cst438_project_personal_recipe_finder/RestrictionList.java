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

public class RestrictionList extends ArrayAdapter<Restriction> {

    private Activity context;
    private List<Restriction> restrictionList;

    public RestrictionList(Activity context, List<Restriction> restrictionList) {
        super(context, R.layout.restriction_list_layout, restrictionList);
        this.context = context;
        this.restrictionList = restrictionList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewRestriction = inflater.inflate(R.layout.restriction_list_layout, null, true);

        TextView textViewRestriction = listViewRestriction.findViewById(R.id.textViewRestriction);

        Restriction restriction = restrictionList.get(position);

        textViewRestriction.setText(restriction.getRestriction());

        return listViewRestriction;
    }
}
