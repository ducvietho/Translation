package com.catviet.android.translation.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.catviet.android.translation.R;

import static com.catviet.android.translation.utils.Constants.MARKET_DETAILS_ID;
import static com.catviet.android.translation.utils.Constants.PLAY_STORE_LINK;
import static com.catviet.android.translation.utils.Constants.PLAY_STORE_PPCLINK;


/**
 * Created by ducvietho on 15/5/2018.
 */

public class AppUtil {
    public AppUtil() {
    }

    public  void goToStore(Context context, String appId) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_DETAILS_ID+appId)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK +appId)));
        }
    }
    public  void moreApp(Context context, String appId) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_PPCLINK)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK +appId)));
        }
    }
    public  void shareText(Context ctx,  String title) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this amazing Translation app!"); //email
        StringBuilder sb = new StringBuilder();
        sb.append("I am using this voice and text translator app and it allows me to communicate effectively in any corner of the globe. Download it now: ");
        sb.append('\n');
        sb.append("iOS:https://apple.co/2JTZOlM");
        sb.append('\n');
        sb.append("Android:https://goo.gl/t9QQsh");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, sb.toString()); //facebook,
        sharingIntent.setType("text/plain");
        ctx.startActivity(Intent.createChooser(sharingIntent, "Share App"));
    }
    public void feedBack(Context context){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@ppclink.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, " Feedback for Translation app 1.0 Android 6.0.1 ");
        intent.putExtra(Intent.EXTRA_TEXT,context.getResources().getString(R.string.feedback_app));
        context.startActivity(intent);
    }
}
