package com.municoyllur.servicioscoyllurquiapp.Utilidades;

import android.content.Context;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;



public class VolleySingleton {


    private static VolleySingleton volleys = null;
    private RequestQueue mRequestQueue;

    private ImageLoader mImageLoader;
    private static Context micontexto;

    private VolleySingleton(Context context) {
        micontexto = context;
        mRequestQueue = Volley.newRequestQueue(context);

    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (volleys == null) {
            volleys = new VolleySingleton(context);
        }
        return volleys;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(micontexto.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
