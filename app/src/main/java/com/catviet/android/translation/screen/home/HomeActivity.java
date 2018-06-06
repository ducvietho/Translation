package com.catviet.android.translation.screen.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.catviet.android.translation.R;
import com.catviet.android.translation.screen.camera.CameraFragment;
import com.catviet.android.translation.screen.setting.SettingFragment;
import com.catviet.android.translation.screen.text.TextFragment;
import com.catviet.android.translation.screen.voice.VoiceFragment;
import com.catviet.android.translation.utils.Constants;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
//implements IMain,IMediationAdHelper,INotificationsHelper
public class HomeActivity extends AppCompatActivity  {
    public static final String EXTRA_POSITION = "postion";
    public static final String EXTRA_TEXT = "text";
    @BindView(R.id.bnve)
    BottomNavigationViewEx mNavigationView;
    @BindView(R.id.id_bgr_banner_relax)
    RelativeLayout bgrBanner;
    @BindView(R.id.id_layout_banner_relax)
    RelativeLayout layoutBannerView;
    @BindView(R.id.id_line_top_banner)
    View lineTopBanner;
    boolean doubleBackToExitPressedOnce = false;

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
       // NotificationsHelper.getInstance().onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PRE_PREMIUM_USER,MODE_PRIVATE);
        boolean isPremium = sharedPreferences.getBoolean(Constants.EXTRA_IS_PREMIUM_USER,false);
        //initAds(isPremium);
        mNavigationView.enableAnimation(false);
        mNavigationView.enableShiftingMode(false);
        mNavigationView.enableItemShiftingMode(false);
        mNavigationView.setTextVisibility(false);
        mNavigationView.setIconSize(30, 30);
        int position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        String string = getIntent().getStringExtra(EXTRA_TEXT);
        mNavigationView.setCurrentItem(position);
        switch (position) {
            case 0:
                startFragment(new VoiceFragment());
                break;
            case 2:
                startFragment(CameraFragment.newInstance(string));
                break;
        }
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bt_camera:
                        startFragment(CameraFragment.newInstance(null));
                        return true;
                    case R.id.bt_voice:
                        startFragment(new VoiceFragment());
                        return true;
                    case R.id.bt_text:
                        startFragment(new TextFragment());
                        return true;
                    case R.id.bt_setting:
                        startFragment(new SettingFragment());
                        return true;
                    default:
                        return true;
                }

            }
        });
        Log.i("device infor:", Settings.Secure.getString(HomeActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        //showBanner();
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
//        NotificationsHelper.getInstance().onDestroy();
//        MediationAdHelper.getInstance().onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
       //NotificationsHelper.getInstance().onResume();
    }


//    @Override
//    public void onClickBannerAd() {
//
//    }
//
//    @Override
//    public void onClickForceShowInterstitialAll() {
//
//    }
//
//    @Override
//    public void onClickForceShowInterstitialImageOnly() {
//
//    }
//
//    @Override
//    public void onClickForceShowInterstitialVideoOnly() {
//
//    }
//
//    @Override
//    public void onClickShowInterstitialConstaintServer() {
//
//    }
//
//    @Override
//    public void onClickNativeMopubAd() {
//
//    }
//
//    @Override
//    public void onClickRemoveAds() {
//
//    }
//
//    @Override
//    public void onBannerAdLoaded(View bannerView) {
//
//    }
//
//    @Override
//    public void onGetBannerError() {
//
//    }
//
//    @Override
//    public void onNetworkChange(boolean isConnected) {
//
//    }
//
//    @Override
//    public void onCompleteClickFullAds(boolean isClick) {
//
//    }
//
//    @Override
//    public void setupNativeFBAds(boolean isRemoveNative) {
//
//    }
//
//    @Override
//    public void setupNativeMopubAds(boolean isRemoveNative) {
//
//    }
//
//    @Override
//    public void setupNativeAdmobAds(boolean isRemoveNative) {
//
//    }
//
//    @Override
//    public void loadNativeAdFacebook() {
//
//    }
//
//    @Override
//    public void loadNativeAdMopub() {
//
//    }
//
//    @Override
//    public void loadNativeAdAdmob() {
//
//    }
//
//    @Override
//    public void onGetConfigSuccess() {
//        ServerConfig.getInstance().getListMoreApp();
//        MediationAdHelper.getInstance().configure(HomeActivity.this);
//        MediationAdHelper.getInstance().configureReward(HomeActivity.this);
//    }
//
//    @Override
//    public void userJustClickTryNowWithBonusData(JSONObject jobjData) {
//
//    }
//
//    private void initAds(boolean isPremiumUser) {
//        ResourceManager.getInstance().initResource(HomeActivity.this);
//        AppDataManager.getInstance().init(HomeActivity.this, isPremiumUser);
//        NotificationsHelper.getInstance().configure(HomeActivity.this, true, getPackageName());
//        NotificationsHelper.getInstance().onStart();
//
//        //Quang cao video
////        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
////        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
////        mRewardedVideoAd.setRewardedVideoAdListener(this);
////        loadRewardedVideoAd();
//    }

    private void startFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, fragment, "nextFragment");
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

//    public void showBanner() {
//
//        if (MediationAdHelper.getInstance() != null) {
//            MediationAdHelper.getInstance().getBannerView(new IListenerBannerAds() {
//                @Override
//                public void onAdLoaded() {
//                    addBanner();
//                }
//
//                @Override
//                public void onAdFailedToLoad() {
//                }
//
//                @Override
//                public void onAdClicked() {
//                }
//            });
//           addBanner();
//        }
//    }
//
//
//
//    private void addBanner() {
//        final View currentView = MediationAdHelper.getInstance().getAdView();
//        if (currentView != null) {
//            ViewGroup parent = (ViewGroup) currentView.getParent();
//            if (parent != null) parent.removeAllViews();
//            if (layoutBannerView != null) {
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                layoutBannerView.addView(currentView, lp);
//            }
//            if (bgrBanner != null) {
//                bgrBanner.setVisibility(View.VISIBLE);
//                //cập nhật giao diện: tùy biến theo ứng dụng của mình (dưới đây là 1 ví dụ)
//                currentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        currentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        int width = currentView.getWidth(); //width is ready
//                        if (width > 0) {
//                            if (lineTopBanner != null) {
//                                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) lineTopBanner.getLayoutParams();
//                                layoutParams.width = width;
//                                lineTopBanner.setLayoutParams(layoutParams);
//                            }
//                        }
//                    }
//                });
//            }
//        }
//    }
}
