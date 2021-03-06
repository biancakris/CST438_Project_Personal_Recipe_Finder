package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

import edu.csumb.abmedina.cst438_project_personal_recipe_finder.BuildConfig;

public class Constants {
    public static final String YUMMLY_API_KEY = BuildConfig.YUMMLY_API_KEY;
    public static final String YUMMLY_APP_ID = BuildConfig.YUMMLY_APP_ID;
    public static final String YUMMLY_BASE_URL = "http://api.yummly.com/v1/api/recipes?";
    public static final String API_ID_QUERY_PARAMETER = "X-Yummly-App-ID";
    public static final String API_KEY_QUERY_PARAMETER = "X-Yummly-App-Key";
    public static final String SEARCH_QUERY_INGREDIENT = "allowedIngredient[]";
    public static final String SEARCH_QUERY_DIET = "allowedDiet[]";
    public static final String SEARCH_QUERY_ALLERGY = "allowedAllergy[]";
    public static final String SEARCH_QUERY_CUISINE = "allowedCuisine[]";
    public static final String SEARCH_QUERY_COURSE = "allowedCourse[]";
    public static final String SEARCH_QUERY_HOLIDAY = "allowedHoliday[]";
    public static final String SEARCH_QUERY_MAX_TIME = "maxTotalTimeInSeconds";
    public static final String PREFERENCES_LOCATION_KEY = "recipe";

}
