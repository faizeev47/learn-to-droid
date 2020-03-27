package com.example.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private final static String PRIMARY_CHANNEL_ID = "primary_channel_id";
    private static final int NOTIFICATION_ID = 0;

    private NotificationManager mNotificationManager;

    private Button mButtonNotify;
    private Button mButtonUpdate;
    private Button mButtonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonNotify = findViewById(R.id.notify);
        mButtonUpdate = findViewById(R.id.update);
        mButtonCancel = findViewById(R.id.cancel);

        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();
        updateUi(true, false, false);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // create a notification channel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot!");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                    .setContentTitle("You've been notified!")
                    .setContentText("This is your notification text")
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentIntent(notificationPendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);
    }

    public void sendNotification(View view) {
        mNotificationManager.notify(NOTIFICATION_ID, getNotificationBuilder().build());
        updateUi(false, true, true);
    }

    public void updateNotification(View view) {
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_bubbles);
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
            .bigPicture(androidImage)
            .setBigContentTitle("Notification Updated!"));
        mNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        updateUi(false, false, true);
    }

    public void cancelNotification(View view) {
        mNotificationManager.cancel(NOTIFICATION_ID);
        updateUi(true, false, false);
    }

    void updateUi(boolean enableNotify, boolean enableUpdate, boolean enableCancel) {
        mButtonNotify.setEnabled(enableNotify);
        mButtonUpdate.setEnabled(enableUpdate);
        mButtonCancel.setEnabled(enableCancel);
    }
}
