package com.walhalla.pillfinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.walhalla.ui.DLog;

/**
 * Created by combo on 28.08.2016.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    final static String ACTION_NETWORK = "network";
    private static final String KEY_STATUS = "n_status";

    //Network status
    public static final int TYPE_CONNECTING = 2;
    public static int TYPE_CONNECTED = 1;
    public static int TYPE_DISCONNECTED = 0;


    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getExtras() != null) {
                ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = null;
                if (connManager != null) {
                    ni = connManager.getActiveNetworkInfo();
                }
                // NetworkInfo ni=(NetworkInfo)
                // intent.getExtras().get(ConnectivityManager.EXTRA_EXTRA_INFO);
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                    Intent int_a = new Intent();
                    int_a.setAction(ACTION_NETWORK);
                    int_a.putExtra(KEY_STATUS, TYPE_CONNECTED);
                    context.sendBroadcast(int_a);
                }
                else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                    Intent int_b = new Intent();
                    int_b.setAction(ACTION_NETWORK);
                    int_b.putExtra(KEY_STATUS, TYPE_DISCONNECTED);
                    context.sendBroadcast(int_b);
                }
                else if (ni != null && ni.getState() == NetworkInfo.State.CONNECTING) {
                    Intent int_c = new Intent();
                    int_c.setAction(ACTION_NETWORK);
                    int_c.putExtra(KEY_STATUS, TYPE_CONNECTING);
                    context.sendBroadcast(int_c);
                }
                else {
                    Intent int_c = new Intent();
                    int_c.setAction(ACTION_NETWORK);
                    NetworkInfo.State st;
                    if (ni != null) {
                        st = ni.getState();
                        int_c.putExtra(KEY_STATUS, st.name());
                        context.sendBroadcast(int_c);
                    }
                }
            }
        } catch (Exception e) {
            DLog.handleException(e);
        }
    }
}