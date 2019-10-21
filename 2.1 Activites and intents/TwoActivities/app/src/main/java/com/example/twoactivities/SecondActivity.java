package com.example.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.twoactivities.extra.REPLY";

    private EditText mReplyEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);
        mReplyEditText = findViewById(R.id.editText_second);
    }

    public void returnReply(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String message = mReplyEditText.getText().toString();
        intent.putExtra(EXTRA_REPLY, message);
        setResult(RESULT_OK, intent);
        finish();
    }
}
