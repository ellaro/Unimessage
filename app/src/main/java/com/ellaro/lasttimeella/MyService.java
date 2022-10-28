package com.ellaro.lasttimeella;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    private SMSReceiver mSMSreceiver;
    private IntentFilter mIntentFilter;
    public static final String CHANNEL_ID = "FGServiceChannel";
    public static final int FG_NOTIFICATION_ID = 111;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("mylog","com.ellaro.lasttimeella.MyService onStartCommand()");

        //SMS event receiver
        mSMSreceiver = new SMSReceiver();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        createFGNotification();
        return START_STICKY;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister the SMS receiver
        unregisterReceiver(mSMSreceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createFGNotification(){
        // create Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "FG Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(serviceChannel);
        }
        // start the MainActivity when notification tap
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // create Notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("FG Service Running...")
                .setContentText("Tap to Stop this FG Service!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                //.setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(FG_NOTIFICATION_ID, notification);

        registerReceiver(mSMSreceiver, mIntentFilter);


    }


}
