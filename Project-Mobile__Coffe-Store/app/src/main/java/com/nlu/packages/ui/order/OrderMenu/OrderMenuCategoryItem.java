package com.nlu.packages.ui.order.OrderMenu;

public class OrderMenuCategoryItem {
    private String imageUrl;
    private String productName;

    public OrderMenuCategoryItem(String imageUrl, String productName) {
        this.imageUrl = imageUrl;
        this.productName = productName;
    }

    public OrderMenuCategoryItem() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
