package com.test.forgroundservicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyForgroundService extends Service {

    String TAG = "MyForgroundService";
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Service is running..");
            }
        }).start();

        // Put into foreground state
        int notificationID = 8100;
        final String CHANNEL_ID = "MyForgroundService";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_HIGH
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentText("Service is running")
                .setContentTitle("My Title")
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher);

        // On click handling
        int reqCode = 123;
        Intent intentToOpenTheApp = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), reqCode, intentToOpenTheApp, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(pendingIntent);

        startForeground(notificationID, notification.build());

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
