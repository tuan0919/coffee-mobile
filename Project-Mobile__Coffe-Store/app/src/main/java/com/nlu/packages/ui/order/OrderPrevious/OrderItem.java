package com.nlu.packages.ui.order.OrderPrevious;

public class OrderItem {
    private String name;
    private String price;
    private int imageResourceId;

    public OrderItem(String name, String price, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
