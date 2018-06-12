package com.example.vdconfigppclinkadsandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.vdconfigppclinkadsandroid.data.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Utils {

    private static SharedPreferences sharedPreferences;

    public static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }


    public static int pixelsToSp(Context context, int px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity);
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device
     * density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need
     *                to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on
     * device density
     */
    public static int convertDpToPixel(int dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    /**
     * This method converts device specific pixels to density independent
     * pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static int convertPixelsToDp(int px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return (int) dp;
    }

    public static void unbindDrawable(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);

        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawable(((ViewGroup) view).getChildAt(i));
            }
            if (!(view instanceof AdapterView))
                ((ViewGroup) view).removeAllViews();
        }
    }

    /* Send email */
    public static void email(Context context, String emailTo, String emailCC,
                             String subject, String emailText) {
        // need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(
                Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{emailTo});
        emailIntent.putExtra(Intent.EXTRA_CC,
                new String[]{emailCC});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public static int getResourceIdByName(Context context, String aString) {
        int id = -1;
        try {
            id = context.getResources().getIdentifier(aString, "drawable",
                    ResourceManager.getInstance().packageName);
            return id;
        } catch (Exception e) {
            return id;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /* Ghi text ra file */
    public static void writeToFile(String text, String path) {
        BufferedWriter writer = null;
        try {
            File file = new File(path);
            if (!file.exists()) { /* file chua ton tai */
                file.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(file));
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // check internet connection
    public static boolean checkInternetConnection(Context context) {
        if (context == null) return false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null
                && networkInfo.isAvailable()
                && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static String createDeviceId(Context context) {
        String imeiAndroidId = getImei_AndroidId(context)
                + ResourceManager.getInstance().packageName;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(imeiAndroidId.getBytes());

            byte byteData[] = md.digest();

            // convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getImei_AndroidId(Context context) {
        String id = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        if (id != null)
            return id;
        else
            return "";
    }

    public static int getRandomNumber(int start, int end, Random random) {
        int num = -1;
        if (start > end) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }

        long range = (long) end - (long) start + 1;
        long fraction = (long) (range * random.nextDouble());
        num = (int) (fraction + start);
        return num;
    }

    public static void getFacebookHashKey(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("FACEBOOK HASHKEY",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @param idNormal
     * @param idPress
     * @return
     */
    public static StateListDrawable createStateList(Context context,
                                                    int idNormal, int idPress) {
        Resources resources = context.getResources();
        StateListDrawable state = new StateListDrawable();
        state.addState(new int[]{android.R.attr.state_pressed},
                resources.getDrawable(idPress));
        state.addState(new int[]{-android.R.attr.state_pressed},
                resources.getDrawable(idNormal));
        return state;
    }

    public static ColorStateList createColorStateList(int colorActive,
                                                      int colorNormal) {
        return new ColorStateList(new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused}, new int[]{}},
                new int[]{colorActive, colorActive, colorNormal});
    }

    public static String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isTablet(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float widthInInches = metrics.widthPixels / metrics.xdpi;
        float heightInInches = metrics.heightPixels / metrics.ydpi;
        float screenSize = (float) Math.sqrt((double) (widthInInches
                * widthInInches + heightInInches * heightInInches));
        if (screenSize > 6)
            return true;
        else
            return false;
    }

    public static boolean appInstalledOrNot(Context mContext, String uri) {
        boolean app_installed = false;
        if (mContext != null) {
            PackageManager pm = mContext.getPackageManager();
            try {
                pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
                app_installed = true;
            } catch (PackageManager.NameNotFoundException e) {
                app_installed = false;
            }
        }
        return app_installed;
    }

    public static void openURL(Context mContext, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}