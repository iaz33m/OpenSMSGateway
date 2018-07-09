package com.codeworm.smsgateway.Managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by azeem on 9/11/2017.
 */

public class SharedPreferencesManager {

    final private String key = "com.codeworm.opensmsgateway";

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        this.context = context;
        this.preferences = this.context.getSharedPreferences(this.key, 0);
        this.editor = preferences.edit();
    }

    public void add(String key , int value){
        editor.putInt(key,value);
        editor.apply();
    }
    public void add(String key , String value){
        editor.putString(key,value);
        editor.apply();
    }
    public void add(String key , boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }

    public void add(String key , float value){
        editor.putFloat(key,value);
        editor.apply();
    }

    public void add(String key , long value){
        editor.putLong(key,value);
        editor.apply();
    }

    public void remove(String key){
        editor.remove(key);
        editor.apply();
    }

    public String getString(String key){
        return (contains(key)) ? preferences.getString(key,null) : null;
    }
    public boolean getBoolean(String key){
        return (contains(key)) ? preferences.getBoolean(key,false) : null;
    }

    public Integer getInt(String key){
        return (contains(key)) ? preferences.getInt(key,0) : null;
    }

    public Double getDouble(String key){
        Double d;
        if (contains(key)){
            d= Double.parseDouble(preferences.getString(key,null));
            return d;
        }
        return null;
    }

    public boolean contains(String key){
        return preferences.contains(key);
    }
}
