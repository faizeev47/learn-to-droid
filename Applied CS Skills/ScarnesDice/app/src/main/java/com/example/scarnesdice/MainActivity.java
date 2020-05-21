package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Logging: ";
    private Random random = new Random();
    private int currentFace;

    private int userTotalScore;
    private int computerTotalScore;

    private ImageView mDiceView;
    private TextView mUserScore;
    private TextView mComputerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDiceView = findViewById(R.id.diceView);
        mDiceView.setImageDrawable(getRandomDieFace());

        mUserScore = findViewById(R.id.user_score);
        mComputerScore = findViewById(R.id.computer_score);

        userTotalScore = 0;
        computerTotalScore = 0;
        mUserScore.setText(Integer.toString(userTotalScore));
        mComputerScore.setText(Integer.toString(computerTotalScore));
    }

    public void roll(View view) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(
                mDiceView, View.ROTATION, 0f, 1800f);
        animator.setDuration(1000);
        animator.setRepeatCount(0);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDiceView.setImageDrawable(getRandomDieFace());
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
    }

    private Drawable getRandomDieFace() {
        int choice = random.nextInt(6) + 1;
        currentFace = choice;
        int drawableId = R.drawable.ic_dice_three;
        switch (choice) {
            case 1:
                drawableId = R.drawable.ic_dice_one;
                break;
            case 2:
                drawableId = R.drawable.ic_dice_two;
                break;
            case 3:
                drawableId = R.drawable.ic_dice_three;
                break;
            case 4:
                drawableId = R.drawable.ic_dice_four;
                break;
            case 5:
                drawableId = R.drawable.ic_dice_five;
                break;
            case 6:
                drawableId = R.drawable.ic_dice_six;
                break;

        }
        return getResources().getDrawable(drawableId);
    }

    public void reset(View view) {
        userTotalScore = 0;
        computerTotalScore = 0;
        mUserScore.setText(Integer.toString(userTotalScore));
        mComputerScore.setText(Integer.toString(computerTotalScore));
    }
}
