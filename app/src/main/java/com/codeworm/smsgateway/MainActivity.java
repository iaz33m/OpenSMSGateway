package com.codeworm.smsgateway;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codeworm.smsgateway.Managers.SharedPreferencesManager;
import com.codeworm.smsgateway.models.User;
import com.codeworm.smsgateway.services.SmsSendingService;
import com.codeworm.smsgateway.Managers.VolleyRequestManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private SharedPreferencesManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pm = new SharedPreferencesManager(this);

        if(isMyServiceRunning(SmsSendingService.class)){
                TextView txt_status = (TextView) findViewById(R.id.txt_status);
                Button btn_toggleService = (Button) findViewById(R.id.btn_toggleService);
                btn_toggleService.setText("Stop");
                txt_status.setText("Running");
            }
        this.startCountUpdater();
    }

    private void startCountUpdater(){
        final TextView txt_count = (TextView) findViewById(R.id.txt_count);
        final Handler handler = new Handler();

        TimerTask countUpdater = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int count = pm.getInt("messages_count");

                        Log.i(TAG,"Updating messages count in countUpdater Timer with count of: "+count);

                        txt_count.setText(count+"");
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(countUpdater,0L,500L);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void toggleService(View view){
        TextView txt_status = (TextView) findViewById(R.id.txt_status);
        Button btn_toggleService = (Button) findViewById(R.id.btn_toggleService);

        if(!isMyServiceRunning(SmsSendingService.class)){
            startService(new Intent(this, SmsSendingService.class));
            btn_toggleService.setText("Stop");
            txt_status.setText("Running");
        }else{
            stopService(new Intent(this, SmsSendingService.class));
            btn_toggleService.setText("Start");
            txt_status.setText("Stopped");
        }
    }

}
