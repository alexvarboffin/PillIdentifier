package okhttp3

import android.os.Build
import com.walhalla.domen.rest.Tls12SocketFactory
import com.walhalla.ui.DLog.e
import com.walhalla.ui.DLog.handleException
//import okhttp3.ConnectionSpec.Builder.build
//import okhttp3.ConnectionSpec.Builder.tlsVersions
import javax.net.ssl.SSLContext

object NetworkUtil {
    fun enableTls12OnPreLollipop(client: OkHttpClient.Builder): OkHttpClient.Builder {
//        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
//            try {
//                val sc = SSLContext.getInstance("TLSv1.2")
//                sc.init(null, null, null)
//                try {
//                    client.sslSocketFactory(Tls12SocketFactory(sc.socketFactory))
//                } catch (e: Exception) {
//                    handleException(e)
//                }
//
//
//                val cs: ConnectionSpec = OkHttpClient.Builder(ConnectionSpec.MODERN_TLS)
//                    .tlsVersions(TlsVersion.TLS_1_2)
//                    .build()
//
//                val specs: MutableList<ConnectionSpec> = ArrayList()
//                specs.add(cs)
//                specs.add(ConnectionSpec.COMPATIBLE_TLS)
//                specs.add(ConnectionSpec.CLEARTEXT)
//
//                client.connectionSpecs(specs)
//            } catch (exc: Exception) {
//                e("Error while setting TLS 1.2$exc")
//            }
//        }

        return client
    }
}
