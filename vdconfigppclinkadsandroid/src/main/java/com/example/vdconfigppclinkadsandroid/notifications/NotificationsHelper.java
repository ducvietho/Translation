package com.example.vdconfigppclinkadsandroid.notifications;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;


import com.example.vdconfigppclinkadsandroid.data.Constants;
import com.example.vdconfigppclinkadsandroid.data.GetServerConfigResult;
import com.example.vdconfigppclinkadsandroid.data.ServerConfig;
import com.example.vdconfigppclinkadsandroid.delegate.INotificationsHelper;
import com.example.vdconfigppclinkadsandroid.utils.AppDataManager;
import com.example.vdconfigppclinkadsandroid.utils.ConnectionManager;
import com.example.vdconfigppclinkadsandroid.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsHelper {
    private static NotificationsHelper instance;
    private boolean shouldAutoShowNotificationWhenOpenApp;
    private Activity controller;
    private boolean isConfigured;
    private INotificationsHelper delegate;
    private boolean bCheckInstallApp;

    public void setCheckInstallApp(boolean value) {
        bCheckInstallApp = value;
    }

    public NotificationsHelper() {
        isConfigured = false;
        bCheckInstallApp = true;

        setShouldAutoShowNotificationWhenOpenApp(true);

        // get freeRateNotification
        int freeRateNotification = AppDataManager.getInstance()
                .getFreeRateNotification();
        if (freeRateNotification == -1
                && ServerConfig.getInstance().getFreeAdsRate() >= ServerConfig.getInstance().getMinFreeAdsRate()) {
            freeRateNotification = Utils.getRandomNumber(
                    ServerConfig.getInstance().getMinFreeAdsRate(), ServerConfig.getInstance().getFreeAdsRate(),
                    new Random());

            if (freeRateNotification >= 0) {
                AppDataManager.getInstance().saveFreeRateNotification(
                        freeRateNotification);
            }
        }

//        MediationAdHelper.getInstance().updateBannerAdNetworkGroup();
//        MediationAdHelper.getInstance().updateInterstitialAdNetworkGroup();
//        MediationAdHelper.getInstance().updateInterstitialVideoAdNetworkGroup();
//        MediationAdHelper.getInstance().updateNativeAdNetwordGroup();
    }

    public static NotificationsHelper getInstance() {
        if (instance == null)
            instance = new NotificationsHelper();
        return instance;
    }

    public void configure(final Activity activity, final boolean enableShowPopUpNotification, String appId) {
        if (isConfigured)
            return;
        if (activity == null || controller == activity)
            return;
        controller = activity;
        delegate = (INotificationsHelper) controller;

        if (isConfigured)
            return;
        ConnectionManager.getListNotifications(new Callback<GetServerConfigResult>() {
            @Override
            public void onResponse(Call<GetServerConfigResult> call, Response<GetServerConfigResult> response) {
                GetServerConfigResult result = response.body();
                if (result != null) {
                    ServerConfig.setServerConfig(response.body().getConfig());
                    ServerConfig.getInstance().setArrNotifications(response.body().getNotifications());
                    Utils.getSharedPreferences(activity).edit().putString(Constants.KEY_SERVER_CONFIG, new Gson().toJson(ServerConfig.getInstance())).commit();
                    int freeRateNotification = AppDataManager.getInstance()
                            .getFreeRateNotification();
                    if (freeRateNotification == -1
                            && ServerConfig.getInstance().getFreeAdsRate() >= ServerConfig.getInstance().getMinFreeAdsRate()) {
                        freeRateNotification = Utils.getRandomNumber(
                                ServerConfig.getInstance().getMinFreeAdsRate(), ServerConfig.getInstance().getFreeAdsRate(),
                                new Random());
                        if (freeRateNotification >= 0) {
                            AppDataManager.getInstance().saveFreeRateNotification(
                                    freeRateNotification);
                        }
                    }
//3 comment
//                    MediationAdHelper.getInstance().updateBannerAdNetworkGroup();
//                    MediationAdHelper.getInstance().updateInterstitialAdNetworkGroup();
//                    MediationAdHelper.getInstance().updateInterstitialVideoAdNetworkGroup();
//                    MediationAdHelper.getInstance().updateNativeAdNetwordGroup();
                    delegate.onGetConfigSuccess();

                    // comment for app mini game (Quang)
                    if (enableShowPopUpNotification)
                        showPopUpNotification(false, null);
                    isConfigured = true;
                }
            }

            @Override
            public void onFailure(Call<GetServerConfigResult> call, Throwable t) {
                String serverconfig = Utils.getSharedPreferences(activity).getString(Constants.KEY_SERVER_CONFIG, "");
                if (serverconfig != null && serverconfig.length() > 0) {
                    ServerConfig.setServerConfig(new Gson().fromJson(serverconfig, ServerConfig.class));
                } else {
                    return;
                }
                int freeRateNotification = AppDataManager.getInstance()
                        .getFreeRateNotification();
                if (freeRateNotification == -1
                        && ServerConfig.getInstance().getFreeAdsRate() >= ServerConfig.getInstance().getMinFreeAdsRate()) {
                    freeRateNotification = Utils.getRandomNumber(
                            ServerConfig.getInstance().getMinFreeAdsRate(), ServerConfig.getInstance().getFreeAdsRate(),
                            new Random());
                    if (freeRateNotification >= 0) {
                        AppDataManager.getInstance().saveFreeRateNotification(
                                freeRateNotification);
                    }
                }

//                MediationAdHelper.getInstance().updateBannerAdNetworkGroup();
//                MediationAdHelper.getInstance().updateInterstitialAdNetworkGroup();
//                MediationAdHelper.getInstance().updateInterstitialVideoAdNetworkGroup();
//                MediationAdHelper.getInstance().updateNativeAdNetwordGroup();
                delegate.onGetConfigSuccess();

                // comment for app mini game (Quang)
                if (enableShowPopUpNotification)
                    showPopUpNotification(false, null);
                isConfigured = true;
            }
        }, appId);
        onResume();
    }

    public void onResume() {
        if (!isConfigured)
            return;
        /*// increment number openapp
        int currValueOpenApp = AppDataManager.getInstance().numberOpenApp;
		AppDataManager.getInstance().saveNumberOpenApp(currValueOpenApp + 1);*/
    }

    public void onStart() {
        // increment number openapp
        int currValueOpenApp = AppDataManager.getInstance().numberOpenApp;
        AppDataManager.getInstance().saveNumberOpenApp(currValueOpenApp + 1);
    }

    public void onDestroy() {
        if (isConfigured)
            isConfigured = false;
        if (instance != null)
            instance = null;
    }

    public void showPopUpNotification(boolean bForceShow, JSONObject jobjData) {
        if (ServerConfig.getInstance().getArrNotifications().size() == 0)
            return;

        if (!bForceShow) {
            if (ServerConfig.getInstance().getAds() != 1)
                return;
        }

        Notification objNotification = getSequenceNotification(false);
        if (isInAdFreeTime()
                && objNotification.getType() != Constants.NTYPE_MESSAGE)
            return;
        boolean bShow = showPopUpNotification(objNotification, jobjData);
        if (bShow && !bForceShow) {
            AppDataManager.getInstance().saveNumberOpenApp(0);
            AppDataManager.getInstance().saveFreeRateNotification(-1);
            int currentIndexNotification = AppDataManager.getInstance()
                    .getCurrentIndexNotification();
            AppDataManager.getInstance().saveCurrentIndexNotification(
                    currentIndexNotification + 1);
        }
    }

    /**
     * Show popup notification
     * <p>
     * : = YES/NO <=> KhÃ´ng/CÃ³ kiá»ƒm tra Ä‘iá»�u kiá»‡n sá»‘ láº§n
     * má»Ÿ app trÆ°á»›c khi show
     *
     * @return YES/NO = Show/not show notification
     **/
    public boolean showPopUpNotification(final Notification objNotification,
                                         final JSONObject jobjData) {
        if (AppDataManager.getInstance().getIsPremiumUser())
            return false;
        if (objNotification == null)
            return false;
        if (controller == null || controller.isFinishing()) {
            return false;
        }
        int notificationType = objNotification.getType();
        // if (notificationType == Constants.NTYPE_VIEWADS) {
        // kiem tra dk so lan mo app truoc
        int numberOpenApp = AppDataManager.getInstance().numberOpenApp;
        int freeRateNotification = AppDataManager.getInstance()
                .getFreeRateNotification();
        if (freeRateNotification >= 0 && numberOpenApp < freeRateNotification) {
            return false;
        }
        // }

        if (notificationType == Constants.NTYPE_APP) {
            // quang cao cho 1 app khac
            // final Notification objNotification = getRandomNotification();
            // if (objNotification != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(controller);
            builder.setTitle(objNotification.getTitle());
            builder.setMessage(objNotification.getDescription());
            builder.setPositiveButton(objNotification.getTryNowText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = objNotification.getURL();
                            Utils.openURL(controller, url);
                            // tang so lan click notification
                            int currClick = AppDataManager
                                    .getInstance()
                                    .getClickNotification(
                                            objNotification.getNotificationId());
                            AppDataManager.getInstance().saveClickNotification(
                                    objNotification.getNotificationId(),
                                    currClick + 1);
                        }
                    });
            builder.setNegativeButton(objNotification.getCancelText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            if (controller != null && !controller.isFinishing())
                builder.create().show();
            // tang so lan hien thi notification
            int currImpression = AppDataManager.getInstance()
                    .getImpressionNotification(
                            objNotification.getNotificationId());
            AppDataManager.getInstance().saveImpressionNotification(
                    objNotification.getNotificationId(), currImpression + 1);
            // }
        } else if (notificationType == Constants.NTYPE_CLEVERNET) {

        } else if (notificationType == Constants.NTYPE_VIEWADS) {
            AlertDialog.Builder builder = new AlertDialog.Builder(controller);
            builder.setTitle(objNotification.getTitle());
            builder.setMessage(objNotification.getDescription());
            builder.setPositiveButton(objNotification.getTryNowText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (jobjData == null) {
                                int installedRewardPoints = objNotification
                                        .getInstalledRewardPoints();
                                int adFree = installedRewardPoints
                                        / ServerConfig.getInstance().getCostOneDayFreeAd();
                                boolean bReward = adFree > 0 ? true : false;
                                userJustClickTryNowWithNotificationId(
                                        objNotification.getNotificationId(),
                                        bReward);
                            } else {
                                delegate.userJustClickTryNowWithBonusData(jobjData);
                                userJustClickTryNowWithNotificationId(
                                        objNotification.getNotificationId(),
                                        true);
                            }

                            // hien quang cao interstitial o day...
//                            MediationAdHelper.getInstance().showInterstitialAd(
//                                    Constants.INTERSTITIAL_AD_TYPE_ALL,
//                                    objNotification);
                        }
                    });
            builder.setNegativeButton(objNotification.getCancelText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            if (controller != null && !controller.isFinishing())
                builder.create().show();
        } else if (notificationType == Constants.NTYPE_INAPP) {
            AlertDialog.Builder builder = new AlertDialog.Builder(controller);
            builder.setTitle("Upgrade Pro");
            builder.setMessage("Xin má»�i nÃ¢ng cáº¥p lÃªn báº£n Pro Ä‘á»ƒ loáº¡i bá»� vÄ©nh viá»…n quáº£ng cÃ¡o.");
            builder.setPositiveButton(objNotification.getTryNowText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.setNegativeButton(objNotification.getCancelText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            if (controller != null && !controller.isFinishing())
                builder.create().show();
        } else if (notificationType == Constants.NTYPE_MESSAGE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(controller);
            builder.setTitle(objNotification.getTitle());
            builder.setMessage(objNotification.getDescription());
            builder.setNegativeButton(objNotification.getCancelText(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            if (controller != null && !controller.isFinishing())
                builder.create().show();
        }

        return true;
    }

    /**
     * Kiá»ƒm tra xem hiá»‡n táº¡i app cÃ³ Ä‘ang trong giai Ä‘oáº¡n Ä‘Æ°á»£c
     * thÆ°á»Ÿng Free Ad hay khÃ´ng? Náº¿u cÃ³ thÃ¬ táº¥t cáº£ cÃ¡c loáº¡i
     * quáº£ng cÃ¡o sáº½ khÃ´ng nÃªn hiá»‡n trá»« má»™t sá»‘ TH Ä‘áº·c biá»‡t.
     * Cáº§n gá»�i hÃ m nÃ y Ä‘á»ƒ kiá»ƒm tra á»Ÿ má»—i thá»�i Ä‘iá»ƒm chuáº©n
     * bá»‹ hiá»‡n quáº£ng cÃ¡o
     *
     * @return YES/NO = in free/not free ad time
     **/
    public boolean isInAdFreeTime() {
        long adFreeExpiredTime = AppDataManager.getInstance()
                .getAdFreeExpiredTime();
        long currTime = System.currentTimeMillis();
        if (currTime < adFreeExpiredTime)
            return true;
        AppDataManager.getInstance().clearAdFreeExpiredTime();
        return false;
    }

    /**
     * Tráº£ vá»� sá»‘ giá»� free quáº£ng cÃ¡o cÃ²n láº¡i
     */
    public int getNumberOfAdFreeHoursRemain() {
        long adFreeExpiredTime = AppDataManager.getInstance()
                .getAdFreeExpiredTime();
        long currTime = System.currentTimeMillis();
        if (adFreeExpiredTime > currTime)
            return (int) (adFreeExpiredTime - currTime) / (1000 * 3600);
        return 0;
    }

    /**
     * Cá»™ng thÃªm cho user nHours giá»� Ad free
     */
    public void addAdFreeHours(int nHours) {
        long adFreeExpiredTime = AppDataManager.getInstance()
                .getAdFreeExpiredTime();
        AppDataManager.getInstance().saveAdFreeExpiredTime(
                adFreeExpiredTime + nHours * 60 * 60 * 1000);
    }

    /**
     * Khi implement delegate vÃ  sá»­ dá»¥ng Custom AlertView Ä‘á»ƒ show
     * notification, khi ngÆ°á»�i dÃ¹ng click View/Try Now pháº£i gá»�i hÃ m
     * nÃ y Ä‘á»ƒ ghi nháº­n vÃ  thá»‘ng kÃª.
     *
     * @param sNotificationID
     * @param bYesNo          : = YES/NO náº¿u user click Ä‘á»ƒ cÃ³/khÃ´ng Ä‘Æ°á»£c nháº­n
     *                        thÆ°á»Ÿng
     */
    public void userJustClickTryNowWithNotificationId(int sNotificationID,
                                                      boolean bYesNo) {

    }

    /**
     * TÆ°Æ¡ng tá»± hÃ m userJustClickTryNowWithNotificationId:(NSString
     * *)sNotificationID forReward:(BOOL)bYesNo nhÆ°ng vá»›i tham sá»‘ bYesNo =
     * NO
     */
    public void userJustClickTryNowWithNotificationId(int sNotificationID) {
        userJustClickTryNowWithNotificationId(sNotificationID, false);
    }

    // Láº¥y danh sÃ¡ch cÃ¡c notification cÃ³ há»— trá»£ loáº¡i quáº£ng cÃ¡o:
    // Icon, Banner,
    // Rectangle, Fullscreen,..
    public ArrayList<Notification> getListOfNotificationWithAdImageType(
            int adImageType) {
        return new ArrayList<Notification>();
    }

    /*
     * Láº¥y danh sÃ¡ch cÃ¡c notifications thuá»™c loáº¡i NTYPE_INAPP(app)
     *
     * @param bIncludeInstalledApps : = YES/NO bao gá»“m cáº£ app Ä‘Ã£ cÃ i
     * Ä‘áº·t/khÃ´ng bao gá»“m app Ä‘Ã£ cÃ i Ä‘áº·t
     */
    public ArrayList<Notification> getListOfNotificationForMoreApps(
            boolean bIncludeInstalledApps) {
        ArrayList<Notification> tmpArrNotifications = new ArrayList<Notification>();
        for (Notification notifi : ServerConfig.getInstance().getArrNotifications()) {
            if (notifi.getType() == Constants.NTYPE_APP) {
                if (bIncludeInstalledApps) {
                    if (notifi.getAdImage() != null
                            && !notifi.getAdImage().equals(""))
                        tmpArrNotifications.add(notifi);
                } else {
                    boolean bExisted = Utils.appInstalledOrNot(controller,
                            notifi.getAppId());
                    if (!bExisted) {
                        if (notifi.getAdImage() != null
                                && !notifi.getAdImage().equals(""))
                            tmpArrNotifications.add(notifi);
                    }
                }
            }
        }
        return tmpArrNotifications;
    }

    public boolean isShouldAutoShowNotificationWhenOpenApp() {
        return shouldAutoShowNotificationWhenOpenApp;
    }

    public void setShouldAutoShowNotificationWhenOpenApp(
            boolean shouldAutoShowNotificationWhenOpenApp) {
        this.shouldAutoShowNotificationWhenOpenApp = shouldAutoShowNotificationWhenOpenApp;
    }

    private Notification getSequenceNotification(boolean checkImageUrl) {
        Notification objNotification = null;
        ArrayList<Notification> tmpArrNotifications = new ArrayList<Notification>();
        tmpArrNotifications.addAll(ServerConfig.getInstance().getArrNotifications());
        int notificationSize = tmpArrNotifications.size();
        for (int i = notificationSize - 1; i >= 0; i--) {
            Notification obj = tmpArrNotifications.get(i);
            boolean bExisted = Utils.appInstalledOrNot(controller,
                    obj.getAppId());
            if (bExisted) {
                if (i >= 0 && i < tmpArrNotifications.size()) {
                    tmpArrNotifications.remove(i);
                }
            } else {
                // ktra maxclick va max impression
                int maxClick = obj.getMaxClick();
                int maxImpression = obj.getMaxImpression();
                int savedMaxClick = AppDataManager.getInstance()
                        .getClickNotification(obj.getNotificationId());
                int savedMaxImpression = AppDataManager.getInstance()
                        .getImpressionNotification(obj.getNotificationId());
                if ((savedMaxClick >= maxClick || savedMaxImpression >= maxImpression)
                        && i < tmpArrNotifications.size())
                    tmpArrNotifications.remove(i);
            }

            if (checkImageUrl) {
                if (TextUtils.isEmpty(obj.getAdImage()) || TextUtils.isEmpty(obj.getAdRectangle())) {
                    if (i >= 0 && i < tmpArrNotifications.size()) {
                        tmpArrNotifications.remove(i);
                    }
                }
            }
        }
        int size = tmpArrNotifications.size();
        int currentIndexNotification = AppDataManager.getInstance()
                .getCurrentIndexNotification();
        if (size > 0) {
            if (currentIndexNotification >= size) {
                currentIndexNotification = 0;
                AppDataManager.getInstance().saveCurrentIndexNotification(
                        currentIndexNotification);
            }

            if (currentIndexNotification >= 0
                    && currentIndexNotification <= size - 1) {
                objNotification = tmpArrNotifications
                        .get(currentIndexNotification);
            }
        }
        return objNotification;
    }

    private Notification getRandomNotification() {
        Notification objNotification = null;
        ArrayList<Notification> tmpArrNotifications = new ArrayList<Notification>();
        tmpArrNotifications.addAll(ServerConfig.getInstance().getArrNotifications());
        for (int i = tmpArrNotifications.size() - 1; i >= 0; i--) {
            Notification obj = tmpArrNotifications.get(i);
            boolean bExisted = Utils.appInstalledOrNot(controller,
                    obj.getAppId());
            if (bExisted)
                tmpArrNotifications.remove(i);
            else {
                // ktra maxclick va max impression
                int maxClick = obj.getMaxClick();
                int maxImpression = obj.getMaxImpression();
                int savedMaxClick = AppDataManager.getInstance()
                        .getClickNotification(obj.getNotificationId());
                int savedMaxImpression = AppDataManager.getInstance()
                        .getImpressionNotification(obj.getNotificationId());
                if (savedMaxClick >= maxClick
                        || savedMaxImpression >= maxImpression)
                    tmpArrNotifications.remove(i);
            }
        }
        int size = tmpArrNotifications.size();
        if (size > 0) {
            int randomIndex = Utils.getRandomNumber(0, size - 1, new Random());
            if (randomIndex >= 0 && randomIndex <= size - 1)
                objNotification = tmpArrNotifications.get(randomIndex);
        }
        return objNotification;
    }

    public ArrayList<Notification> getListNotifications() {
        ArrayList<Notification> tmpArrNotifications = new ArrayList<Notification>();
        tmpArrNotifications.addAll(ServerConfig.getInstance().getArrNotifications());
        for (int i = tmpArrNotifications.size() - 1; i >= 0; i--) {
            Notification obj = tmpArrNotifications.get(i);
            boolean bExisted = Utils.appInstalledOrNot(controller,
                    obj.getAppId());
            if (bExisted)
                tmpArrNotifications.remove(i);
            else {
                // ktra maxclick va max impression
                int maxClick = obj.getMaxClick();
                int maxImpression = obj.getMaxImpression();
                int savedMaxClick = AppDataManager.getInstance()
                        .getClickNotification(obj.getNotificationId());
                int savedMaxImpression = AppDataManager.getInstance()
                        .getImpressionNotification(obj.getNotificationId());
                if (savedMaxClick >= maxClick
                        || savedMaxImpression >= maxImpression)
                    tmpArrNotifications.remove(i);
            }
        }
        return tmpArrNotifications;
    }

    /*
     * @ @return YES/NO: náº¿u Ä‘á»§/khÃ´ng Ä‘iá»�u kiá»‡n Ä‘á»ƒ show
     * notification (theo config rate trong khoáº£ng [MinFreeAdRate, FreeAdRate]
     */
    public boolean isOnTimeToShowANotification() {
        boolean isOnTime = true;

        // kiem tra dk so lan mo app truoc
        int numberOpenApp = AppDataManager.getInstance().numberOpenApp;
        int freeRateANotification = AppDataManager.getInstance()
                .getFreeRateNotification();
        if (freeRateANotification >= 0 && numberOpenApp < freeRateANotification) {
            isOnTime = false;
        }

        return isOnTime;
    }

    public Notification getANotificationInfoToShow() {
        if (ServerConfig.getInstance().getArrNotifications().size() == 0)
            return null;

        if (ServerConfig.getInstance().getAds() != 1)
            return null;

        Notification objNotification = getSequenceNotification(false);
        if (!isInAdFreeTime()) {
            AppDataManager.getInstance().saveNumberOpenApp(0);
            AppDataManager.getInstance().saveFreeRateNotification(-1);
            int currentIndexNotification = AppDataManager.getInstance()
                    .getCurrentIndexNotification();
            AppDataManager.getInstance().saveCurrentIndexNotification(
                    currentIndexNotification + 1);

            if (objNotification.getType() == Constants.NTYPE_APP) {
                return objNotification;
            }
        }

        return null;
    }

    public Notification getPPCLINKNativeAd() {
        Notification objNotification = getSequenceNotification(true);
        if (objNotification != null)
            return objNotification;
        return null;
    }

}
