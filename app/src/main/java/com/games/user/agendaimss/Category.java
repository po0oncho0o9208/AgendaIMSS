package com.games.user.agendaimss;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Category {
    private String title;
    private String categoryId;
    private String description;
    private Bitmap imagen;

    public Category() {
        super();
    }

    public Category(String categoryId, String title, String description, Bitmap imagen) {
        super();
        this.title = title;
        this.description = description;
        this.imagen = imagen;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTittle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}