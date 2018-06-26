package com.catviet.android.translation.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.catviet.android.translation.service.AlarmOnReceiver;

/**
 * Created by ducvietho on 25/06/2018.
 */

public class AlarmAdsFull {
    private Context mContext;

    public AlarmAdsFull(Context context) {
        mContext = context;
    }
    public void alarmAdsFull(){
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(mContext, AlarmOnReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent,0);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),3*60*1000,
                pendingIntent);
    }
}
