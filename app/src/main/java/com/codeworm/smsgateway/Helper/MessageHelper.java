package com.codeworm.smsgateway.Helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codeworm.smsgateway.R;

/**
 * Created by Muhammad Abubakar on 10/20/2017.
 */

public class MessageHelper {


    private static void makeCustomToast(LayoutInflater inflater, String message, Context context, String bColor, String textColor, int icon){

        View toastRoot = inflater.inflate(R.layout.toast, null);
        RelativeLayout container = (RelativeLayout) toastRoot.findViewById(R.id.toast_container);
        TextView txt_message = (TextView) toastRoot.findViewById(R.id.toast_message);
        ImageView img_toast = (ImageView) toastRoot.findViewById(R.id.toast_image);
        container.setBackgroundColor(Color.parseColor(bColor));
        txt_message.setText(message);
        txt_message.setTextColor(Color.parseColor(textColor));
        img_toast.setBackground(context.getResources().getDrawable(icon));
        Toast toast = new Toast(context);
        toast.setView(toastRoot);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showCustomToastSuccess(Context context, LayoutInflater inflater, String message){

        makeCustomToast(inflater,message,context,"#20ab2a", "#ffffff", R.drawable.icon_ok_3);
    }

    public static void showCustomToastError(Context context, LayoutInflater inflater, String message){
        makeCustomToast(inflater,message,context,"#d01716","#ffffff", R.drawable.icon_error_3);

    }


}
