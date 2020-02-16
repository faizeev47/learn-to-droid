package com.example.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_UPDATE_NOTIFICATION = "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final int NOTIFICATION_ID = 0;

    private Button mButtonNotify;
    private Button mButtonUpdate;
    private Button mButtonCancel;

    private NotificationManager mNotifyManager;
    private NotificationReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonNotify = findViewById(R.id.notify);
        mButtonUpdate = findViewById(R.id.update);
        mButtonCancel = findViewById(R.id.cancel);
        setNotificationButtonState(true, false, false);
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled) {
        mButtonNotify.setEnabled(isNotifyEnabled);
        mButtonUpdate.setEnabled(isUpdateEnabled);
        mButtonCancel.setEnabled(isCancelEnabled);
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot!");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is a useless notification.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        return notificationBuilder;
    }

    public void sendNotification(View view) {
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID,
                updateIntent,
                PendingIntent.FLAG_ONE_SHOT);

        createNotificationChannel();
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        notificationBuilder.addAction(R.drawable.ic_action_update, "Update notification", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        setNotificationButtonState(false, true, true);
    }

    public void updateNotification(View view) {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification updated!"));
        mNotifyManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    public void updateNotification() {
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification updated!"));
        mNotifyManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    public void cancelNotification(View view) {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    public class NotificationReceiver extends BroadcastReceiver {
        public NotificationReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }
    }
}
