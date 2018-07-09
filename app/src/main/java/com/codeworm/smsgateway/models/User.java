package com.codeworm.smsgateway.models;

import android.content.Context;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Infocaliper on 12/15/2017.
 */

public class User extends APIModel {


    static final public String LOGIN_ENDPOINT = "/api/v1/auth/login";

    private int id;
    private String name;
    private String email;
    private String password;
    private String key;

    public User(JSONObject o, Context context){
        super(context);

        try{
            this.id = o.getInt("id");
            this.name = o.getString("name");
            this.email = o.getString("email");
            this.key = o.getString("key");

        }catch (JSONException e){
            Toast.makeText(context, "Please report This Bug: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void save(){



        pm.add("id",this.id);
        pm.add("name",this.name);
        pm.add("email",this.email);
        pm.add("password",this.password);
        pm.add("key",this.key);
    }

}
