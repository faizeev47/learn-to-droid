package com.example.scarnesdice;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Logging: ";
    private Random random = new Random();

    public boolean isUserTurn;
    public int userTotalScore;
    public int computerTotalScore;
    public int turnScore;

    private ImageView mDiceView;
    private TextView mUserScore;
    private TextView mComputerScore;
    private TextView mTurn;
    private TextView mTurnScore;
    private Button mRoll;
    private Button mHold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRoll = findViewById(R.id.btn_roll);
        mHold = findViewById(R.id.btn_hold);

        mDiceView = findViewById(R.id.diceView);
        mDiceView.setImageDrawable(getResources().getDrawable(R.drawable.ic_dice_one));

        mUserScore = findViewById(R.id.user_score);
        mComputerScore = findViewById(R.id.computer_score);
        mTurn = findViewById(R.id.turn);
        mTurnScore = findViewById(R.id.turn_score);

        userTotalScore = 0;
        computerTotalScore = 0;
        turnScore = 0;

        isUserTurn = true;
        updateTurnScoreUI();
        updateTotalScoreUI();

    }

    private void updateTotalScoreUI() {
        mUserScore.setText(Integer.toString(userTotalScore));
        mComputerScore.setText(Integer.toString(computerTotalScore));
    }

    private void updateTurnScoreUI() {
        if (isUserTurn) {
            mTurn.setText(getResources().getString(R.string.your_turn));
            mTurn.setTextColor(getResources().getColor(R.color.user_color));
        } else {
            mTurn.setText(getResources().getString(R.string.my_turn));
            mTurn.setTextColor(getResources().getColor(R.color.computer_color));
        }
        mTurnScore.setText(Integer.toString(turnScore));
    }

    private void updateButtonsUI() {
        mRoll.setEnabled(isUserTurn);
        mHold.setEnabled(isUserTurn);
    }

    public void roll(View view) {
        userTurn();
    }

    private Drawable getRandomDieFace(int choice) {
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
        turnScore = 0;
        isUserTurn = true;

        updateTotalScoreUI();
        updateTurnScoreUI();
        updateButtonsUI();
    }

    public void hold(View view) {
        if (isUserTurn) {
            userTotalScore += turnScore;
        } else {
            computerTotalScore += turnScore;
        }

        isUserTurn = false;
        turnScore = 0;

        updateTotalScoreUI();
        updateTurnScoreUI();
        updateButtonsUI();
        computerTurn();
    }

    private void userTurn() {
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
                int choice = random.nextInt(6) + 1;
                mDiceView.setImageDrawable(getRandomDieFace(choice));
                if (choice == 1) {
                    turnScore = 0;
                    isUserTurn = false;
                } else {
                    turnScore += choice;
                }

                updateTurnScoreUI();

                if (!isUserTurn) {
                    userTotalScore += turnScore;
                    turnScore = 0;
                    updateTotalScoreUI();
                    computerTurn();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
    }

    private void computerTurn() {
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
                int choice = random.nextInt(6) + 1;
                mDiceView.setImageDrawable(getRandomDieFace(choice));
                if (choice == 1) {
                    turnScore = 0;
                    isUserTurn = true;
                } else {
                    turnScore += choice;
                }

                updateTurnScoreUI();

                if (isUserTurn) {
                    computerTotalScore += turnScore;
                    turnScore = 0;
                    mRoll.setEnabled(true);
                    mHold.setEnabled(true);
                    updateTotalScoreUI();
                } else {
                    if (computerTotalScore + turnScore > userTotalScore) {
                        computerTotalScore += turnScore;
                        turnScore = 0;
                        isUserTurn = true;
                        updateTurnScoreUI();
                        updateTotalScoreUI();
                        updateButtonsUI();
                    } else {
                        animator.start();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
    }
}
