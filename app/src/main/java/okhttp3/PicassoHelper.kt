package okhttp3

//import com.jakewharton.picasso.OkHttp3Downloader;
import android.app.ActivityManager
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.walhalla.pillfinder.R

class PicassoHelper private constructor() {
    private val Android_4_4_enable_ = true

    private fun getCustomPicasso(context: android.content.Context): Picasso {
        val var0: Picasso.Builder
        if (Android_4_4_enable_) {
            val builder: OkHttpClient.Builder = OkHttpClient.Builder() //.addInterceptor(logging)
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(
                    com.walhalla.pillfinder.Constants.KEY_TIMEOUT,
                    java.util.concurrent.TimeUnit.SECONDS
                )
                .writeTimeout(
                    com.walhalla.pillfinder.Constants.KEY_TIMEOUT,
                    java.util.concurrent.TimeUnit.SECONDS
                )
                .readTimeout(
                    com.walhalla.pillfinder.Constants.KEY_TIMEOUT,
                    java.util.concurrent.TimeUnit.SECONDS
                )
            val client: OkHttpClient = NetworkUtil.enableTls12OnPreLollipop(builder).build()

//        client.setHostnameVerifier(new HostnameVerifier() {
//            @Override
//            public boolean verify(String s, SSLSession sslSession) {
//                return true;
//            }
//        });
//        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
//            @Override
//            public void checkClientTrusted(
//                    java.security.cert.X509Certificate[] x509Certificates,
//                    String s) throws java.security.cert.CertificateException {
//            }
//
//            @Override
//            public void checkServerTrusted(
//                    java.security.cert.X509Certificate[] x509Certificates,
//                    String s) throws java.security.cert.CertificateException {
//            }
//
//            @Override
//            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                return new java.security.cert.X509Certificate[] {};
//            }
//        } };
//        try {
//            SSLContext sc = SSLContext.getInstance("TLS");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            client.setSslSocketFactory(sc.getSocketFactory());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
            val okBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(5, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(5, java.util.concurrent.TimeUnit.SECONDS)

            //
//        OkHttpClient client = NetworkUtil.enableTls12OnPreLollipop(okBuilder).build();
//
            var0 = Picasso.Builder(context)
                .downloader(OkHttp3Downloader(client))
                .listener(Picasso.Listener { picasso: Picasso?, uri: android.net.Uri?, exception: java.lang.Exception? ->
                    com.walhalla.ui.DLog.handleException(
                        exception
                    )
                })
        } else {
            var0 = Picasso.Builder(context)
        }

        //set 12% of available app memory for image cache
        var0.memoryCache(com.squareup.picasso.LruCache(getBytesForMemCache(14, context)))
        //set request transformer
        val requestTransformer: Picasso.RequestTransformer =
            Picasso.RequestTransformer { request: Request? -> request }
        var0.requestTransformer(requestTransformer)
        return var0.build()
    }

    private fun getBytesForMemCache(percent: Int, context: android.content.Context): Int {
        val mi: ActivityManager.MemoryInfo = ActivityManager.MemoryInfo()
        val activityManager: ActivityManager =
            context.getSystemService(android.content.Context.ACTIVITY_SERVICE) as ActivityManager
        if (activityManager != null) {
            activityManager.getMemoryInfo(mi)
        }
        val availableMemory: Double = mi.availMem.toDouble()
        return (percent * availableMemory / 100).toInt()
    }

    fun loadFullImages(
        context: android.content.Context?,
        icon: String?,
        imageView: android.widget.ImageView?
    ) {
        if (icon != null && !icon.isEmpty()) {
            Picasso.with(context)
                .load(icon) //.placeholder(R.drawable.ic_not_available)
                .noFade()
                .resize(iSize, iSize)
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.ic_not_available)
                .into(imageView, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                    }

                    override fun onError() {
                        //Try again online if cache failed
                        Picasso.with(context)
                            .load(icon)
                            .error(R.drawable.ic_not_available) //.fit().centerCrop()
                            //                                    .resize(800, 400) // Width and Height
                            //                                    .centerCrop() // Image scaling type
                            //                                    .onlyScaleDown()


                            .into(imageView, object : com.squareup.picasso.Callback {
                                override fun onSuccess() {
                                }

                                override fun onError() {
                                    com.walhalla.ui.DLog.d("Could not fetch image")
                                }
                            })
                    }
                })
        }
    }

    fun loadFullImagesErr(context: android.content.Context?, view: android.widget.ImageView?) {
        Picasso.with(context)
            .load(R.drawable.ic_not_available)
            .noFade()
            .resize(iSize, iSize)
            .centerCrop()
            .into(view, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                }

                override fun onError() {
                    //Try again online if cache failed
                    Picasso.with(context)
                        .load(R.drawable.ic_not_available)
                        .error(R.drawable.ic_not_available) //.fit().centerCrop()

                        .resize(iSize, iSize) // Width and Height
                        .centerCrop() // Image scaling type
                        .onlyScaleDown()


                        .into(view, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                            }

                            override fun onError() {
                                com.walhalla.ui.DLog.d("Could not fetch image")
                            }
                        })
                }
            })
    }

    fun loadThumbImages(
        context: android.content.Context?,
        icon: String?,
        imageView: android.widget.ImageView?
    ) {
        Picasso.with(context)
            .load(icon)
            .placeholder(R.drawable.placeholder)
            .noFade()
            .resize(iSize, iSize)
            .centerCrop()
            .networkPolicy(NetworkPolicy.OFFLINE)
            .error(R.drawable.ic_not_available)

            .into(imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                }

                override fun onError() {
                    //Try again online if cache failed
                    Picasso.with(context)
                        .load(icon)
                        .error(R.drawable.ic_not_available) //.fit().centerCrop()
                        //                                    .resize(800, 400) // Width and Height
                        //                                    .centerCrop() // Image scaling type
                        //                                    .onlyScaleDown()


                        .into(imageView, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                            }

                            override fun onError() {
                                com.walhalla.ui.DLog.d("Could not fetch image")
                            }
                        })
                }
            })
    }

    companion object {
        private var singleton: PicassoHelper? = null

        @JvmStatic
        fun getInstance(context: android.content.Context): PicassoHelper? {
            if (singleton == null) {
                singleton = PicassoHelper()
                Picasso.setSingletonInstance(singleton!!.getCustomPicasso(context))
            }
            return singleton
        }

        private const val iSize = 600
    }
}
