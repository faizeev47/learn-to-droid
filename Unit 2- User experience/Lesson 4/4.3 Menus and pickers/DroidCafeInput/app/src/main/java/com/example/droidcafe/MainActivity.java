package com.example.droidcafe;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.android.droidcafe.mainactivity.extra.MESSAGE";
    public static final String EXTRA_ITEM = "com.example.android.droidcafe.mainactivity.extra.ITEM";

    private ImageView mDonutImageView;
    private ImageView mIcecreamImageView;
    private ImageView mFroyoImageView;

    private String mOrderMessage;
    private String mOrderItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mOrderMessage = "";
        mOrderItem = "";

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOrderMessage.length() > 0) {
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                    intent.putExtra(EXTRA_ITEM, mOrderItem);
                    startActivity(intent);
                }
                else {
                    displayToast("Order something first, will ya'?");
                }
            }
        });

        mDonutImageView = findViewById(R.id.donut);
        mIcecreamImageView = findViewById(R.id.icecream);
        mFroyoImageView = findViewById(R.id.froyo);

        registerForContextMenu(mDonutImageView);
        registerForContextMenu(mIcecreamImageView);
        registerForContextMenu(mFroyoImageView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v,  menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            displayToast("This here is a clickable picture of a food item");
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_order:
                displayToast(getString(R.string.action_order_message));
                if (mOrderMessage.length() > 0) {
                    Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                    intent.putExtra(EXTRA_ITEM, mOrderItem);
                    startActivity(intent);
                }
                else {
                    displayToast("Order something first, will ya'?");
                }

                break;
            case R.id.action_status:
                displayToast(getString(R.string.action_status_message));
                break;
            case R.id.action_favorites:
                displayToast(getString(R.string.action_favorites_message));
                break;
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Shows a message that the donut image was clicked
     * @param view
     */
    public void showDonutOrder(View view) {
        mOrderMessage = getString(R.string.donut_order_message);
        mOrderItem = getString(R.string.donut);
        displayToast(mOrderMessage);
    }

    /**
     * Shows a message that the ice cream image was clicked
     * @param view
     */
    public void showIcecreamOrder(View view) {
        mOrderMessage = getString(R.string.ice_cream_order_message);
        mOrderItem = getString(R.string.icecream);
        displayToast(mOrderMessage);
    }

    /**
     * Shows a message that the froyo image was clicked
     * @param view
     */
    public void showFroyoOrder(View view) {
        mOrderMessage = getString(R.string.froyo_order_message);
        mOrderItem = getString(R.string.froyo);
        displayToast(mOrderMessage);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
