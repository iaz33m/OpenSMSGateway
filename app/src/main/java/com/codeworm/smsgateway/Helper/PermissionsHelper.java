package com.codeworm.smsgateway.Helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by Muhammad Abubakar on 11/17/2017.
 */

public class PermissionsHelper {
    public static boolean getPermission(final Activity activity, final String permission, int title, int message, final int id) {
        if (ContextCompat.checkSelfPermission(activity,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {


                new AlertDialog.Builder(activity)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{permission}, id);
                            }
                        })
                        .create()
                        .show();

            } else {

                ActivityCompat.requestPermissions(activity,
                        new String[]{permission},
                        id);

            }
            return false;
        } else {
            return true;
        }
    }

}

