package com.example;

import java.lang.reflect.Method;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.bundle;
import android.preference.PreferenceManager;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

@Override
public class PhoneCallStateListener extends PhoneStateListener {
    private Context context;

    public PhoneCallStateListener(Context context) {
        this.context = context;
    }
}


@Override
public void OnCallStateChanged(int state, String incomingNumber){

}