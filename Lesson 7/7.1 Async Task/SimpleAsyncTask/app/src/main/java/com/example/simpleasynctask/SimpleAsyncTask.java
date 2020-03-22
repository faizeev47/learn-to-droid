package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.sql.Time;
import java.time.Clock;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class SimpleAsyncTask extends AsyncTask <Void, Long, String> {
    private WeakReference<TextView> mTextView;

    public SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int sleepTime = (new Random().nextInt(30) + 20) * 20;
        try {
            new TimerThread(currentTimeMillis(), sleepTime).start();
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Awake at last after sleeping for " + sleepTime + " milliseconds.";
    }

    @Override
    public void onPostExecute(String result) {
        mTextView.get().setText(result);
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        String message = "I have been sleeping for " + values[0] + " milliseconds.";
        mTextView.get().setText(message);
    }


    class TimerThread extends Thread {

        long startTime;
        int sleepTime;
        public TimerThread(long startTime, int sleepTime) {
            this.startTime = startTime;
            this.sleepTime = sleepTime;
        }

        public void run() {
            long timeSlept = currentTimeMillis() - startTime;
            while (timeSlept < sleepTime) {
                publishProgress(timeSlept);
                timeSlept = currentTimeMillis() - startTime;
            }
        }
    }

}
