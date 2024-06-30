package com.nlu.packages.model;

public class AvailableIngredient {
    private int ingredientId;
    private String ingredientName;
    private String ingredientType;
    private String ingredientEnum;
    private double addPrice;

    public AvailableIngredient() {
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(String ingredientType) {
        this.ingredientType = ingredientType;
    }

    public String getIngredientEnum() {
        return ingredientEnum;
    }

    public void setIngredientEnum(String ingredientEnum) {
        this.ingredientEnum = ingredientEnum;
    }

    public double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(double addPrice) {
        this.addPrice = addPrice;
    }
}
