package com.codeworm.smsgateway.models;

import android.content.Context;


import com.codeworm.smsgateway.Managers.SharedPreferencesManager;

import java.io.Serializable;

/**
 * Created by azeem on 8/13/2017.
 */

public class APIModel implements Serializable {

    protected Context context;
    protected SharedPreferencesManager pm;

    public static String HOST = "http://sg.infocaliper.com";

    public APIModel(){
        context = null;
        pm  = null;
    }

    public APIModel(Context context){
        this.context = context;
        this.pm = new SharedPreferencesManager(context);
    }

    public APIModel(SharedPreferencesManager pm){
        context =null;
        this.pm = pm;
    }
    public void save(){}
}
