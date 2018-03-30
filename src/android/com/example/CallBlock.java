
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
import android.content.Context;
import android.content.Intent;


//For call handling
import java.lang.reflect.Method;
import android.media.AudioManager;
import android.os.bundle;


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
    	Log.d(TAG, "Watch ended");
    }
    return true;
  }

  public void startWatch(String params){
    Log.d(TAG, "Initiating watch");
    PhoneCallReciever phoneCallReciever = new PhoneCallReciever(this.callbackContext);
    
  }
}