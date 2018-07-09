package com.codeworm.smsgateway;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.codeworm.smsgateway.Handlers.SuccessHandler;
import com.codeworm.smsgateway.Handlers.VolleyErrorHandler;
import com.codeworm.smsgateway.Helper.PermissionsHelper;
import com.codeworm.smsgateway.Managers.SharedPreferencesManager;
import com.codeworm.smsgateway.Managers.VolleyRequestManager;
import com.codeworm.smsgateway.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Infocaliper on 12/18/2017.
 */

public class AuthActivity extends AppCompatActivity {

    private final String TAG = "AuthActivity";
    private VolleyRequestManager vrm;
    private SharedPreferencesManager pm;


    private User mUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        vrm = new VolleyRequestManager(this);
        pm = new SharedPreferencesManager(this);

        if (!pm.contains("messages_count") ){
            pm.add("messages_count",0);
        }
        if (!pm.contains("id")){
            pm.add("id",0);
        }

        int user_id = pm.getInt("id");

        if(user_id == 0){

            PermissionsHelper.getPermission(this, Manifest.permission.SEND_SMS,R.string.sms_title_permission,R.string.sms_text_permission,1);
        }else {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
    }

    public void login(View view) {

        EditText txt_username = (EditText) findViewById(R.id.txt_username);
        EditText txt_password = (EditText) findViewById(R.id.txt_password);

        final String username = txt_username.getText().toString();
        final String password = txt_password.getText().toString();

        if(!(username.equals("") || password.equals(""))){

            String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),"android_id");
            String model = Build.BRAND;

            Map<String, String> params = new HashMap<>();
            params.put("email", username);
            params.put("password", password);
            params.put("device_id", android_id);
            params.put("device_name", model);

            vrm.makePostRequest(User.LOGIN_ENDPOINT, params, new SuccessHandler() {
                @Override
                public void onSuccess(String response) {
                    try {
                        JSONObject o = new JSONObject(response);

                        if (o.getBoolean("success")) {
                            Log.i(TAG,"You have been Logged in with response "+response);

                            JSONObject userObj = o.getJSONObject("user");
                            mUser = new User(userObj,getApplicationContext());
                            mUser.save();

                            Intent i = new Intent(AuthActivity.this,MainActivity.class);
                            startActivity(i);

                            //startCountUpdader();

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
                    Log.i(TAG,e.getLocalizedMessage());
                    Log.i(TAG,e.getMessage());
                }
            });

        }else{
            Toast.makeText(this,"Username and Password is required",Toast.LENGTH_SHORT).show();
        }

    }

    public void forgotPassword(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
