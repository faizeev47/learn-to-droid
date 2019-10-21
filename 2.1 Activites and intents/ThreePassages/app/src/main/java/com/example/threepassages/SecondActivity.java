package com.example.threepassages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView mPassageTextView = findViewById(R.id.passage);
        Intent intent = getIntent();
        String choice = intent.getStringExtra(MainActivity.EXTRA_CHOICE);
        Log.d(LOG_TAG, "Choice: " + choice);
        switch (choice) {
            case "Text One":
                mPassageTextView.setText(R.string.text_one);
                break;
            case "Text Two":
                mPassageTextView.setText(R.string.text_two);
                break;
            case "Text Three":
                mPassageTextView.setText(R.string.text_three);
                break;
        }
    }
}
