package com.codeworm.smsgateway.services;

import android.app.Service;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.telephony.SmsManager;
import android.util.Log;

import com.android.volley.VolleyError;
import com.codeworm.smsgateway.Handlers.SuccessHandler;
import com.codeworm.smsgateway.Handlers.VolleyErrorHandler;
import com.codeworm.smsgateway.Managers.SharedPreferencesManager;
import com.codeworm.smsgateway.Managers.VolleyRequestManager;
import com.codeworm.smsgateway.models.MessageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by azeem on 5/3/2017.
 */

public class SmsSendingService extends Service {

    static final public String GET_MESSAGES_ENDPOINT = "/api/v1/messages";

    private final String TAG = "SmsSendingService";
    int messages_count;

    private TimerTask scanTask;
    private Timer t ;
    private SharedPreferencesManager pm;
    private VolleyRequestManager vrm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        super.onCreate();
        this.t = new Timer();
        pm = new SharedPreferencesManager(this);
        vrm = new VolleyRequestManager(this);

    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        messages_count = pm.getInt("messages_count");
        this.scanTask = new TimerTask() {
            @Override
            public void run() {


                int user_id = pm.getInt("id");
                System.out.println("Service Started");

                Map<String,String> params = new HashMap<>();
                params.put("user_id",String.valueOf(user_id));


                vrm.makeGetRequest(GET_MESSAGES_ENDPOINT+"?user_id="+user_id,null, new SuccessHandler() {
                    @RequiresApi(api = Build.VERSION_CODES.DONUT)
                    @Override
                    public void onSuccess(String response) {

                        try {
                            JSONObject o = new JSONObject(response);
                            if (o.getBoolean("success")) {

                                Log.i(TAG,"Response: "+response);
                                ArrayList<MessageModel> messages = MessageModel.getMessages(o.getJSONArray("messages"),getApplicationContext());

                                for (MessageModel message:messages){

                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(message.getNumber(),null, message.getMessage(), null, null);

                                    messages_count++;
                                    Log.i(TAG,"Adding Count because message got from server: "+messages_count);
                                    pm.add("messages_count",messages_count);
                                }
                            }else {
                                Log.i(TAG,o.getString("message"));
                            }
                        } catch (JSONException e) {
                            Log.i(TAG,"Please report This Bug : " + e.getMessage());
                        }

                    }
                }, new VolleyErrorHandler() {
                    @Override
                    public void onError(VolleyError e) {
                        Log.i(TAG,"Error while Connecting to Server, please try again !");
                    }
                });


            }
        };

        this.t.schedule(this.scanTask,0,5000);

        return START_STICKY_COMPATIBILITY;
    }

    public void onDestroy(){
        System.out.println("Service Stopped");
        scanTask.cancel();
        super.onDestroy();
    }

}
