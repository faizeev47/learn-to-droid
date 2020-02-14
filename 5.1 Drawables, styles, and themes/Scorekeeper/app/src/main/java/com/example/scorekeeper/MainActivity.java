package com.example.scorekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mScore1TextView;
    private TextView mScore2TextVView;

    private int mScore1;
    private int mScore2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScore1TextView = findViewById(R.id.scoreTeam1);
        mScore2TextVView = findViewById(R.id.scoreTeam2);

        mScore1 = 0;
        mScore2 = 0;
    }

    public void decreaseScore(View view) {
        switch (view.getId()) {
            case R.id.decreaseTeam1:
                mScore1--;
                mScore1TextView.setText(String.format("%02d", mScore1));
                break;
            case R.id.decreaseTeam2:
                mScore2--;
                mScore2TextVView.setText(String.format("%02d", mScore2));
        }
    }

    public void increaseScore(View view) {
        switch (view.getId()) {
            case R.id.increaseTeam1:
                mScore1++;
                mScore1TextView.setText(String.format("%02d", mScore1));
                break;
            case R.id.increaseTeam2:
                mScore2++;
                mScore2TextVView.setText(String.format("%02d", mScore2));
        }
    }
}
