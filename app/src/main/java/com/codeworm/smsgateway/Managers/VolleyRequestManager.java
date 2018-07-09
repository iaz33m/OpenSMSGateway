package com.codeworm.smsgateway.Managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codeworm.smsgateway.Handlers.SuccessHandler;
import com.codeworm.smsgateway.Handlers.VolleyErrorHandler;
import com.codeworm.smsgateway.models.APIModel;

import java.util.Map;


/**
 * Created by azeem on 10/6/2017.
 */

public class VolleyRequestManager {

    private Context context;
    private  RequestQueue queue;


    public VolleyRequestManager(Context context){
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

    public void makePostRequest(String endPoint , Map<String,String> params , SuccessHandler successHandler, VolleyErrorHandler errorHandler){

        makeRequest(endPoint,params,successHandler,errorHandler,Request.Method.POST);
    }

    public void makeGetRequest(String endPoint , Map<String,String> params , SuccessHandler successHandler, VolleyErrorHandler errorHandler){

        makeRequest(endPoint,params,successHandler,errorHandler,Request.Method.GET);
    }

    private synchronized void makeRequest(final String endPoint , final Map<String,String> params , final SuccessHandler successHandler, final VolleyErrorHandler errorHandler, int method ){

        if (haveNetworkConnection()){

            StringRequest request = new StringRequest(method, APIModel.HOST + endPoint,
                    new Response.Listener<String>()  {
                        @Override
                        public void onResponse(String response) {
                            if(successHandler!=null){
                                successHandler.onSuccess(response);
                            }
                        }
                    },
                    new Response.ErrorListener()  {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (errorHandler!=null){
                                errorHandler.onError(error);
                            }
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(request);
        }

    }

    private boolean haveNetworkConnection() {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        /*if(PermissionsHelper.getPermission((Activity) context,android.Manifest.permission.ACCESS_NETWORK_STATE,
                R.string.title_network_permission,R.string.text_network_permission,1)){}
*/
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
            return haveConnectedWifi || haveConnectedMobile;

    }
}
