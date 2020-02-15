package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Void, String> {
    private WeakReference<TextView> mTextView;

    public SimpleAsyncTask(TextView tv) {
        mTextView = new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int sleepTime = new Random().nextInt(11) * 20;
        try {
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
}
