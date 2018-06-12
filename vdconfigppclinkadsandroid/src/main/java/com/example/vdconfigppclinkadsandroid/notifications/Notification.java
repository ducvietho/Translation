package com.example.vdconfigppclinkadsandroid.notifications;

import android.text.TextUtils;

public class Notification {
    private int NotificationId;
    private String AppId;
    private String AdImage;
    private String AdBanner;
    private String AdRectangle;
    private String AdFullscreen;
    private int order;
    private int Type;
    private String Title;
    private String Description;
    private String TryNowText;
    private String CancelText;
    private String URL;
    private int installedRewardPoints;
    private int MaxClick;
    private int MaxImpression;
    private String AdIconMoreApps;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getAdIconMoreApps() {
        if (AdIconMoreApps != null && AdIconMoreApps.length() > 0 && !AdIconMoreApps.contains("http://")) {
            AdIconMoreApps = "http://deltago.com/notifications/adv/public/upload/images/" + AdIconMoreApps;
        }
        return AdIconMoreApps;
    }

    public void setAdIconMoreApps(String adIconMoreApps) {
        this.AdIconMoreApps = adIconMoreApps;
    }

    public Notification() {

    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int notificationId) {
        NotificationId = notificationId;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getAdImage() {
        if(TextUtils.isEmpty(AdImage)) return AdImage;
        if (!AdImage.contains("http://")) {
            AdImage = "http://deltago.com/notifications/adv/public/upload/images/" + AdImage;
        }
        return AdImage;
    }

    public void setAdImage(String adImage) {
        AdImage = adImage;
    }

    public String getAdBanner() {
        if(TextUtils.isEmpty(AdBanner)) return AdBanner;
        if (!AdBanner.contains("http://")) {
            AdBanner = "http://deltago.com/notifications/adv/public/upload/images/" + AdBanner;
        }
        return AdBanner;
    }

    public void setAdBanner(String adBanner) {
        AdBanner = adBanner;
    }

    public String getAdRectangle() {
        if(TextUtils.isEmpty(AdRectangle)) return AdRectangle;
        if (!AdRectangle.contains("http://")) {
            AdRectangle = "http://deltago.com/notifications/adv/public/upload/images/" + AdRectangle;
        }
        return AdRectangle;
    }

    public void setAdRectangle(String adRectangle) {
        AdRectangle = adRectangle;
    }

    public String getAdFullscreen() {
        if(TextUtils.isEmpty(AdFullscreen)) return AdFullscreen;
        if (!AdFullscreen.contains("http://")) {
            AdFullscreen = "http://deltago.com/notifications/adv/public/upload/images/" + AdFullscreen;
        }
        return AdFullscreen;
    }

    public void setAdFullscreen(String adFullscreen) {
        AdFullscreen = adFullscreen;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTryNowText() {
        return TryNowText;
    }

    public void setTryNowText(String tryNowText) {
        TryNowText = tryNowText;
    }

    public String getCancelText() {
        return CancelText;
    }

    public void setCancelText(String cancelText) {
        CancelText = cancelText;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public int getInstalledRewardPoints() {
        return installedRewardPoints;
    }

    public void setInstalledRewardPoints(int installedRewardPoints) {
        this.installedRewardPoints = installedRewardPoints;
    }

    public int getMaxClick() {
        return MaxClick;
    }

    public void setMaxClick(int maxClick) {
        MaxClick = maxClick;
    }

    public int getMaxImpression() {
        return MaxImpression;
    }

    public void setMaxImpression(int maxImpression) {
        MaxImpression = maxImpression;
    }
}
