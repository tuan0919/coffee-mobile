package com.nlu.packages.model;

import java.util.List;

public class Product {
    private int productId;
    private String name;
    private double basePrice;
    private String avatar;
    private int categoryId;
    private String categoryName;
    private List<AvailableSize> availableSizes;
    private List<AvailableIngredient> availableIngredients;
    private String productType;
    private double discountPercent;

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<AvailableSize> getAvailableSizes() {
        return availableSizes;
    }

    public void setAvailableSizes(List<AvailableSize> availableSizes) {
        this.availableSizes = availableSizes;
    }

    public List<AvailableIngredient> getAvailableIngredients() {
        return availableIngredients;
    }

    public void setAvailableIngredients(List<AvailableIngredient> availableIngredients) {
        this.availableIngredients = availableIngredients;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
}
