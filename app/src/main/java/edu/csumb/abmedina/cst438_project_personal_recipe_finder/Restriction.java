package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

public class Restriction {
    private String resId;
    private String userId;
    private String restriction;

    public Restriction() {}

    public Restriction(String resId, String userId, String restriction) {
        this.resId = resId;
        this.userId = userId;
        this.restriction = restriction;
    }

    public String getResId() {
        return resId;
    }

    public String getUserId() {
        return userId;
    }

    public String getRestriction() {
        return restriction;
    }
}
