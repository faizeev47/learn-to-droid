package com.example.shoppinglist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final int ITEM_REQUEST = 1;

    private String[] items = new String[10];
    private int itemsSelected = 0;

    private TextView[] mList;
    private EditText mStoreEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = new TextView[10];
        mList[0] = findViewById(R.id.listItem1);
        mList[1] = findViewById(R.id.listItem2);
        mList[2] = findViewById(R.id.listItem3);
        mList[3] = findViewById(R.id.listItem4);
        mList[4] = findViewById(R.id.listItem5);
        mList[5] = findViewById(R.id.listItem6);
        mList[6] = findViewById(R.id.listItem7);
        mList[7] = findViewById(R.id.listItem8);
        mList[8] = findViewById(R.id.listItem9);
        mList[9] = findViewById(R.id.listItem10);

        if (savedInstanceState != null) {
            items = savedInstanceState.getStringArray("items");
            itemsSelected = savedInstanceState.getInt("items_selected");
            for (int i = 0; i < itemsSelected; i++) {
                mList[i].setText((i + 1) + ": " + items[i]);
            }
        }

        mStoreEditText = findViewById(R.id.editText_store);
    }


    public void OpenItems(View view) {
        if (itemsSelected < 10) {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivityForResult(intent, ITEM_REQUEST);
        }
        else {
            Toast toast = Toast.makeText(this, "No more space in cart!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                String item = data.getStringExtra(SecondActivity.EXTRA_ITEM);
                items[itemsSelected] = item;
                mList[itemsSelected++].setText(itemsSelected + ": " + item);
            }
        }
    }

    public void RemoveItem(View view) {
        if (itemsSelected > 0) {
            mList[--itemsSelected].setText("");
        }
        else {
            Toast toast = Toast.makeText(this, "Add items to cart to remove them!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (itemsSelected > 0) {
            outState.putStringArray("items", items);
            outState.putInt("items_selected", itemsSelected);
        }
    }

    public void FindStore(View view) {
        String storeStr = mStoreEditText.getText().toString();
        Uri store = Uri.parse("geo:0,0?q=" + (storeStr.toLowerCase().contains("store") ? storeStr : storeStr + "store"));
        Intent intent = new Intent(Intent.ACTION_VIEW, store);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Can't search for a store right now!");
        }
    }
}
