package com.walhalla.health.activity.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log.d
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.walhalla.boilerplate.domain.executor.impl.ThreadExecutor
import com.walhalla.boilerplate.threading.MainThreadImpl
import com.walhalla.domain.interactors.AdvertInteractor
import com.walhalla.domain.interactors.impl.AdvertInteractorImpl
import com.walhalla.domain.repository.AdvertRepository
import com.walhalla.health.IdealWeight.InnerAbstractFragment.FInnerCallback
import com.walhalla.health.R
import com.walhalla.ui.DLog
import com.walhalla.ui.DLog.handleException

abstract class InnerAdActivity : AppCompatActivity(), FInnerCallback {


    private val callback: AdvertInteractor.Callback<View> =
        object : AdvertInteractor.Callback<View> {
            override fun onMessageRetrieved(id: Int, message: View) {
                DLog.d(message.javaClass.name + " --> " + message.hashCode())
                val content: FrameLayout = findViewById(R.id.bottom_button)
                DLog.d("@@@" + content.javaClass.name)
                try {
                    //content.removeView(message);
                    if (message.parent != null) {
                        (message.parent as ViewGroup).removeView(message)
                    }
                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.gravity = Gravity.BOTTOM or Gravity.CENTER
                    message.layoutParams = params

                    val vto = message.viewTreeObserver
                    vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                        @SuppressLint("ObsoleteSdkInt")
                        override fun onGlobalLayout() {
                            if (Build.VERSION.SDK_INT < 16) {
                                message.viewTreeObserver.removeGlobalOnLayoutListener(this)
                            } else {
                                message.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            }
                            //int width = message.getMeasuredWidth();
                            //int height = message.getMeasuredHeight();
                            //DLog.i("@@@@" + height + "x" + width);
                            //setSpaceForAd(height);
                        }
                    })
                    content.addView(message)
                } catch (e: Exception) {
                    handleException(e)
                }
            }

            override fun onRetrievalFailed(error: String) {
                DLog.d("---->$error")
            }
        }

    //protected AdView mAd;
    var tvHeader: TextView? = null
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (aTheme() > 0) {
            setTheme(aTheme())
        }
        setContentView(aLayout())

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = null
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

//        tvHeader = findViewById(R.id.tvHeader);//Only in char_* activitys
//        if(tvHeader!=null){
//            tvHeader.setText("@@@@");//Text Contains in XML
//        }
        toolbar = findViewById(R.id.toolbar)
        val img_back = findViewById<ImageView>(R.id.img_back)
        img_back.setOnClickListener { v: View? -> onBackPressed() }

        //        mAd = findViewById(R.id.adView);
//        if (ADS_ENABLED) {
//            loadAd();
//        } else {
//            mAd.setVisibility(View.GONE);
//        }
        setupAdAtBottom()
    }

    protected abstract fun aLayout(): Int

    protected abstract fun aTheme(): Int


    //    @Override
    //    protected void onPause() {
    //
    //        if (ADS_ENABLED && mAd != null) {
    //            mAd.pause();
    //        } else {
    //            Log.w(TAG, "mAd has not been set");
    //        }
    //        super.onPause();
    //    }
    //    @Override
    //    protected void onResume() {
    //        super.onResume();
    //
    //        if (ADS_ENABLED && mAd != null) {
    //            mAd.resume();
    //        } else {
    //            Log.w(TAG, "mAd has not been set");
    //        }
    //
    //        DLog.d(getClass().getSimpleName());
    //    }
    //    @Override
    //    protected void onDestroy() {
    //        if (ADS_ENABLED && mAd != null) {
    //            mAd.destroy();
    //        } else {
    //            Log.w(TAG, "mAd has not been set");
    //        }
    //
    //        super.onDestroy();
    //    }
    //    protected void loadAd() {
    //
    //        if (mAd == null) {
    //            Log.d(TAG, "mAd must be set before loading an ad");
    //            return;
    //        }
    //
    ////        String[] testDeviceIds = getResources().getStringArray(R.array.admob_test_device_ids);
    ////
    //        AdRequest.Builder builder = new AdRequest.Builder();
    ////        builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
    ////        for (String id : testDeviceIds) {
    ////            builder.addTestDevice(id);
    ////        }
    ////        builder.tagForChildDirectedTreatment(getResources()
    ////                .getBoolean(R.bool.child_directed_treatment));
    //
    //        mAd.loadAd(builder.build());
    //    }
    override fun setTitleNew(title: Int) {
        val aaa = findViewById<TextView>(R.id.text_title)
        aaa.setText(title)
    }

    override fun snackbar(message: Int) {
        val snackbar = Snackbar.make(findViewById(R.id.scrollcontainer), message, Snackbar.LENGTH_SHORT)
        snackbar.setActionTextColor(resources.getColor(android.R.color.holo_red_dark))
        snackbar.setAction("[x]", null)
        snackbar.show()
    }

    override fun replaceFragment(fragment: Fragment) {
        try {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.scrollcontainer, fragment)
            transaction.addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        } catch (e: Exception) {
            handleException(e)
        }
    }

    protected fun setupAdAtBottom() {
        //FrameLayout content = findViewById(android.R.id.content);

        val content: FrameLayout = findViewById(R.id.bottom_button)

//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.BOTTOM;

//        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater()
//                .inflate(R.layout.ad_layout, null);
//        linearLayout.setLayoutParams(params);
//
//        // adding viewtreeobserver to get height of linearLayout layout , so that
//        // android.R.id.content will set margin of that height
//        ViewTreeObserver vto = linearLayout.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @SuppressLint("ObsoleteSdkInt")
//            @Override
//            public void onGlobalLayout() {
//                if (Build.VERSION.SDK_INT < 16) {
//                    linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                } else {
//                    linearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                }
//                int width = linearLayout.getMeasuredWidth();
//                int height = linearLayout.getMeasuredHeight();
//                //DLog.i("@@@@" + height + "x" + width);
//                setSpaceForAd(height);
//            }
//        });
//        addLayoutToContent(linearLayout);
        val interactor = AdvertInteractorImpl(
            ThreadExecutor.getInstance(),
            MainThreadImpl.getInstance(), loadRepository()
        )
        //aa.attach(this);
        //DLog.d("---->" + aa.hashCode());
        interactor.selectView(content, callback)
    }

    protected abstract fun loadRepository(): AdvertRepository

    companion object {
        const val TAG: String = "@@@"
        private const val ADS_ENABLED = true
    }
}
