package edu.csumb.abmedina.cst438_project_personal_recipe_finder;

public class Item {
    private String userId;
    private String itemId;
    private String itemName;
    private String itemType;
    private int quantity;
    private String unit;

    public Item() {}

    public Item(String userId, String itemId, String itemName, String itemType, int quantity, String unit) {
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
