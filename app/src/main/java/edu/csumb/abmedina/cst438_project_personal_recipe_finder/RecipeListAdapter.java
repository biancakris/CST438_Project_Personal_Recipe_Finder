package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

//perhaps these do not matter
import edu.csumb.abmedina.cst438_project_personal_recipe_finder.R;
import edu.csumb.abmedina.cst438_project_personal_recipe_finder.Recipe;
import edu.csumb.abmedina.cst438_project_personal_recipe_finder.RecipeDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>{
    private ArrayList<Recipe> mRecipe = new ArrayList<>();
    private Context mContext;

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes) {
        mContext = context;
        mRecipe = recipes;
    }

    @Override
    public RecipeListAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_layout, parent, false);
        RecipeViewHolder viewHolder = new RecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter.RecipeViewHolder holder, int position) {
        holder.bindRecipe(mRecipe.get(position));
    }

    @Override
    public int getItemCount() {
        return mRecipe.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.recipeImageView) ImageView mRecipeImageView;
        @Bind(R.id.recipeNameTextView) TextView mRecipeNameTextView;
        @Bind(R.id.sourceTextView) TextView mSourceTextView;


        private Context mContext;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, RecipeDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("recipes", Parcels.wrap(mRecipe));
            //bug: will not switch activities

            Log.d("mContext","mContext:\n "+mContext.toString());

           // mContext.startActivity(intent);

        }

        public void bindRecipe(Recipe recipe) {
            Picasso.with(mContext).load(recipe.getImageUrl()).into(mRecipeImageView);
            mRecipeNameTextView.setText(recipe.getRecipeName());
            mSourceTextView.setText("Posted By: " + recipe.getSource());
        }

    }

}
