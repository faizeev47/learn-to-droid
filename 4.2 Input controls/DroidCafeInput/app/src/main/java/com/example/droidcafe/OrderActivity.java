package com.example.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        TextView textViewOrder = findViewById(R.id.textView_order);
        textViewOrder.setText("Order: " + getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));
    }
}
