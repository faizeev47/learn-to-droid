package com.example.droidcafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    private String oOrderItem;

    private EditText oPickUpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        TextView textViewOrder = findViewById(R.id.textView_order);
        Intent intent = getIntent();
        oOrderItem = intent.getStringExtra(MainActivity.EXTRA_ITEM);
        textViewOrder.setText("Order: " + getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE));

        oPickUpEditText = findViewById(R.id.editText_pickup);
        if (oPickUpEditText != null) {
            oPickUpEditText.setVisibility(EditText.INVISIBLE);
            oPickUpEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        dialNumber(v.getText().toString());
                        handled = true;
                    }
                    return handled;
                }
            });
        }

        findViewById(R.id.nextday).performClick();

        Spinner spinner = findViewById(R.id.spinner_phoneType);
        if (spinner != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.labels_array,
                    android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }



    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            switch (view.getId()) {
                case R.id.sameday:
                    displayToast(getString(R.string.same_day_messenger_service));
                    oPickUpEditText.setVisibility(EditText.INVISIBLE);
                    break;
                case R.id.nextday:
                    displayToast(getString(R.string.next_day_ground_delivery));
                    oPickUpEditText.setVisibility(EditText.INVISIBLE);
                    break;
                case R.id.pickup:
                    displayToast(getString(R.string.pick_up));
                    oPickUpEditText.setVisibility(EditText.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    public void dialNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.e("Implicit intents", "Cannot open the dialer!");
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showAlert(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(OrderActivity.this);
        alertBuilder.setTitle("Confirmation");
        alertBuilder.setMessage("Are you sure you want to place the order for a " + oOrderItem + "?");
        alertBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayToast("The order is placed!");
                    }
                });
        alertBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayToast("The order is not placed.");
                    }
                });
        alertBuilder.show();
    }
}
