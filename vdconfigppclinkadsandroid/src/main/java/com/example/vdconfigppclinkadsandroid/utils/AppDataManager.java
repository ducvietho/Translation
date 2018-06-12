package com.example.vdconfigppclinkadsandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.vdconfigppclinkadsandroid.data.Constants;

public class AppDataManager {
    private static final String kPrefsName = "APP_DATA";
    private static final String kDeviceId = "DEVICE_ID";
    private static final String kUDID = "UDID";
    private static final String kInterstitialAdFreeExpiredDate = "interstitialAdFreeExpiredDate";
    private String kMaxClickNotification = "MAX_CLICK_NOTIFICATION_";
    private String kMaxImpressionNotification = "MAX_IMPRESSION_NOTIFICATION_";
    private static final String kNumberOpenApp = "NUMBER_OPEN_APP";
    private static final String kFreeRateNotification = "FREE_RATE_NOTIFICATION";
    private static final String kFreeRateANotification = "FREE_RATE_A_NOTIFICATION";
    public static final String kAdFreeExpiredTime = "AD_FREE_EXPIRED_TIME";
    public static final String kSaveNotification = "SAVE_NOTIFICATION";
    public static final String kCurrentIndexNotification = "CURRENT_INDEX_NOTIFICATION";

    public static AppDataManager instance;

    private SharedPreferences mSettings;
    private SharedPreferences.Editor mEditor;
    public String deviceId;
    public boolean isTablet;
    public String UDID;
    public int deviceType;
    public int productType;

    public long interstitialAdFreeExpiredDate;
    public int numberOpenApp;

    public boolean isPremiumUser;

    public static AppDataManager getInstance() {
        if (instance == null)
            instance = new AppDataManager();
        return instance;
    }

    public void init(Context context, boolean isPremiumUser1) {
        if (mSettings != null) {
            mSettings = null;
        }
        if (mSettings == null) {
            mSettings = context.getSharedPreferences(kPrefsName,
                    Context.MODE_PRIVATE);
            mEditor = mSettings.edit();
        }

        if (mSettings != null) {
            deviceId = mSettings
                    .getString(kDeviceId, Utils.createDeviceId(context));
        }
        isTablet = Utils.isTablet((Activity) context);
        if (isTablet)
            deviceType = Constants.DT_ANDROID_PAD;
        else
            deviceType = Constants.DT_ANDROID_PHONE;
        productType = Constants.PT_FREE;

        if (mSettings != null)
            UDID = mSettings.getString(kUDID, null);
//        interstitialAdFreeExpiredDate = mSettings.getLong(
//                kInterstitialAdFreeExpiredDate, System.currentTimeMillis());
        interstitialAdFreeExpiredDate = System.currentTimeMillis();
        if (mSettings != null)
            numberOpenApp = mSettings.getInt(kNumberOpenApp, 0);
        isPremiumUser = isPremiumUser1;
    }

    public void setIsPremiumUser(boolean isPremiumUser1) {
        isPremiumUser = isPremiumUser1;
    }

    public boolean getIsPremiumUser() {
        return isPremiumUser;
    }

    public void saveUDID(String id) {
        UDID = id;
        mEditor.putString(kUDID, UDID);
        mEditor.commit();
    }

    public void saveInterstitialAdFreeExpiredDate(long additionValue) {
        if (additionValue == -1)
            interstitialAdFreeExpiredDate = System.currentTimeMillis();
        else
            interstitialAdFreeExpiredDate += additionValue;
        if (mEditor == null)
            if (mSettings != null) {
                mEditor = mSettings.edit();
            }
        if (mEditor != null) {
            mEditor.putLong(kInterstitialAdFreeExpiredDate,
                    interstitialAdFreeExpiredDate);
            mEditor.commit();
        }
    }

    public int getClickNotification(int notificationID) {
        if (mSettings != null)
            return mSettings.getInt(kMaxClickNotification + notificationID, 0);
        return 0;
    }

    public void saveClickNotification(int notificationID, int value) {
        if (mEditor != null) {
            mEditor.putInt(kMaxClickNotification + notificationID, value);
            mEditor.commit();
        }
    }

    public int getImpressionNotification(int notificationID) {
        if (mSettings != null)
            return mSettings.getInt(kMaxImpressionNotification + notificationID, 0);
        return 0;
    }

    public void saveImpressionNotification(int notificationID, int value) {
        if (mEditor != null) {
            mEditor.putInt(kMaxImpressionNotification + notificationID, value);
            mEditor.commit();
        }
    }

    public void saveNumberOpenApp(int value) {
        if (mEditor != null) {
            numberOpenApp = value;
            mEditor.putInt(kNumberOpenApp, value);
            mEditor.commit();
        }
    }

    public int getFreeRateNotification() {
        if (mSettings != null)
            return mSettings.getInt(kFreeRateNotification, -1);
        return -1;
    }

    public void saveFreeRateNotification(int value) {
        if (mEditor != null) {
            mEditor.putInt(kFreeRateNotification, value);
            mEditor.commit();
        }
    }

    public void saveAdFreeExpiredTime(long value) {
        if (mEditor != null) {
            mEditor.putLong(kAdFreeExpiredTime, value);
            mEditor.commit();
        }
    }

    public void clearAdFreeExpiredTime() {
        if (mEditor != null) {
            mEditor.remove(kAdFreeExpiredTime);
            mEditor.commit();
        }
    }

    public long getAdFreeExpiredTime() {
        return mSettings
                .getLong(kAdFreeExpiredTime, System.currentTimeMillis());
    }

//    public String getSaveNotification() {
//        return mSettings.getString(kSaveNotification, null);
//    }

    public void clearSaveNotification() {
        mEditor.remove(kSaveNotification);
        mEditor.commit();
    }

    public void saveNotification(String value) {
        mEditor.putString(kSaveNotification, value);
        mEditor.commit();
    }

    public void saveCurrentIndexNotification(int value) {
        if (mEditor != null) {
            mEditor.putInt(kCurrentIndexNotification, value);
            mEditor.commit();
        }
    }

    public int getCurrentIndexNotification() {
        if (mEditor != null) {
            return mSettings.getInt(kCurrentIndexNotification, 0);
        }
        return 0;
    }

    public void onDestroy(){
        if (instance != null)
            instance = null;
    }

}
