package com.ngamolsky.android.materialme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.materialme.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView sportsTitle = findViewById(R.id.titleDetail);
        ImageView sportsImage = findViewById(R.id.sportsImageDetail);
        Intent intent = getIntent();
        sportsTitle.setText(intent.getStringExtra("title"));
        Glide.with(this)
                .load(intent.getIntExtra("image_resource", 0))
                .into(sportsImage);
    }
}
