package com.example.roomwordssample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class NewWordsActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = NewWordsActivity.class.getSimpleName() + "REPLY";

    private EditText mEditWordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_words);

        mEditWordView = findViewById(R.id.edit_word);
    }

    public void save(View view) {
        Intent replyIntent = new Intent();
        String word = mEditWordView.getText().toString();
        if (word.isEmpty()) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
