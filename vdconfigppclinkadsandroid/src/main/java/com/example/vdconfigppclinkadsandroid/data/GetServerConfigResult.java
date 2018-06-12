package com.example.vdconfigppclinkadsandroid.data;

import com.example.vdconfigppclinkadsandroid.notifications.Notification;

import java.util.ArrayList;

/**
 * Created by HungNX on 5/9/16.
 */
public class GetServerConfigResult {
    String result;
    ServerConfig config;
    ArrayList<Notification> notifications;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ServerConfig getConfig() {
        return config;
    }

    public void setConfig(ServerConfig config) {
        this.config = config;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
