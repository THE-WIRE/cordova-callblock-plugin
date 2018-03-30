package com.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.cordova.CallbackContext;

public class PhoneCallReciever extends BroadcastReceiver {

  private final CallbackContext callbackContext;
  private final static String TAG = "CallBlock";

  public PhoneCallReciever(CallbackContext cb){
    this.callbackContext = cb;
  }

  @Override
  public void onReceive(Context context, Intent intent){
      Log.d(TAG, "Call recieved");
      TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      PhoneCallStateListener customPhoneListner = new PhoneCallStateListener(context, this.callbackContext);
      telephony.listen(customPhoneListner, PhoneStateListener.LISTEN_CALL_STATE);
  }

}
