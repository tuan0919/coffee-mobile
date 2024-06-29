package com.nlu.packages.ui.detailstore;

public class OurData {
    private int image;
    private String title;
    private String price;
    private String description;

    public OurData(int image, String title, String price, String description) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public OurData() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
