package com.example.transitionanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private static final String TRANSITION_TYPE = "com.example.transitionanitmation.MainActivity.TRANSITION";
    private static final String ANDROID_TRANSITION = "switchAndroid";
    private static final String BLUE_TRANSITION = "switchBlue";
    private final ImageButton[] buttons = new ImageButton[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttons[0] = findViewById(R.id.block1);
        buttons[1] = findViewById(R.id.block2);
        buttons[2] = findViewById(R.id.block4);

        if (getIntent().hasExtra(TRANSITION_TYPE)) {
            switch (getIntent().getStringExtra(TRANSITION_TYPE)) {
                case "Explode":
                    getWindow().setEnterTransition(new Explode());
                    break;
                case "Fade":
                    getWindow().setEnterTransition(new Fade());
                    break;
                default:
                    break;
            }
        }
    }

    public void explode(View view) {
        Intent intent = new Intent(this, this.getClass());
        intent.putExtra(TRANSITION_TYPE, "Explode");
        getWindow().setExitTransition(new Explode());
        startActivity(intent,
                ActivityOptions
                        .makeSceneTransitionAnimation(this)
                        .toBundle());
    }

    public void fade(View view) {
        Intent intent = new Intent(this, this.getClass());
        intent.putExtra(TRANSITION_TYPE, "Fade");
        getWindow().setExitTransition(new Fade());
        startActivity(intent,
                ActivityOptions
                    .makeSceneTransitionAnimation(this)
                    .toBundle());
    }

    public void rotate(View view) {
        final ObjectAnimator animator = ObjectAnimator.ofFloat(
                view, View.ROTATION, 0f, 360f);
        animator.setDuration(1000);
        animator.setRepeatCount(1);
        animator.start();
    }

    public void swap(View view) {
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this,
                        Pair.create(view, ANDROID_TRANSITION),
                        Pair.create((View)buttons[1], BLUE_TRANSITION));
        startActivity(new Intent(this, this.getClass()), options.toBundle());
    }
}
