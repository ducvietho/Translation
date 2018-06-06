package com.catviet.android.translation.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Share App"); //email
        sharingIntent.putExtra(Intent.EXTRA_TEXT, PLAY_STORE_LINK+title); //facebook,
        sharingIntent.setType("text/plain");
        ctx.startActivity(Intent.createChooser(sharingIntent, "Share App"));
    }
    public void feedBack(Context context){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@ppclink.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, " Feedback for app ");
        intent.putExtra(Intent.EXTRA_TEXT, "Do you like this app -  Please provide us feedback for this app. \n" + "Thank you!");
        context.startActivity(intent);
    }
}
