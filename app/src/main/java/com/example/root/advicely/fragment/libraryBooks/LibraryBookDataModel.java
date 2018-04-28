package com.example.root.advicely.fragment.libraryBooks;

/**
 * Created by root on 1/3/18.
 */

public class LibraryBookDataModel {
    private String bookName;
    private String bookLink;
    private String bookDescription;
    private String imageUrl;

    public LibraryBookDataModel(String book_Name ,String book_Description, String book_Link,String image_Url) {
        bookName = book_Name;
        bookLink = book_Link;
        bookDescription = book_Description;
        imageUrl = image_Url;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookLink() {
        return bookLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBookDescription() {
        return bookDescription;
    }
}
