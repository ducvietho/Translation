package com.catviet.android.translation.screen.setting;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.catviet.android.translation.R;
import com.catviet.android.translation.screen.home.HomeActivity;
import com.catviet.android.translation.utils.AppUtil;
import com.catviet.android.translation.utils.Constants;
import com.catviet.android.translation.utils.customview.TextViewRegular;
import com.example.vdconfigppclinkadsandroid.data.ServerConfig;
import com.example.vdconfigppclinkadsandroid.notifications.Notification;
import com.example.vdconfigppclinkadsandroid.notifications.NotificationsHelper;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener,PurchasesUpdatedListener {
    @BindView(R.id.layout_rate)
    LinearLayout layoutRate;
    @BindView(R.id.layout_support)
    LinearLayout layoutSupport;
    @BindView(R.id.layout_share)
    LinearLayout layoutShare;
    @BindView(R.id.layout_upgrade)
    CardView layoutUpgrade;
    @BindView(R.id.rec_more_app)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_upgrage)
    TextViewRegular tvUpgrade;
    View v;
    private BillingClient mBillingClient;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, v);

        mBillingClient = BillingClient.newBuilder(v.getContext()).setListener(SettingFragment.this).build();
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
                        if(responseCode == BillingClient.BillingResponse.OK && purchasesList.size()>0){
                            SharedPreferences.Editor editor = v.getContext().getSharedPreferences(Constants
                                            .PRE_PREMIUM_USER,
                                    Context.MODE_PRIVATE).edit();
                            editor.putBoolean(Constants.EXTRA_IS_PREMIUM_USER,true);
                            editor.commit();
                            tvUpgrade.setVisibility(View.GONE);
                            layoutUpgrade.setVisibility(View.GONE);
                        }

                    }
                });

            }

            @Override
            public void onBillingServiceDisconnected() {

            }
        });
        layoutRate.setOnClickListener(this);
        layoutSupport.setOnClickListener(this);
        layoutShare.setOnClickListener(this);
        layoutUpgrade.setOnClickListener(this);
        setupMoreApp();
        return v;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationsHelper.getInstance().onDestroy();
    //    MediationAdHelper.getInstance().onDestroy();

    }

    @Override
    public void onResume() {
        super.onResume();
        NotificationsHelper.getInstance().onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_rate:
                new AppUtil().goToStore(v.getContext(), v.getContext().getPackageName());
                break;
            case R.id.layout_share:
                new AppUtil().shareText(v.getContext(), v.getContext().getPackageName());
                break;
            case R.id.layout_support:
                new AppUtil().feedBack(v.getContext());
                break;
            case R.id.layout_upgrade:
                setUpPurchase();
                break;
            default:
                break;
        }
    }
    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK
                && purchases != null) {
            tvUpgrade.setVisibility(View.GONE);
            layoutUpgrade.setVisibility(View.GONE);
            SharedPreferences.Editor editor = v.getContext().getSharedPreferences(com.catviet.android.translation
                    .utils.Constants.PRE_PREMIUM_USER, MODE_PRIVATE).edit();
            editor.putBoolean(com.catviet.android.translation.utils.Constants.EXTRA_IS_PREMIUM_USER,true);
            editor.commit();
            HomeActivity.mAdView.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) HomeActivity.mFrameLayout
                    .getLayoutParams();
            params.setMargins(0,0,0,(int) getResources().getDimension(R.dimen.dip_50));
            HomeActivity.mFrameLayout.setLayoutParams(params);
        }
    }
    private void setUpPurchase(){


        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                            .setSku("com.catviet.android.translation.removeads")
                            .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                            .build();
                    mBillingClient.launchBillingFlow((Activity)v.getContext(),flowParams);
                }
            }
            @Override
            public void onBillingServiceDisconnected() {

            }
        });

    }
    public void setupMoreApp() {
        final ArrayList<Notification> list = ServerConfig.getInstance().getListMoreApp();
        if (list != null && list.size() > 0) {
            int i = 0;
            while (i < list.size()) {
                if (TextUtils.isEmpty(list.get(i).getAdIconMoreApps())) {
                    list.remove(i);
                } else {
                    i++;
                }
            }
            if (list.size() > 0) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(),LinearLayoutManager.HORIZONTAL,
                        false));
                mRecyclerView.setAdapter(new MoreAppAdapter(list));
            }
        }
    }


}
