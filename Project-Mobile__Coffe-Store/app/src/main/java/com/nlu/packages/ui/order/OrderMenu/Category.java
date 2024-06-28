package com.nlu.packages.ui.order.OrderMenu;

import java.io.Serializable;

public class Category implements Serializable {
    private int categoryId;
    private String categoryName;
    private String avatar;
    private String type;

    public Category(int categoryId, String categoryName, String avatar, String type) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.avatar = avatar;
        this.type = type;
    }

    public Category() {

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
