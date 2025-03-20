package com.walhalla.pillfinder

import android.content.SharedPreferences
import android.net.ConnectivityManager

import androidx.multidex.MultiDexApplication
import androidx.preference.PreferenceManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.gson.GsonBuilder
import com.walhalla.AppOpenManager

import com.walhalla.domain.repository.from_internet.AdvertAdmobRepository
import com.walhalla.domain.repository.from_internet.AdvertConfig
import com.walhalla.domen.rest.API2
import com.walhalla.lib.RxnormRepository
import com.walhalla.lib.service.RxnormApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit


//import android.app.Application;
//import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
class MyApp : MultiDexApplication(), Constants
{
    var connectivityManager: ConnectivityManager? = null
    //var wifiInfo: NetworkInfo? = null
    //var mobileInfo: NetworkInfo? = null
    var connected: Boolean = false

    override fun onCreate() {
        super.onCreate()
        val list: MutableList<String> = ArrayList()
        list.add(AdRequest.DEVICE_ID_EMULATOR)
        list.add("0FAB3538BA09A5BBFF7DBDAAB19D898A")
        list.add("82BC32045C513A294C9238653D63429A")
        val requestConfiguration = RequestConfiguration.Builder()
            .setTestDeviceIds(list)
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)
        MobileAds.initialize(this) { initializationStatus: InitializationStatus? -> }


        appOpenManager = AppOpenManager(this, getString(R.string.r1))


//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/main.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)


        //sSharedPreferences = getSharedPreferences("PREF_DATA", Context.MODE_PRIVATE);


