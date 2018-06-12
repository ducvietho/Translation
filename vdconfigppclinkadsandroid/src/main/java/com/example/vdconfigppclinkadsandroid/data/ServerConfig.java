package com.example.vdconfigppclinkadsandroid.data;

import android.text.TextUtils;


import com.example.vdconfigppclinkadsandroid.notifications.Notification;
import com.example.vdconfigppclinkadsandroid.privatekey.PrivateKey;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServerConfig {
    public static ServerConfig serverConfig;

    public static ServerConfig getInstance() {
        if (serverConfig == null) {
            serverConfig = new ServerConfig();
        }
        return serverConfig;
    }

    public static void setServerConfig(ServerConfig config) {
        serverConfig = config;
        checkKeyAds();
    }

    int adRectangleRefreshRate = 20;
    int Ads = 1;
    int AdTestMode = 0;
    int bannerAdShowTimeInterval = 180;
    int contactUsUIStyle = 0;
    // Cost (in points) per ad-free day/hours
    int CostOneDayFreeAd = 1;
    int FreeAdsRate = 3;
    // Khoang thoi gian toi thieu giua 2 lan hien quang cao interstitial
    int fullScreenAdFreeBetweenAdShowMinTimeInterval = 240;
    // Khoang thoi gian toi thieu giua 2 lan hien quang cao interstitial khi da
    // user click/xem quang cao full/video
    int fullScreenAdFreeTimeIntervalBonus = 300;
    // Khoang thoi gian toi thieu tu khi mo app den khi show interstitial ad dau
    // tien
    int fullScreenAdFreeWhenOpenAppTimeInterval = 180;
    int GoogleAnalyticsEnable = 1;
    int GoogleAnalyticsDispatchInterval;
    int incomingLocalNotifyInterval = 3;
    String incomingLocalNotifyMessage = "default";
    String ipServer = "42.112.31.56";
    String message = "default";
    int message_enable = 0;
    int message_version = 18;
    int MinFreeAdsRate = 2;
    int MinPaidAdsRate = 0;
    int PaidAdsRate = 0;
    int MinAdFreeTimeStayOnView = 6;
    int PercentVideoInterstitalAd = 30;
    int VideoAdRewardAmount = 0;
    String PPCLINKMediationConfig_AdBanner = "admob#mopub";
    String PPCLINKMediationConfig_Interstitial = "admob#mopub#adcolony";
    String PPCLINKMediationConfig_VideoInterstitial = "adcolony#admob#mopub";
    String PPCLINKMediationConfig_NativeAd = "mopub#facebook";
    // Enable quay vong mang quang cao
    int RecirculateAdNetworkEnable;
    int showAdWhenSwitchChannel;
    int ShowBannerAdOnFullscreenLandscapeMode;
    int ShowPopularYoutubeVideosOnHomePage;
    String TVCountryCodeAllowParseYoutubeUsingXCD;
    int TVMenuHiddenItems;
    int TVMenuHiddenItemsUsingOTAndUD;
    int TVMinOpenTimes;
    int TVMinUsingDays;
    int TVModeParseYoutubeLink;
    int V4VCAdFreeTimeIntervalBonus;
    String app_latest_version;
    String appstoreurl;
    String nativead_rate;
    int frequencyShowNativeAd = 0;
    @SerializedName("percentPpclinkNativeads")
    int percentPpclinkNativeads = 0;

    //remote config
    @SerializedName("first_default_tab")
    String firstDefaultTab;
    @SerializedName("sync_request_quoteoftheday")
    String syncRequestQuoteoftheday;
    @SerializedName("sync_request_system_event")
    String syncRequestSystemEvent;
    @SerializedName("sync_request_user_event")
    String syncRequestUserEvent;
    @SerializedName("sync_request_system_article")
    String syncRequestSystemArticle;
    @SerializedName("sync_request_error_code")
    String syncRequestErrorCode;
    @SerializedName("sync_request_photo_cover")
    String syncRequestPhotoCover;
    @SerializedName("sync_request_quote")
    String syncRequestQuote;
    @SerializedName("activate_ads_facebook")
    String activateAdsFacebook;
    @SerializedName("gift_enable")
    String giftEnable;

    //AdID configuration key
    String adid_adcolony_appid = PrivateKey.privatekey_kAppID;
    String adid_adcolony_zoneid = PrivateKey.privatekey_kZoneID;

    String adid_vungle = PrivateKey.privatekey_vungle;

    String adid_admob_banner = PrivateKey.privatekey_kAdmobBannerMediationID;
    String adid_admob_interstitial_image = PrivateKey.privatekey_kAdmobImageInterstitialMediationID;
    String adid_admob_interstitial_video = PrivateKey.privatekey_kAdmobVideoInterstitialMediationID;

    String adid_mopub_phone_banner = PrivateKey.privatekey_kMopubBannerPhone;
    String adid_mopub_phone_full = PrivateKey.privatekey_kMopubFullPhone;
    String adid_mopub_tablet_leaderboard = PrivateKey.privatekey_kMopubTabletLeaderboard;
    String adid_mopub_tablet_full = PrivateKey.privatekey_kMopubTabletFull;
    String adid_mopub_video = PrivateKey.privatekey_kMopubVideo;
    String adid_mopub_native = PrivateKey.privatekey_kMopubNative;

    String adid_facebook_native = PrivateKey.privatekey_nativeAdFacebook;

    private static void checkKeyAds(){
        if (serverConfig == null)
            return;
        //adcolony
        if (TextUtils.isEmpty(serverConfig.getAdid_adcolony_appid()) || serverConfig.getAdid_adcolony_appid().equals("0"))
            serverConfig.setAdid_adcolony_appid(PrivateKey.privatekey_kAppID);
        if (TextUtils.isEmpty(serverConfig.getAdid_adcolony_zoneid()) || serverConfig.getAdid_adcolony_zoneid().equals("0"))
            serverConfig.setAdid_adcolony_zoneid(PrivateKey.privatekey_kZoneID);

        //vungle

        //admob
        if (TextUtils.isEmpty(serverConfig.getAdid_admob_banner()) || serverConfig.getAdid_admob_banner().equals("0"))
            serverConfig.setAdid_admob_banner(PrivateKey.privatekey_kAdmobBannerMediationID);
        if (TextUtils.isEmpty(serverConfig.getAdid_admob_interstitial_image()) || serverConfig.getAdid_admob_interstitial_image().equals("0"))
            serverConfig.setAdid_admob_interstitial_image(PrivateKey.privatekey_kAdmobImageInterstitialMediationID);
        if (TextUtils.isEmpty(serverConfig.getAdid_admob_interstitial_video()) || serverConfig.getAdid_admob_interstitial_video().equals("0"))
            serverConfig.setAdid_admob_interstitial_video(PrivateKey.privatekey_kAdmobVideoInterstitialMediationID);

        //mopub
        if (TextUtils.isEmpty(serverConfig.getAdid_mopub_phone_banner()) || serverConfig.getAdid_mopub_phone_banner().equals("0"))
            serverConfig.setAdid_mopub_phone_banner(PrivateKey.privatekey_kMopubBannerPhone);
        if (TextUtils.isEmpty(serverConfig.getAdid_mopub_phone_full()) || serverConfig.getAdid_mopub_phone_full().equals("0"))
            serverConfig.setAdid_mopub_phone_full(PrivateKey.privatekey_kMopubFullPhone);
        if (TextUtils.isEmpty(serverConfig.getAdid_mopub_tablet_leaderboard()) || serverConfig.getAdid_mopub_tablet_leaderboard().equals("0"))
            serverConfig.setAdid_mopub_tablet_leaderboard(PrivateKey.privatekey_kMopubTabletLeaderboard);
        if (TextUtils.isEmpty(serverConfig.getAdid_mopub_tablet_full()) || serverConfig.getAdid_mopub_tablet_full().equals("0"))
            serverConfig.setAdid_mopub_tablet_full(PrivateKey.privatekey_kMopubTabletFull);
        if (TextUtils.isEmpty(serverConfig.getAdid_mopub_video()) || serverConfig.getAdid_mopub_video().equals("0"))
            serverConfig.setAdid_mopub_video(PrivateKey.privatekey_kMopubVideo);
        if (TextUtils.isEmpty(serverConfig.getAdid_mopub_native()) || serverConfig.getAdid_mopub_native().equals("0"))
            serverConfig.setAdid_mopub_native(PrivateKey.privatekey_kMopubNative);

        //facebook native
        if (TextUtils.isEmpty(serverConfig.getAdid_facebook_native()) || serverConfig.getAdid_facebook_native().equals("0"))
            serverConfig.setAdid_facebook_native(PrivateKey.privatekey_nativeAdFacebook);
    }

    ArrayList<Notification> arrNotifications = new ArrayList<Notification>();

    public int getAdRectangleRefreshRate() {
        return adRectangleRefreshRate;
    }

    public void setAdRectangleRefreshRate(int adRectangleRefreshRate) {
        this.adRectangleRefreshRate = adRectangleRefreshRate;
    }

    public int getAds() {
        return Ads;
    }

    public void setAds(int ads) {
        Ads = ads;
    }

    public int getAdTestMode() {
        return AdTestMode;
    }

    public void setAdTestMode(int adTestMode) {
        AdTestMode = adTestMode;
    }

    public int getBannerAdShowTimeInterval() {
        return bannerAdShowTimeInterval;
    }

    public void setBannerAdShowTimeInterval(int bannerAdShowTimeInterval) {
        this.bannerAdShowTimeInterval = bannerAdShowTimeInterval;
    }

    public int getContactUsUIStyle() {
        return contactUsUIStyle;
    }

    public void setContactUsUIStyle(int contactUsUIStyle) {
        this.contactUsUIStyle = contactUsUIStyle;
    }

    public int getCostOneDayFreeAd() {
        return CostOneDayFreeAd;
    }

    public void setCostOneDayFreeAd(int costOneDayFreeAd) {
        CostOneDayFreeAd = costOneDayFreeAd;
    }

    public int getFreeAdsRate() {
        return FreeAdsRate;
    }

    public void setFreeAdsRate(int freeAdsRate) {
        FreeAdsRate = freeAdsRate;
    }

    public int getFullScreenAdFreeBetweenAdShowMinTimeInterval() {
        return fullScreenAdFreeBetweenAdShowMinTimeInterval;
    }

    public void setFullScreenAdFreeBetweenAdShowMinTimeInterval(int fullScreenAdFreeBetweenAdShowMinTimeInterval) {
        this.fullScreenAdFreeBetweenAdShowMinTimeInterval = fullScreenAdFreeBetweenAdShowMinTimeInterval;
    }

    public int getFullScreenAdFreeTimeIntervalBonus() {
        return fullScreenAdFreeTimeIntervalBonus;
    }

    public void setFullScreenAdFreeTimeIntervalBonus(int fullScreenAdFreeTimeIntervalBonus) {
        this.fullScreenAdFreeTimeIntervalBonus = fullScreenAdFreeTimeIntervalBonus;
    }

    public int getFullScreenAdFreeWhenOpenAppTimeInterval() {
        return fullScreenAdFreeWhenOpenAppTimeInterval;
    }

    public void setFullScreenAdFreeWhenOpenAppTimeInterval(int fullScreenAdFreeWhenOpenAppTimeInterval) {
        this.fullScreenAdFreeWhenOpenAppTimeInterval = fullScreenAdFreeWhenOpenAppTimeInterval;
    }

    public int getGoogleAnalyticsEnable() {
        return GoogleAnalyticsEnable;
    }

    public void setGoogleAnalyticsEnable(int googleAnalyticsEnable) {
        GoogleAnalyticsEnable = googleAnalyticsEnable;
    }

    public int getGoogleAnalyticsDispatchInterval() {
        return GoogleAnalyticsDispatchInterval;
    }

    public void setGoogleAnalyticsDispatchInterval(int googleAnalyticsDispatchInterval) {
        GoogleAnalyticsDispatchInterval = googleAnalyticsDispatchInterval;
    }

    public int getIncomingLocalNotifyInterval() {
        return incomingLocalNotifyInterval;
    }

    public void setIncomingLocalNotifyInterval(int incomingLocalNotifyInterval) {
        this.incomingLocalNotifyInterval = incomingLocalNotifyInterval;
    }

    public String getIncomingLocalNotifyMessage() {
        return incomingLocalNotifyMessage;
    }

    public void setIncomingLocalNotifyMessage(String incomingLocalNotifyMessage) {
        this.incomingLocalNotifyMessage = incomingLocalNotifyMessage;
    }

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessage_enable() {
        return message_enable;
    }

    public void setMessage_enable(int message_enable) {
        this.message_enable = message_enable;
    }

    public int getMessage_version() {
        return message_version;
    }

    public void setMessage_version(int message_version) {
        this.message_version = message_version;
    }

    public int getMinFreeAdsRate() {
        return MinFreeAdsRate;
    }

    public void setMinFreeAdsRate(int minFreeAdsRate) {
        MinFreeAdsRate = minFreeAdsRate;
    }

    public int getMinPaidAdsRate() {
        return MinPaidAdsRate;
    }

    public void setMinPaidAdsRate(int minPaidAdsRate) {
        MinPaidAdsRate = minPaidAdsRate;
    }

    public int getPaidAdsRate() {
        return PaidAdsRate;
    }

    public void setPaidAdsRate(int paidAdsRate) {
        PaidAdsRate = paidAdsRate;
    }

    public int getMinAdFreeTimeStayOnView() {
        return MinAdFreeTimeStayOnView;
    }

    public void setMinAdFreeTimeStayOnView(int minAdFreeTimeStayOnView) {
        MinAdFreeTimeStayOnView = minAdFreeTimeStayOnView;
    }

    public int getPercentVideoInterstitalAd() {
        return PercentVideoInterstitalAd;
    }

    public void setPercentVideoInterstitalAd(int percentVideoInterstitalAd) {
        PercentVideoInterstitalAd = percentVideoInterstitalAd;
    }

    public String getPPCLINKMediationConfig_AdBanner() {
        return PPCLINKMediationConfig_AdBanner;
    }

    public void setPPCLINKMediationConfig_AdBanner(String PPCLINKMediationConfig_AdBanner) {
        this.PPCLINKMediationConfig_AdBanner = PPCLINKMediationConfig_AdBanner;
    }

    public String getPPCLINKMediationConfig_Interstitial() {
        return PPCLINKMediationConfig_Interstitial;
    }

    public void setPPCLINKMediationConfig_Interstitial(String PPCLINKMediationConfig_Interstitial) {
        this.PPCLINKMediationConfig_Interstitial = PPCLINKMediationConfig_Interstitial;
    }

    public String getPPCLINKMediationConfig_VideoInterstitial() {
        return PPCLINKMediationConfig_VideoInterstitial;
    }

    public void setPPCLINKMediationConfig_VideoInterstitial(String PPCLINKMediationConfig_VideoInterstitial) {
        this.PPCLINKMediationConfig_VideoInterstitial = PPCLINKMediationConfig_VideoInterstitial;
    }

    public String getPPCLINKMediationConfig_NativeAd() {
        return PPCLINKMediationConfig_NativeAd;
    }

    public void setPPCLINKMediationConfig_NativeAd(String PPCLINKMediationConfig_NativeAd) {
        this.PPCLINKMediationConfig_NativeAd = PPCLINKMediationConfig_NativeAd;
    }

    public int getRecirculateAdNetworkEnable() {
        return RecirculateAdNetworkEnable;
    }

    public void setRecirculateAdNetworkEnable(int recirculateAdNetworkEnable) {
        RecirculateAdNetworkEnable = recirculateAdNetworkEnable;
    }

    public int getShowAdWhenSwitchChannel() {
        return showAdWhenSwitchChannel;
    }

    public void setShowAdWhenSwitchChannel(int showAdWhenSwitchChannel) {
        this.showAdWhenSwitchChannel = showAdWhenSwitchChannel;
    }

    public int getShowBannerAdOnFullscreenLandscapeMode() {
        return ShowBannerAdOnFullscreenLandscapeMode;
    }

    public void setShowBannerAdOnFullscreenLandscapeMode(int showBannerAdOnFullscreenLandscapeMode) {
        ShowBannerAdOnFullscreenLandscapeMode = showBannerAdOnFullscreenLandscapeMode;
    }

    public int getShowPopularYoutubeVideosOnHomePage() {
        return ShowPopularYoutubeVideosOnHomePage;
    }

    public void setShowPopularYoutubeVideosOnHomePage(int showPopularYoutubeVideosOnHomePage) {
        ShowPopularYoutubeVideosOnHomePage = showPopularYoutubeVideosOnHomePage;
    }

    public String getTVCountryCodeAllowParseYoutubeUsingXCD() {
        return TVCountryCodeAllowParseYoutubeUsingXCD;
    }

    public void setTVCountryCodeAllowParseYoutubeUsingXCD(String TVCountryCodeAllowParseYoutubeUsingXCD) {
        this.TVCountryCodeAllowParseYoutubeUsingXCD = TVCountryCodeAllowParseYoutubeUsingXCD;
    }

    public int getTVMenuHiddenItems() {
        return TVMenuHiddenItems;
    }

    public void setTVMenuHiddenItems(int TVMenuHiddenItems) {
        this.TVMenuHiddenItems = TVMenuHiddenItems;
    }

    public int getTVMenuHiddenItemsUsingOTAndUD() {
        return TVMenuHiddenItemsUsingOTAndUD;
    }

    public void setTVMenuHiddenItemsUsingOTAndUD(int TVMenuHiddenItemsUsingOTAndUD) {
        this.TVMenuHiddenItemsUsingOTAndUD = TVMenuHiddenItemsUsingOTAndUD;
    }

    public int getTVMinOpenTimes() {
        return TVMinOpenTimes;
    }

    public void setTVMinOpenTimes(int TVMinOpenTimes) {
        this.TVMinOpenTimes = TVMinOpenTimes;
    }

    public int getTVMinUsingDays() {
        return TVMinUsingDays;
    }

    public void setTVMinUsingDays(int TVMinUsingDays) {
        this.TVMinUsingDays = TVMinUsingDays;
    }

    public int getTVModeParseYoutubeLink() {
        return TVModeParseYoutubeLink;
    }

    public void setTVModeParseYoutubeLink(int TVModeParseYoutubeLink) {
        this.TVModeParseYoutubeLink = TVModeParseYoutubeLink;
    }

    public int getV4VCAdFreeTimeIntervalBonus() {
        return V4VCAdFreeTimeIntervalBonus;
    }

    public void setV4VCAdFreeTimeIntervalBonus(int v4VCAdFreeTimeIntervalBonus) {
        V4VCAdFreeTimeIntervalBonus = v4VCAdFreeTimeIntervalBonus;
    }

    public String getApp_latest_version() {
        return app_latest_version;
    }

    public void setApp_latest_version(String app_latest_version) {
        this.app_latest_version = app_latest_version;
    }

    public String getAppstoreurl() {
        return appstoreurl;
    }

    public void setAppstoreurl(String appstoreurl) {
        this.appstoreurl = appstoreurl;
    }

    public String getNativead_rate() {
        return nativead_rate;
    }

    public void setNativead_rate(String nativead_rate) {
        this.nativead_rate = nativead_rate;
    }

    public int getFrequencyShowNativeAd() {
        return frequencyShowNativeAd;
    }

    public void setFrequencyShowNativeAd(int frequencyShowNativeAd) {
        this.frequencyShowNativeAd = frequencyShowNativeAd;
    }

    public String getAdid_adcolony_appid() {
        return adid_adcolony_appid;
    }

    public void setAdid_adcolony_appid(String adid_adcolony_appid) {
        this.adid_adcolony_appid = adid_adcolony_appid;
    }

    public String getAdid_adcolony_zoneid() {
        return adid_adcolony_zoneid;
    }

    public void setAdid_adcolony_zoneid(String adid_adcolony_zoneid) {
        this.adid_adcolony_zoneid = adid_adcolony_zoneid;
    }

    public String getAdid_admob_banner() {
        return adid_admob_banner;
    }

    public void setAdid_admob_banner(String adid_admob_banner) {
        this.adid_admob_banner = adid_admob_banner;
    }

    public String getAdid_admob_interstitial_image() {
        return adid_admob_interstitial_image;
    }

    public void setAdid_admob_interstitial_image(String adid_admob_interstitial_image) {
        this.adid_admob_interstitial_image = adid_admob_interstitial_image;
    }

    public String getAdid_admob_interstitial_video() {
        return adid_admob_interstitial_video;
    }

    public void setAdid_admob_interstitial_video(String adid_admob_interstitial_video) {
        this.adid_admob_interstitial_video = adid_admob_interstitial_video;
    }

    public String getAdid_mopub_phone_banner() {
        return adid_mopub_phone_banner;
    }

    public void setAdid_mopub_phone_banner(String adid_mopub_phone_banner) {
        this.adid_mopub_phone_banner = adid_mopub_phone_banner;
    }

    public String getAdid_mopub_phone_full() {
        return adid_mopub_phone_full;
    }

    public void setAdid_mopub_phone_full(String adid_mopub_phone_full) {
        this.adid_mopub_phone_full = adid_mopub_phone_full;
    }

    public String getAdid_mopub_tablet_leaderboard() {
        return adid_mopub_tablet_leaderboard;
    }

    public void setAdid_mopub_tablet_leaderboard(String adid_mopub_tablet_leaderboard) {
        this.adid_mopub_tablet_leaderboard = adid_mopub_tablet_leaderboard;
    }

    public String getAdid_mopub_tablet_full() {
        return adid_mopub_tablet_full;
    }

    public void setAdid_mopub_tablet_full(String adid_mopub_tablet_full) {
        this.adid_mopub_tablet_full = adid_mopub_tablet_full;
    }

    public String getAdid_mopub_video() {
        return adid_mopub_video;
    }

    public void setAdid_mopub_video(String adid_mopub_video) {
        this.adid_mopub_video = adid_mopub_video;
    }

    public String getAdid_mopub_native() {
        return adid_mopub_native;
    }

    public void setAdid_mopub_native(String adid_mopub_native) {
        this.adid_mopub_native = adid_mopub_native;
    }

    public String getAdid_facebook_native() {
        return adid_facebook_native;
    }

    public void setAdid_facebook_native(String adid_facebook_native) {
        this.adid_facebook_native = adid_facebook_native;
    }

    public ArrayList<Notification> getArrNotifications() {
        return arrNotifications;
    }

    public ArrayList<Notification> getListMoreApp() {
        ArrayList<Notification> list = new ArrayList<>();
        for (Notification notification : arrNotifications) {
            if (!TextUtils.isEmpty(notification.getAdIconMoreApps())) {
                list.add(notification);
            }
        }
        return list;
    }

    public ArrayList<Notification> getListAdbanner() {
        ArrayList<Notification> list = new ArrayList<>();
        for (Notification notification : arrNotifications) {
            if (!TextUtils.isEmpty(notification.getAdBanner())) {
                list.add(notification);
            }
        }
        return list;
    }

    public ArrayList<Notification> getListAdImage() {
        ArrayList<Notification> list = new ArrayList<>();
        for (Notification notification : arrNotifications) {
            if (!TextUtils.isEmpty(notification.getAdImage())) {
                list.add(notification);
            }
        }
        return list;
    }

    public ArrayList<Notification> getListRectangle() {
        ArrayList<Notification> list = new ArrayList<>();
        for (Notification notification : arrNotifications) {
            if (!TextUtils.isEmpty(notification.getAdRectangle())) {
                list.add(notification);
            }
        }
        return list;
    }

    public void setArrNotifications(ArrayList<Notification> arrNotifications) {
        this.arrNotifications = arrNotifications;
    }

    public int getVideoAdRewardAmount() {
        return VideoAdRewardAmount;
    }

    public String getAdid_vungle() {
        return adid_vungle;
    }

    public int getPercentPpclinkNativeads() {
        return percentPpclinkNativeads;
    }

    public void setPercentPpclinkNativeads(int percentPpclinkNativeads) {
        this.percentPpclinkNativeads = percentPpclinkNativeads;
    }

    public String getFirstDefaultTab() {
        return firstDefaultTab;
    }

    public void setFirstDefaultTab(String firstDefaultTab) {
        this.firstDefaultTab = firstDefaultTab;
    }

    public String getSyncRequestQuoteoftheday() {
        return syncRequestQuoteoftheday;
    }

    public void setSyncRequestQuoteoftheday(String syncRequestQuoteoftheday) {
        this.syncRequestQuoteoftheday = syncRequestQuoteoftheday;
    }

    public String getSyncRequestSystemEvent() {
        return syncRequestSystemEvent;
    }

    public void setSyncRequestSystemEvent(String syncRequestSystemEvent) {
        this.syncRequestSystemEvent = syncRequestSystemEvent;
    }

    public String getSyncRequestUserEvent() {
        return syncRequestUserEvent;
    }

    public void setSyncRequestUserEvent(String syncRequestUserEvent) {
        this.syncRequestUserEvent = syncRequestUserEvent;
    }

    public String getSyncRequestSystemArticle() {
        return syncRequestSystemArticle;
    }

    public void setSyncRequestSystemArticle(String syncRequestSystemArticle) {
        this.syncRequestSystemArticle = syncRequestSystemArticle;
    }

    public String getSyncRequestErrorCode() {
        return syncRequestErrorCode;
    }

    public void setSyncRequestErrorCode(String syncRequestErrorCode) {
        this.syncRequestErrorCode = syncRequestErrorCode;
    }

    public String getSyncRequestPhotoCover() {
        return syncRequestPhotoCover;
    }

    public void setSyncRequestPhotoCover(String syncRequestPhotoCover) {
        this.syncRequestPhotoCover = syncRequestPhotoCover;
    }

    public String getSyncRequestQuote() {
        return syncRequestQuote;
    }

    public void setSyncRequestQuote(String syncRequestQuote) {
        this.syncRequestQuote = syncRequestQuote;
    }

    public String getActivateAdsFacebook() {
        return activateAdsFacebook;
    }

    public void setActivateAdsFacebook(String activateAdsFacebook) {
        this.activateAdsFacebook = activateAdsFacebook;
    }

    public String getGiftEnable() {
        return giftEnable;
    }

    public void setGiftEnable(String giftEnable) {
        this.giftEnable = giftEnable;
    }
}
