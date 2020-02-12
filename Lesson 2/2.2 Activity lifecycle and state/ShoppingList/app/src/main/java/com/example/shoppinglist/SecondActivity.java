package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "com.example.android.shoppinglist.extra.ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void ReturnItem(View view) {
        Button itemButton = (Button)view;
        String itemName = itemButton.getText().toString();
        Intent returnItemIntent = new Intent();
        returnItemIntent.putExtra(EXTRA_ITEM, itemName);
        setResult(RESULT_OK, returnItemIntent);
        finish();
    }
}
