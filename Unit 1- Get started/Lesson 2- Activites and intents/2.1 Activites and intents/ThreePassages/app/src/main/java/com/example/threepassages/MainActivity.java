package com.example.threepassages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    public final static String EXTRA_CHOICE = "com.example.android.threepassages.extra.CHOICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSecondActivity(View view) {
        Button buttonPressed = (Button)view;
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_CHOICE, buttonPressed.getText());
        startActivity(intent);
    }
}
