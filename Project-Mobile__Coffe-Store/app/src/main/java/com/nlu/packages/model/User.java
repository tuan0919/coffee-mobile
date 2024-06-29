package com.nlu.packages.model;

public class User {
    private String id;
    private String  displayName;
    private String  loginToken;

    private String  image;
    private String email;

    public User() {
    }

    public User(String id, String displayName, String loginToken, String image, String email) {
        this.id = id;
        this.displayName = displayName;
        this.loginToken = loginToken;
        this.image = image;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
