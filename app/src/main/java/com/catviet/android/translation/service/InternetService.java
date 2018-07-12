package com.catviet.android.translation.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.net.ConnectivityManager;
import android.view.View;

import com.catviet.android.translation.screen.camera.CameraFragment;
import com.catviet.android.translation.screen.home.HomeActivity;
import com.catviet.android.translation.screen.text.TextFragment;
import com.catviet.android.translation.screen.voice.VoiceFragment;

/**
 * Created by ducvietho on 23/06/2018.
 */

public class InternetService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null) {
            if(VoiceFragment.tvInternet!=null&& TextFragment.tvInternet!=null&&CameraFragment.tvInternet!=null){
                VoiceFragment.tvInternet.setVisibility(View.VISIBLE);
                TextFragment.tvInternet.setVisibility(View.VISIBLE);
                CameraFragment.tvInternet.setVisibility(View.VISIBLE);
            }

        } else {
            if(VoiceFragment.tvInternet!=null&& TextFragment.tvInternet!=null&&CameraFragment.tvInternet!=null){
                VoiceFragment.tvInternet.setVisibility(View.GONE);
                TextFragment.tvInternet.setVisibility(View.GONE);
                CameraFragment.tvInternet.setVisibility(View.GONE);
            }

        }
    }
}
