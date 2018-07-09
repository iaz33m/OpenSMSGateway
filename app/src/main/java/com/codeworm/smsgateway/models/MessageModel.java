package com.codeworm.smsgateway.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Infocaliper on 12/15/2017.
 */

public class MessageModel extends APIModel {

    private String number;
    private String message;

    public MessageModel(JSONObject o,Context context){
        super(context);
        try{

            this.number = o.getString("number");
            this.message= o.getString("message");

        }catch (JSONException e){
            Toast.makeText(context, "Please report This Bug: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ArrayList<MessageModel> getMessages(JSONArray messagesArray, Context context){
        ArrayList<MessageModel> tempMessages = new ArrayList<>();
        try {
            for (int i = 0; i < messagesArray.length(); i++) {
                tempMessages.add(new MessageModel(messagesArray.getJSONObject(i),context));
            }
        }catch (JSONException e){
            Toast.makeText(context, "Please report This Bug : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            tempMessages = null;
        }
        return tempMessages;
    }
}
