package com.walhalla.pillfinder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.walhalla.ui.DLog.handleException

/**
 * Created by combo on 28.08.2016.
 */
class NetworkStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (intent.extras != null) {
                val connManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                var ni: NetworkInfo? = null
                if (connManager != null) {
                    ni = connManager.activeNetworkInfo
                }
                // NetworkInfo ni=(NetworkInfo)
                // intent.getExtras().get(ConnectivityManager.EXTRA_EXTRA_INFO);
                if (ni != null && ni.state == NetworkInfo.State.CONNECTED) {
                    val int_a = Intent()
                    int_a.setAction(ACTION_NETWORK)
                    int_a.putExtra(KEY_STATUS, TYPE_CONNECTED)
                    context.sendBroadcast(int_a)
                } else if (intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                        java.lang.Boolean.FALSE
                    )
                ) {
                    val int_b = Intent()
                    int_b.setAction(ACTION_NETWORK)
                    int_b.putExtra(KEY_STATUS, TYPE_DISCONNECTED)
                    context.sendBroadcast(int_b)
                } else if (ni != null && ni.state == NetworkInfo.State.CONNECTING) {
                    val int_c = Intent()
                    int_c.setAction(ACTION_NETWORK)
                    int_c.putExtra(KEY_STATUS, TYPE_CONNECTING)
                    context.sendBroadcast(int_c)
                } else {
                    val int_c = Intent()
                    int_c.setAction(ACTION_NETWORK)
                    val st: NetworkInfo.State
                    if (ni != null) {
                        st = ni.state
                        int_c.putExtra(KEY_STATUS, st.name)
                        context.sendBroadcast(int_c)
                    }
                }
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }

    companion object {
        const val ACTION_NETWORK: String = "network"
        private const val KEY_STATUS = "n_status"

        //Network status
        const val TYPE_CONNECTING: Int = 2
        var TYPE_CONNECTED: Int = 1
        var TYPE_DISCONNECTED: Int = 0
    }
}