        //ApplicationInfo applicationInfo = this.getApplicationInfo();
        //int stringId = applicationInfo.labelRes;
        //String mm = stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : this.getString(stringId);
        //DLog.d("@@x@@"+ ""+this.getString(stringId)+" | "+"");
        val config = AdvertConfig.newBuilder()
            .setAppId(getString(R.string.application_id))
            .setBannerId(getString(R.string.banner_ad_unit_id))
            .build()
        repository = AdvertAdmobRepository.getInstance(config)
    }


    companion object {
        @kotlin.jvm.JvmField
        var repository: AdvertAdmobRepository? = null
        var appOpenManager: AppOpenManager? = null
        //private static final API service1;

        @kotlin.jvm.JvmField
        val service2: API2

        /**
         * instance
         * private static Application instance = null;
         * Convenient accessor, saves having to call and cast
         * getApplicationContext()
         *
         *
         * public static Application getInstance(Context ctx) {
         * context = ctx.getApplicationContext();
         * checkInstance();
         * return instance;
         * }
         *
         *
         * private static void checkInstance() {
         * if (instance == null)
         * throw new IllegalStateException(""); //$NON-NLS-1$
         * }
         */
        //    public static API getService1() {
        //        return service1;
        //    }
        @kotlin.jvm.JvmField
        val rxnorm: RxnormApi

        //    public AdvertAdmobRepository bb() {

        @kotlin.jvm.JvmField
        var sharedPreferences: SharedPreferences? = null
        //private set


        init {
            //System.setProperty("http.keepAlive", "false");
            val gson = GsonBuilder()
                .setLenient()
                .create()

            // Configure cipher suites to demonstrate how to customize which cipher suites will be used for
            // an OkHttp request. In order to be selected a cipher suite must be included in both OkHttp's
            // connection spec and in the SSLSocket's enabled cipher suites array. Most applications should
            // not customize the cipher suites list.
//        List<CipherSuite> customCipherSuites = Arrays.asList(
//                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384,
//                CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384);
//        final ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .cipherSuites(customCipherSuites.toArray(new CipherSuite[0]))
//                .build();
//
//
//        X509TrustManager trustManager = null;
//        SSLSocketFactory customSslSocketFactory = null;

//        try {
//            trustManager = defaultTrustManager();
//            SSLSocketFactory sslSocketFactory = defaultSslSocketFactory(trustManager);
//            customSslSocketFactory = new CustomCipherSuites.DelegatingSSLSocketFactory(sslSocketFactory) {
//                @Override
//                protected SSLSocket configureSocket(SSLSocket socket) throws IOException {
//                    socket.setEnabledCipherSuites(javaNames(spec.cipherSuites()));
//                    return socket;
//                }
//            };
//        } catch (Exception e) {
//            Log.i(TAG, "create: " + e);
//        }

            //Optional
            val builder0 = OkHttpClient.Builder()
            //        if(BuildConfig.DEBUG){
//            //logger
//            okhttp3.logging.HttpLoggingInterceptor logging = new okhttp3.logging.HttpLoggingInterceptor();
//            logging.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BASIC);
//            builder0.addInterceptor(logging);
//        }
            builder0.followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(Constants.KEY_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.KEY_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.KEY_TIMEOUT, TimeUnit.SECONDS)


            //        if (customSslSocketFactory != null
//                && trustManager != null) {
//            //Android 4.x old protocol
//            builder.connectionSpecs(Collections.singletonList(spec))
//                    .sslSocketFactory(customSslSocketFactory, trustManager);
//        }


//        if(BuildConfig.DEBUG){
//            builder.addInterceptor(chain -> {
//
//                 /*   String uid = "0";
//                    long timestamp = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
//                    String signature = MD5Util.crypt(timestamp + "" + uid + MD5_SIGN);
//                    String base64encode = signature + ":" + timestamp + ":" + uid;
//                    base64encode = Base64.encodeToString(base64encode.getBytes(), Base64.NO_WRAP | Base64.URL_SAFE);
//*/
//                Request request = chain.request();
//
////            Response response = chain.proceed(request);
////            M.d(response);
//
//                ////sout(String.format("\nrequest:\n%s\nheaders:\n%s",
//                //        request.body().toString(), request.headers()));
//
//                HttpUrl url = request.url()
//
//                        .newBuilder()
//                        //.addQueryParameter("apikey", sApiKey)
//                        //.addQueryParameter("appid", getString(R.string.app_name))
//                        //.addQueryParameter("method", "getAliasList")
//                        .build();
//
//
//                request = request
//                        .newBuilder()
//                        //.addHeader("Connection", "close")
//                        //.addHeader("Authorization", "zui " + base64encode)
//                        //.addHeader("from_client", "rx-droid")
//                        .url(url)
//                        .build();
//                if (BuildConfig.DEBUG) {
//                    DLog.d(url.toString());
//                }
//                return chain.proceed(request);
//            });
//        }
            val client0 = builder0.build() //NetworkUtil.enableTls12OnPreLollipop(builder).build();
            val client1 = builder0.build() //NetworkUtil.enableTls12OnPreLollipop(builder).build();


            //        Retrofit RETROFIT_RX = new Retrofit.Builder()
//                .baseUrl(ENDPOINT_RX_IMAGE)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client1)
//                .build();
            val RETROFIT_FDA_GOV = Retrofit.Builder()
                .baseUrl(Constants.ENDPOINT_FDA_GOV)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client0)
                .build()

            //service1 = RETROFIT_RX.create(API.class);
            service2 = RETROFIT_FDA_GOV.create(API2::class.java)


            //        Request request = new Request.Builder()
//                .url("https://publicobject.com/helloworld.txt")
//                .build();
//
//        try (Response response = client1.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            //sout(response.handshake().cipherSuite());
//            //sout(response.body().string());
//        }

            // provide an instance for our static accessors
            //instance = this;
            //context = getApplicationContext();
            val RETROFIT_RX_NORM = Retrofit.Builder()
                .baseUrl(RxnormRepository.ENDPOINT_RXNAV)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client1)
                .build()

            rxnorm = RETROFIT_RX_NORM.create(RxnormApi::class.java)
        }


        @Throws(IOException::class)
        private fun convertInputStreamToString(inputStream: InputStream): String {
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            val result = StringBuilder()
            while ((bufferedReader.readLine().also { line = it }) != null) result.append(line)

            inputStream.close()
            return result.toString()
        }

        //        return AdvertAdmobRepository.getInstance(
        //                new AdvertConfig() {
        //                    @Override
        //                    public String application_id() {
        //                        return getString(R.string.application_id);
        //                    }
        //
        //                    @Override
        //                    public SparseArray<String> banner_ad_unit_id() {
        //                        SparseArray<String> map = new SparseArray<>();
        //                        map.put(R.id.bottom_banner, getString(R.string.banner_ad_unit_id));
        ////                        map.put(R.id.nlm_bottom_banner, "ca-app-pub-5111357348858303/8596124522");
        ////                        return new String[]{
        //                        //"ca-app-pub-3940256099942544/6300978111"//test
        //                        //getString(R.string.banner_ad_unit_id)
        ////                        };
        //                        return map;
        //                    }
        //
        //                    @Override
        //                    public String interstitial_ad_unit_id() {
        //                        return null;
        //                    }
        //                }
        //        );
        //    }
        //    public boolean isInterestingActivityVisible() {
        //        return isInterestingActivityVisible;
        //    }
        //    @Override
        //    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //
        //    }
        //
        //    @Override
        //    public void onActivityStarted(Activity activity) {
        //
        //    }
        //
        //    @Override
        //    public void onActivityResumed(Activity activity) {
        //        if (activity instanceof MainActivity) {
        //            //isInterestingActivityVisible = true;
        //        }
        //    }
        //
        //    @Override
        //    public void onActivityPaused(Activity activity) {
        //
        //    }
        //
        //    @Override
        //    public void onActivityStopped(Activity activity) {
        //        if (activity instanceof MainActivity) {
        //            //isInterestingActivityVisible = false;
        //        }
        //    }
        //
        //    @Override
        //    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //
        //    }
        //
        //    @Override
        //    public void onActivityDestroyed(Activity activity) {
        //
        //    }
        /*
     * @Override protected void attachBaseContext(Context base) { //Multidex
     * super.attachBaseContext(base); MultiDex.install(this); }
     */
    }
}