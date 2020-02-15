package com.example.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String STATE_SCORE_1 = "com.example.android.scorekeeper.MainActivity.STATE_SCORE1";

    public static final String STATE_SCORE_2 = "com.example.android.scorekeeper.MainActivity.STATE_SCORE2";

    private TextView mScore1TextView;
    private TextView mScore2TextVView;

    private int mScore1;
    private int mScore2;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        recreate();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.mode);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            item.setTitle(R.string.day_mode);
            item.setIcon(R.drawable.ic_day);
        }
        else {
            item.setTitle(R.string.night_mode);
            item.setIcon(R.drawable.ic_night);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScore1TextView = findViewById(R.id.scoreTeam1);
        mScore2TextVView = findViewById(R.id.scoreTeam2);

        if (savedInstanceState != null) {
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);
        }
        else {
            mScore1 = 0;
            mScore2 = 0;
        }

        mScore1TextView.setText(mScore1 >= 0 ? String.format("%02d", mScore1) : String.format("%03d", mScore1));
        mScore2TextVView.setText(mScore2 >= 0 ? String.format("%02d", mScore2) : String.format("%03d", mScore2));
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_SCORE_1, mScore1);
        outState.putInt(STATE_SCORE_2, mScore2);
        super.onSaveInstanceState(outState);
    }

    public void decreaseScore(View view) {
        switch (view.getId()) {
            case R.id.decreaseTeam1:
                mScore1--;
                mScore1TextView.setText(mScore1 >= 0 ? String.format("%02d", mScore1) : String.format("%03d", mScore1));
                break;
            case R.id.decreaseTeam2:
                mScore2--;
                mScore2TextVView.setText(mScore2 >= 0 ? String.format("%02d", mScore2) : String.format("%03d", mScore2));
        }
    }

    public void increaseScore(View view) {
        switch (view.getId()) {
            case R.id.increaseTeam1:
                mScore1++;
                mScore1TextView.setText(mScore1 >= 0 ? String.format("%02d", mScore1) : String.format("%03d", mScore1));
                break;
            case R.id.increaseTeam2:
                mScore2++;
                mScore2TextVView.setText(mScore2 >= 0 ? String.format("%02d", mScore2) : String.format("%03d", mScore2));
        }
    }
}
