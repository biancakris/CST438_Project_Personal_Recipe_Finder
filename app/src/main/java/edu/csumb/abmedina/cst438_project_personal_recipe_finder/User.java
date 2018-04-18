package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

public class User {
    private String userId;
    private String username;
    private String dietType;

    public User() {}

    public User(String userId, String username, String dietType) {
        this.userId = userId;
        this.username = username;
        this.dietType = dietType;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDietType() {
        return dietType;
    }
}
