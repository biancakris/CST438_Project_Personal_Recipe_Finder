package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import java.util.ArrayList;

public class Recipe {
    String name;
    ArrayList<String> ingredients = new ArrayList<>();
    String imgUrl;
    String rating;
    String source;
    String id;
    private String pushId;
    String index;

    public Recipe() {}

    public Recipe(String recipeName, ArrayList<String> ingredients, String imageUrl, String rating, String source, String id) {
        this.name = recipeName;
        this.ingredients = ingredients;
        this.imgUrl = imageUrl;
        this.rating = rating;
        this.source = source;
        this.id = id;
        this.index = "not_specified";

    }

    public String getRecipeName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getImageUrl() {
        return  imgUrl;
    }

    public String getRating() {
        return rating;
    }

    public String getSource() {
        return source;
    }

    public String getId() { return id; }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

}
