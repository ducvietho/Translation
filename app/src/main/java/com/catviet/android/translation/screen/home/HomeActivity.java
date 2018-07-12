package com.catviet.android.translation.screen.home;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.catviet.android.translation.R;
import com.catviet.android.translation.screen.camera.CameraFragment;
import com.catviet.android.translation.screen.setting.SettingFragment;
import com.catviet.android.translation.screen.text.TextFragment;
import com.catviet.android.translation.screen.voice.VoiceFragment;
import com.catviet.android.translation.service.InternetService;
import com.catviet.android.translation.utils.AlarmAdsFull;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.customview.TextViewLight;
import com.crashlytics.android.Crashlytics;
import com.example.vdconfigppclinkadsandroid.data.ServerConfig;
import com.example.vdconfigppclinkadsandroid.delegate.INotificationsHelper;
import com.example.vdconfigppclinkadsandroid.notifications.NotificationsHelper;
import com.example.vdconfigppclinkadsandroid.utils.AppDataManager;
import com.example.vdconfigppclinkadsandroid.utils.ResourceManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import io.fabric.sdk.android.Fabric;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements INotificationsHelper, PurchasesUpdatedListener {
    public static final String EXTRA_POSITION = "postion";
    public static final String EXTRA_TEXT = "text";
    @BindView(R.id.bnve)
    TabLayout tabLayout;
    @BindView(R.id.id_bgr_banner_relax)
    RelativeLayout bgrBanner;
    @BindView(R.id.id_layout_banner_relax)
    RelativeLayout layoutBannerView;
    @BindView(R.id.id_line_top_banner)
    View lineTopBanner;
    public static ViewPager mFrameLayout;
    public static AdView mAdView;
    private BillingClient mBillingClient;
    private AlarmAdsFull alarmAdsFull;
    boolean doubleBackToExitPressedOnce = false;
//    Handler handler = new Handler();
//    Runnable mRunnable;
    private int[] tabIcons = {R.drawable.icon_voice, R.drawable.icon_text, R.drawable.icon_camera, R.drawable.icon_setting,};
    int position;
    String string;

    public static Intent getIntent(Context context, int postision, String text) {
        Intent intent = new Intent(context, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(EXTRA_TEXT, text);
        intent.putExtra(EXTRA_POSITION, postision);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationsHelper.getInstance().onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        // MobileAds.initialize(this, Constants.APP_ADSMOB_ID);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.PRE_PREMIUM_USER, MODE_PRIVATE);
                boolean isPremium = sharedPreferences.getBoolean(Constants.EXTRA_IS_PREMIUM_USER,false);
                if(!isPremium && !isAppIsInBackground(HomeActivity.this)){
                    final InterstitialAd mInterstitialAd = new InterstitialAd(HomeActivity.this);
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

                handler.postDelayed(this,3*60*1000);
            }
        },3*60*1000);

        mFrameLayout = (ViewPager) findViewById(R.id.frame_layout);
        mAdView = (AdView) findViewById(R.id.ad_view);
        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();

        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
//                String purchaseToken = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP).getPurchasesList()
//                        .get(0).getPurchaseToken();
//                mBillingClient.consumeAsync(purchaseToken, new ConsumeResponseListener() {
//                    @Override
//                    public void onConsumeResponse(int responseCode, String purchaseToken) {
//
//                    }
//                });
                mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP, new PurchaseHistoryResponseListener() {
                    @Override
                    public void onPurchaseHistoryResponse(int responseCode, List<Purchase> purchasesList) {
                        if (responseCode == BillingClient.BillingResponse.OK && purchasesList.size() > 0) {
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.PRE_PREMIUM_USER, Context.MODE_PRIVATE).edit();
                            editor.putBoolean(Constants.EXTRA_IS_PREMIUM_USER, true);
                            editor.commit();
                            mAdView.setVisibility(View.GONE);
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mFrameLayout.getLayoutParams();
                            params.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.dip_50));
                            mFrameLayout.setLayoutParams(params);

                        }

                    }
                });

            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PRE_PREMIUM_USER, MODE_PRIVATE);
        boolean isPremium = sharedPreferences.getBoolean(Constants.EXTRA_IS_PREMIUM_USER, false);
        initAds(isPremium);
        if (!isPremium) {
            AdRequest adRequest = new AdRequest.Builder().addTestDevice("B1275FEF015C0FDAF096A04ADDC853BB").build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mFrameLayout.getLayoutParams();
            params.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.dip_50));
            mFrameLayout.setLayoutParams(params);

        }


        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        string = getIntent().getStringExtra(EXTRA_TEXT);

        setUpViewPager();
        Log.i("device infor:", Settings.Secure.getString(HomeActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID));

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
            return;
        }
        doubleBackToExitPressedOnce = true;
        String toast = "Press again to exit";
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationsHelper.getInstance().onDestroy();
        Runtime.getRuntime().gc();

    }

    @Override
    protected void onResume() {
        super.onResume();
        NotificationsHelper.getInstance().onResume();
    }


    @Override
    public void onGetConfigSuccess() {
        ServerConfig.getInstance().getListMoreApp();

    }

    @Override
    public void userJustClickTryNowWithBonusData(JSONObject jobjData) {

    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

    }

    private void initAds(boolean isPremiumUser) {
        ResourceManager.getInstance().initResource(HomeActivity.this);
        AppDataManager.getInstance().init(HomeActivity.this, isPremiumUser);
        NotificationsHelper.getInstance().configure(HomeActivity.this, true, getPackageName());
        NotificationsHelper.getInstance().onStart();

        //Quang cao video
//        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
//        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//        mRewardedVideoAd.setRewardedVideoAdListener(this);
//        loadRewardedVideoAd();
    }

    private void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new VoiceFragment());
        adapter.addFragment(new TextFragment());
        switch (position) {
            case 0:
                adapter.addFragment(CameraFragment.newInstance(null));
                break;
            case 2:
                adapter.addFragment(CameraFragment.newInstance(string));
                break;
        }
        adapter.addFragment(new SettingFragment());
        mFrameLayout.setAdapter(adapter);
        mFrameLayout.setCurrentItem(position);
        mFrameLayout.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(mFrameLayout);
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.blue_5);
        tabLayout.getTabAt(position).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mFrameLayout) {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.blue_5);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.gray);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });

    }

    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}
