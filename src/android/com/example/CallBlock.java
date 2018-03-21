/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.example.PhoneCallStateListener; 

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

//For call handling
import java.lang.reflect.Method;
import android.media.AudioManager;
import android.os.bundle;

import com.android.internal.telephony.ITelephony;


public class CallBlock extends CordovaPlugin {
  private static final String TAG = "CallBlock";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing CallBlock Plugin");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("startWatch")) {

      String phrase = args.getString(0);
      Log.d(TAG, phrase);

      //Start Watching for incomming calls
      startWatch(phrase);

    } else if(action.equals("stopWatch")) {
      // An example of returning data back to the web layer
      // final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
      // callbackContext.sendPluginResult(result);
    }
    return true;
  }

  public void startWatch(String params){
    Log.d(TAG, "Initiating watch");
    PhoneCallReciever phoneCallReciever = new PhoneCallReciever(this.callbackContext);
    Log.d(TAG, "Watch ended");
  }
}

public class PhoneCallReciever extends BroadcastReceiver {

  private final CallbackContext callbackContext;

  @Override
  public PhoneCallReciever(CallbackContext cb){
    this.callbackContext = cb;
  }

  @Override
  public void onRecieve(Context context, Intent intent){
      TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      PhoneCallStateListener customPhoneListner = new PhoneCallStateListener(context, this.callbackContext);
      telephony.listen(customPhoneListner, PhoneStateListener.LISTEN_CAL_STATE);
  }
}



@Override
public class PhoneCallStateListener extends PhoneStateListener {
  private Context context;
  private final CallbackContext callbackContext;

  public PhoneCallStateListener(Context context, CallbackContext cb) {
      this.context = context;
      this.callbackContext = cb;
  }

  @Override
  public void OnCallStateChanged(int state, String incomingNumber){

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

    super.OnCallStateChanged(state, incomingNumber);
  }
}



