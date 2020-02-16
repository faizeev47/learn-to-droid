package com.example.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    public static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction != null) {
            String toastMessage = "unknown intent action";
            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Charging...";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power unplugged!";
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    toastMessage = "Headset disconnected!";
                    if (am != null) {
                        for(AudioDeviceInfo audioDeviceInfo : am.getDevices(AudioManager.FLAG_PLAY_SOUND |
                                AudioManager.FLAG_ALLOW_RINGER_MODES
                                |AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
                                |AudioManager.FLAG_SHOW_UI
                                |AudioManager.FLAG_VIBRATE)) {
                            int type = audioDeviceInfo.getType();
                            Log.d("AUDIO", Integer.toString(type));
                            if (type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES ||
                                type == audioDeviceInfo.TYPE_AUX_LINE ||
                                type == audioDeviceInfo.TYPE_WIRED_HEADSET) {
                                toastMessage = "Headset connected!";
                                break;
                            }
                        }
                    }
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom broadcast recieved!";
                    break;
                default:
                    break;
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
