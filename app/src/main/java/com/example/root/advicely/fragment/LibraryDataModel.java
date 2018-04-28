package com.example.root.advicely.fragment;

import android.media.Image;

/**
 * Created by root on 12/26/17.
 */

public class LibraryDataModel {
    private String image;
    private String title;
    private String description;

    public LibraryDataModel(String title,String description,String image) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
