package com.example;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import org.apache.cordova.CallbackContext;

import java.lang.reflect.Method;

public class PhoneCallStateListener extends PhoneStateListener {
  private Context context;
  private CallbackContext callbackContext;
  private final static String TAG = "CallBlock";

  public PhoneCallStateListener(Context context, CallbackContext cb) {
      this.context = context;
      this.callbackContext = cb;
  }

  public void onCallStateChanged(int state, String incomingNumber){

    Log.d(TAG, "State changes, " + state);

    switch(state){
      case TelephonyManager.CALL_STATE_RINGING:

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_RING, true);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        try{
          Class clazz = Class.forName(telephonyManager.getClass().getName());
          Method method = clazz.getDeclaredMethod("getITelephony");
          method.setAccessible(true);;
          ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);

          telephonyService.silenceRinger();
          Log.d(TAG, "Call silenced : " + incomingNumber.toString());

          telephonyService.endCall();
          Log.d(TAG, "Call blocked : " + incomingNumber.toString());
        } catch(Exception e){
          Log.e(TAG, "Error : " + e.toString());
        }

        audioManager.setStreamMute(AudioManager.STREAM_RING, false);
        break;
      case PhoneCallStateListener.LISTEN_CALL_STATE:
    }

    super.onCallStateChanged(state, incomingNumber);
  }
}
