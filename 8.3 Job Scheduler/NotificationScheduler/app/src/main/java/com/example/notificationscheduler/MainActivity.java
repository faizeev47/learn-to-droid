package com.example.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int JOB_ID = 0;

    private JobScheduler mScheduler;

    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;
    private SeekBar mDeadlineSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);
        mDeadlineSeekBar = findViewById(R.id.seekBar);
        final TextView seekbarProgress = findViewById(R.id.seekBarProgress);
        mDeadlineSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0) {
                    seekbarProgress.setText(progress  + "s");

                } else {
                    seekbarProgress.setText("Not set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void scheduleJob(View view) {
        RadioGroup networkOptions = findViewById(R.id.networkOptions);
        int selectedNetworkId = networkOptions.getCheckedRadioButtonId();
        int selectedNetworkOption;
        switch (selectedNetworkId) {
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
            default:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
        }
        boolean idleConstrained = mDeviceIdleSwitch.isChecked();
        boolean chargingConstrained = mDeviceChargingSwitch.isChecked();

        int seekBarInteger = mDeadlineSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;
        Log.d("HELLO", seekBarInteger + "");

        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(idleConstrained)
                .setRequiresCharging(chargingConstrained);

        if (seekBarSet) {
            builder.setOverrideDeadline(seekBarInteger * 100000);
        }

        boolean constraintSet = selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE
                || idleConstrained
                || chargingConstrained || seekBarSet;

        String toastMessage;
        if (constraintSet) {
            mScheduler.schedule(builder.build());
            toastMessage = "Job Scheduled, job will run when the constraints are met!";
        } else {
            toastMessage = "Please set at least one constraint";
        }
        Toast.makeText(
                this,
                toastMessage,
                Toast.LENGTH_SHORT).show();


    }

    public void cancelJobs(View view) {
        if (mScheduler != null) {
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
