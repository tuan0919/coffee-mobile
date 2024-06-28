package com.nlu.packages.ui.order.OrderPopular;

public class Favorite {
    private int id;
    private int userId;
    private boolean isFavorite;

    public Favorite(int id, int userId, boolean isFavorite) {
        this.id = id;
        this.userId = userId;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
