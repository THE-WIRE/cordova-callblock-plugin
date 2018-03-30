/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;


public class CallBlock extends CordovaPlugin {


    private static final String TAG = "CallBlock";
    private CallbackContext callBackContext;


    String [] permissions = { Manifest.permission.MODIFY_PHONE_STATE, Manifest.permission.READ_PHONE_STATE };



    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        this.callBackContext = callbackContext;
        if(action.equals("startWatch"))
        {
            if(hasPermisssion())
            {
                PluginResult r = new PluginResult(PluginResult.Status.OK);
                this.callBackContext.sendPluginResult(r);
                return true;
            }
            else {
                cordova.requestPermission(this, 1, permissions[1]);
//                PermissionHelper.requestPermissions(this, 10, permissions);
            }
//            return true;

//        }else if(action.equals("startWatch")) {

            String phrase = args.getString(0);
            Log.d(TAG, phrase);

            //Start Watching for incomming calls
            startWatch(callbackContext);

            return true;

        } else if(action.equals("stopWatch")) {
            // An example of returning data back to the web layer
            // final PluginResult result = new PluginResult(PluginResult.Status.OK, (new Date()).toString());
            // callbackContext.sendPluginResult(result);

            return true;
        }

        return false;
    }


    public void onRequestPermissionResult(int requestCode, String[] permissions,
                                          int[] grantResults) throws JSONException
    {
        PluginResult result;
        //This is important if we're using Cordova without using Cordova, but we have the geolocation plugin installed
        if(this.callBackContext != null) {
            for (int r : grantResults) {
                if (r == PackageManager.PERMISSION_DENIED) {
                    LOG.d(TAG, "Permission Denied!");
                    result = new PluginResult(PluginResult.Status.ILLEGAL_ACCESS_EXCEPTION);
                    this.callBackContext.sendPluginResult(result);
                    return;
                }

            }
            result = new PluginResult(PluginResult.Status.OK);
            this.callBackContext.sendPluginResult(result);
        }
    }

    public boolean hasPermisssion() {
        for(String p : permissions)
        {
            if(!PermissionHelper.hasPermission(this, p))
            {
                return false;
            }
        }
        return true;
    }

    /*
     * We override this so that we can access the permissions variable, which no longer exists in
     * the parent class, since we can't initialize it reliably in the constructor!
     */

    public void requestPermissions(int requestCode)
    {
        PermissionHelper.requestPermissions(this, requestCode, permissions);
    }


    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d(TAG, "Initializing CallBlock Plugin");
        }

        public void startWatch(CallbackContext callBackContext){
        Log.d(TAG, "Initiating watch");
        PhoneCallReciever phoneCallReciever = new PhoneCallReciever(callBackContext);
        Log.d(TAG, "Watch ended");
    }
}



