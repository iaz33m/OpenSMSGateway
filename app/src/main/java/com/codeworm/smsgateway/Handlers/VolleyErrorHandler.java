package com.codeworm.smsgateway.Handlers;

import com.android.volley.VolleyError;

/**
 * Created by azeem on 10/6/2017.
 */

public interface VolleyErrorHandler {
    void onError(VolleyError e);
}
