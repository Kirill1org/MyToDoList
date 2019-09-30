package com.koromyslov.mytodolist.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification("Hey, hurry up!", "You have one hour left to do it: " + intent.getStringExtra("taskTitle"));
        notificationHelper.getManager().notify(intent.getIntExtra("taskID", 1), nb.build());

    }
}
