package com.walhalla.pillfinder.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.walhalla.Util
import com.walhalla.abcsharedlib.Share
import com.walhalla.pillfinder.BuildConfig
import com.walhalla.pillfinder.Constants
import com.walhalla.pillfinder.MyLocalStorage
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.fragment.Nlmx.NlmRxFragment
import com.walhalla.pillfinder.fragment.Nlmx.NlmRxFragment.Companion.getInstance
import com.walhalla.pillfinder.fragment.Nlmx.NlmxFragmentCallback
import com.walhalla.pillfinder.fragment.main.FragmentMain.Companion.newInstance
import com.walhalla.pillfinder.fragment.main.FragmentMain.FragmentCallback
import com.walhalla.pillfinder.fragment.main.IMainView
import com.walhalla.ui.DLog.d
import com.walhalla.ui.DLog.handleException
import com.walhalla.ui.observer.AgreementObserver
import com.walhalla.ui.plugins.DialogAbout.aboutDialog

import gov.nih.nlm.model.NlmRxImage
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class MainActivity : BaseActivity(), FragmentCallback,
    NlmxFragmentCallback, IMainView {
    private var qrCodeDialog: QRCodeDialog? = null
    private var mpm: MyLocalStorage? = null

    //private RateAppModule mRateAppModule;
    private var replyStatus: TextView? = null


    //    private AdvertAdmobRepository mAdmobRepository;
    override fun replaceFragment(fragment: Fragment) {
        try {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override fun setMainTitle(var1: String, var2: Spannable) {
        if (supportActionBar != null) {
            //            SpannableStringBuilder builder = new SpannableStringBuilder(var1);
//            builder.setSpan(new BackgroundColorSpan(Color.RED), 0, var1.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
//            builder.setSpan(new ForegroundColorSpan(Color.WHITE), 0, var1.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
//            getSupportActionBar().setTitle(builder);

            supportActionBar!!.title = var1

            //            SpannableStringBuilder sp = new SpannableStringBuilder(var2);
////            sp.setSpan(new BackgroundColorSpan(Color.RED), 0, var2.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
////            sp.setSpan(new ForegroundColorSpan(Color.WHITE), 0, var2.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
////            sp.setSpan(new UnderlineSpan(), 0, var2.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
////            getSupportActionBar().setSubtitle(sp);
            supportActionBar!!.subtitle = var2
        }
    }

    override fun mSnackbar(message: Int) {
        mSnackbar(getString(message))
    }

    override fun showMoreInfo(s: String) {
        val fragment = getInstance(s)
        replaceFragment(fragment)
    }

    override fun mSnackbar(message: String) {
        val snackbar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_SHORT)
        snackbar.setActionTextColor(resources.getColor(android.R.color.holo_red_dark))
        snackbar.setAction("[x]", null)
        snackbar.show()
        //        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    override fun replyStatus(spanned: Spanned) {
        replyStatus!!.text = spanned
    }

    override fun handleThrowable(t: Throwable) {
        //JsonSyntaxException
        //no_parameters_reply
        val err: String?
        if (t is ConnectException) {
            err = getString(R.string.err_connection)
        } else if (t is SSLHandshakeException) {
            err = t.getLocalizedMessage()
        } else if (t is UnknownHostException) {
            err = getString(R.string.err_connection)
        } else if (t is IOException) {
            d("@" + t.message)
            err = getString(R.string.err_connection)
        } else {
            err = t.localizedMessage //getString(R.string.err_refine_query);
        }
        mSnackbar(err!!)
        d(t.javaClass.name)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*ConnectionResult: SUCCESS, SERVICE_MISSING, SERVICE_UPDATING,
        SERVICE_VERSION_UPDATE_REQUIRED, SERVICE_DISABLED, SERVICE_INVALID*/
//        switch (requestCode) {
//            case D_Shape.DIALOG_SHAPE:
//                Toast.makeText(this, "@@@", Toast.LENGTH_SHORT).show();
//                if (resultCode == Activity.RESULT_OK) {
//                    // After Ok code.
//                } else if (resultCode == Activity.RESULT_CANCELED) {
//                    // After Cancel code.
//                }
//
//                break;
//        }

//        d("onActivityResult: " + requestCode + "-->" + resultCode + "-->" + ((data == null)
//                ? "null" : data.toString()));
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replyStatus = findViewById(R.id.replyStatus)


        //mBinding.toolbar.setNavigationIcon(R.mipmap.ic_launcher);

//        mAdmobRepository = ((Application) getApplication()).bb();
//
//        //mAdmobRepository.attach(findViewById(R.id.bottom_banner), Gravity.TOP);
//
//        AdvertInteractorImpl interactor = new AdvertInteractorImpl(
//                //ThreadExecutor.getInstance(),
//                BackgroundExecutor.getInstance(),
//                MainThreadImpl.getInstance(),
//                mAdmobRepository
//        );
//
//        // Run interactor
//        interactor.selectView(mBinding.bottomBanner, this);
//        getLifecycle().addObserver(mAdmobRepository);
        //        mExtensionConfig = getExtensionConfig();
        toolbar.setNavigationOnClickListener { v: View? ->
            if (supportFragmentManager.backStackEntryCount == 0) {
                aboutDialog(this)
            } else {
                onBackPressed()
            }
        }

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }


        //        final ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setIcon(R.mipmap.ic_launcher);
////            //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
////            ab.setDisplayHomeAsUpEnabled(true);
////            ab.setDisplayShowHomeEnabled(true);
////            //ab.setDisplayShowTitleEnabled(false);
////            ab.setTitle("123"); //==  toolbar.setTitle("123");
////            ab.setMainTitle("Sub");
//        }
        mpm = MyLocalStorage.getInstance()

        //        toolbar.post(() -> {
////            try {
////                Thread.sleep(3000);
////            } catch (InterruptedException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
//
//
//            /*LifeCycle:
//             * onCreate()
//             * onPostCreate()
//             * onResume()
//             * onPostResume()
//             * ...
//             * view.post()//Ok, activity is visible...
//             * */
//
//        });
        if (savedInstanceState != null) {
            return
        }

        //        if (google_market_build()) {
//            toolbar.post(() -> Module_U.checkUpdate(MainActivity.this));
//        }

        /*
         * Brakes due to the braking fragment
         * */
        supportFragmentManager.beginTransaction()
            .add(
                R.id.container,
                newInstance()
            ) //.add(R.id.container, new FragmentDemo())//FragmentDemo.newInstance()
            .commit()


        //footer = findViewById(R.id.bottom_navigation_view);
        //footer.setVisibility(View.GONE);

//        setupToolbar();

//        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
//                .findViewById(android.R.id.content)).getChildAt(0);
//        ads.inject(viewGroup);


//        if (google_market_build()) {
//            mRateAppModule = new RateAppModule(this);
//
//            //WhiteScreen
//            getLifecycle().addObserver(mRateAppModule);
//        }
        lifecycle.addObserver(
            AgreementObserver(
                this, Constants.URL_PRIVACY_POLICY //        , getString(R.string.app_name)
            )
        )


        //checkPlayServices();
        //Demo.f1(this);
    }

    override fun aLayout(): Int {
        return R.layout.activity_main
    }


    //    private boolean checkPlayServices() {
    //        try {
    //            long versionCode = PackageInfoCompat.getLongVersionCode(getPackageManager()
    //                    .getPackageInfo(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE, 0));
    //
    //
    //            long versionCode2 = GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    //
    //            Log.i(TAG, "onCreate: " + versionCode + " --> " + versionCode2);
    //            //8705230 < 12451000
    //            //14574007 --> 12451000 up_to_date
    //            int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
    //            if (errorCode != ConnectionResult.SUCCESS) {
    //                if (GoogleApiAvailability.getInstance().isUserResolvableError(errorCode)) {
    //
    //                    //GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode,
    //                    //        PLAY_SERVICES_RESOLUTION_REQUEST).show();
    //
    //                    boolean bb = GoogleApiAvailability.getInstance().showErrorDialogFragment(
    //                            this, errorCode, PLAY_SERVICES_RESOLUTION_REQUEST, dialog -> Log.i(TAG, "onCancel: "));
    //                    GoogleApiAvailability.getInstance().showErrorNotification(this, errorCode);
    //                }
    //
    //                return false;
    //            }
    //            Log.i(TAG, "onCreate: " + errorCode);
    //            return true;
    //
    //        } catch (PackageManager.NameNotFoundException e) {
    //            Log.i(TAG, "onCreate: " + e.getLocalizedMessage());
    //            return false;
    //        }
    //    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }


    //    @Override
    //    public void setSupportActionBar(@Nullable Toolbar toolbar) {
    //        super.setSupportActionBar(toolbar);
    //    }
    //    @Override
    //    protected void onPostResume() {
    //        super.onPostResume();
    //    }
    override fun onSaveInstanceState(outState: Bundle) {
        // super.onSaveInstanceState(outState);
        // No call for super(). Bug on API Level > 11.

//        if (mRateAppModule != null && google_market_build()) {
//            mRateAppModule.appReloadedHandler();
//        }
        //outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");

        super.onSaveInstanceState(outState)
    }


    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        // Inflate the menu; this adds items to the action bar if it is present.
    //        getMenuInflater().inflate(R.menu.main, menu);
    //        return true;
    //    }
    //    @Override
    //    public void onMessageRetrieved(int id, View message) {
    //        ViewGroup viewGroup = findViewById(R.id.bottom_banner);
    //        if (viewGroup != null && message != null) {
    //            try {
    //                //M.d(message.toString());
    //                //viewGroup.removeView(message);
    //                if (message.getParent() != null) {
    //                    ((ViewGroup) message.getParent()).removeView(message);
    //                }
    //                viewGroup.addView(message);
    //            } catch (Exception e) {
    //                //M.d(e.getLocalizedMessage());
    //            }
    //        }
    //    }
    override fun onBackPressed() {
        //if (presenter.onBackPressedRequest(this)) {
        super.onBackPressed()
        //}
    }

    override fun viewProxy(adapterPosition: Int, data: NlmRxImage) {
        val value = Util.wrapperText(this, data)
        val sp = SpannableString(Html.fromHtml(value.toString()))
        //@@@DLog.d(sp.toString());
        AlertDialog.Builder(this)
            .setIcon(R.mipmap.ic_launcher)
            .setTitle("" + data.name)
            .setMessage(sp)
            .setPositiveButton(
                android.R.string.ok
            ) { dialog: DialogInterface, which: Int -> dialog.cancel() }
            .show()
    }

    override fun shareProxy(adapterPosition: Int, data: NlmRxImage) {
        val value = Util.wrapperText(this, data)
        val sp = SpannableString(Html.fromHtml(value.toString()))
        val extra = sp.toString()
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")

        intent.putExtra(
            Intent.EXTRA_SUBJECT,  //String.format(Module_U.GOOGLE_PLAY_URL, getContext().getPackageName())
            getString(R.string.app_name)
        )
        intent.putExtra(Intent.EXTRA_TEXT, extra)

        if (extra.length < 50000) {
            intent.putExtra(Share.comPinterestEXTRA_DESCRIPTION, extra)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(Intent.createChooser(intent, "" + " - " + getString(R.string.app_name)))
    }

    override fun makeQRCode(adapterPosition: Int, data: NlmRxImage) {
        var tmp = data.name
        if (TextUtils.isEmpty(tmp)) {
            tmp = data.getLabeler()
        }
        qrCodeDialog = QRCodeDialog.newInstance(tmp!!)
        if (supportFragmentManager != null) {
            qrCodeDialog?.show(supportFragmentManager, "dlg1")
        }
        d(data.toString())
    }

    override fun showProgressBar() {
        if (null != findViewById(R.id.scan_progress)) {
            (findViewById<View>(R.id.scan_progress) as ProgressBar).isIndeterminate =
                true
        }
    }

    override fun hideProgressBar() {
        if (null != findViewById(R.id.scan_progress)) {
            (findViewById<View>(R.id.scan_progress) as ProgressBar).isIndeterminate =
                false
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var isValid = true

        for (i in permissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isValid = false
                break
            }
        }

        if (requestCode == NlmRxFragment.REQUEST_CODE_TO_PRINT) {
            if (isValid) {
                val aa = supportFragmentManager.fragments
                for (fragment in aa) {
                    if (fragment is NlmRxFragment) {
                        fragment.saveDocument(this)
                    }
                }
            }
        }
    }

    companion object {
        private val DEBUG = BuildConfig.DEBUG

        private const val PLAY_SERVICES_RESOLUTION_REQUEST = 999
    }
}