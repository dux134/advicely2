package com.example.root.advicely.fragment;

/**
 * Created by root on 12/28/17.
 */

public class NotificationDataModel {
    private String notificationTitle;
    private String link;
    private String type;

    public NotificationDataModel(String notificationTitle,String link,String type) {
        this.link = link;
        this.notificationTitle = notificationTitle;
        this.type = type;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getLink() {
        return link;
    }

    public String getType() {
        return type;
    }
}
