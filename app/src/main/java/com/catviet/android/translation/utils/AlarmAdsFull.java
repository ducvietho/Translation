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
    private AlarmManager manager;
    private PendingIntent pendingIntent;
    private Intent myIntent;

    public AlarmAdsFull(Context context) {
        mContext = context;
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        myIntent = new Intent(context, AlarmOnReceiver.class);
    }

    public void alarmAdsFull() {
        pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 3 * 60 * 1000, pendingIntent);
    }

    public void cancel() {


        pendingIntent = PendingIntent.getBroadcast(mContext, 0, myIntent, 0);
        manager.cancel(pendingIntent);
    }
}
