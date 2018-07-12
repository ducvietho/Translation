package com.catviet.android.translation.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.catviet.android.translation.utils.Constants;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by ducvietho on 25/06/2018.
 */

public class AlarmOnReceiver extends BroadcastReceiver {
    private InterstitialAd mInterstitialAd;
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PRE_PREMIUM_USER,context
                .MODE_PRIVATE);
        boolean isPremium = sharedPreferences.getBoolean(Constants.EXTRA_IS_PREMIUM_USER,false);
        if(!isPremium){
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId("ca-app-pub-4154334692952403/8161249770");
            mInterstitialAd.loadAd(new AdRequest.Builder()
                    .addTestDevice("B1275FEF015C0FDAF096A04ADDC853BB")
                    .addTestDevice("FEA7581C72C93EEABE8145B2780726A1")
                    .build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Log.e("ads:","error "+errorCode);
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the interstitial ad is closed.
                }
            });
        }

    }
}
