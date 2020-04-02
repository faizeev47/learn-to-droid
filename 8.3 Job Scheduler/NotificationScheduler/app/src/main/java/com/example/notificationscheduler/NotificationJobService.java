package com.example.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService {

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    NotificationManager mNotifyManager;

    SleepTask sleepTask;
    public static long taskProgress;

    JobParameters jobParams;

    @Override
    public boolean onStartJob(JobParameters params) {
        jobParams = params;
        createNotificationChannel();
        PendingIntent contentPendingIntent = PendingIntent.getActivity(
                this,
                NOTIFICATION_ID,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your job is running!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotifyManager.notify(NOTIFICATION_ID, builder.build());
        sleepTask = new SleepTask();
        sleepTask.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (!sleepTask.completed) {
            Toast.makeText(this, "Job not completed!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void createNotificationChannel() {
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Job Service Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifications from job service");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public class SleepTask extends AsyncTask<Void, Integer, Void> {

        boolean completed;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            completed = false;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            taskProgress = values[0];
            completed = false;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(i * 1000);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            completed = true;
            jobFinished(jobParams, false);
        }
    }
}
