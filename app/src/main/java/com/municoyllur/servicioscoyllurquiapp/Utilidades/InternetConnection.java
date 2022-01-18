package com.municoyllur.servicioscoyllurquiapp.Utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnection{
    public Context _context;

    public InternetConnection(Context context) {
        this._context = context;
    }
    public boolean isConnectingInternet(){
        ConnectivityManager cm =
                (ConnectivityManager)this._context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


}