package com.nlu.packages.ui.order.OrderMenu;

import java.util.ArrayList;

public class OrderMenuTypeItem {
    String categoyName;
    ArrayList<OrderMenuCategoryItem> categoryItems;

    public OrderMenuTypeItem(String categoyName, ArrayList<OrderMenuCategoryItem> categoryItems) {
        this.categoyName = categoyName;
        this.categoryItems = categoryItems;
    }

    public String getCategoyName() {
        return categoyName;
    }

    public ArrayList<OrderMenuCategoryItem> getCategoryItems() {
        return categoryItems;
    }
}
