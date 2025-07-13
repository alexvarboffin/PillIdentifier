package com.walhalla.pillfinder.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.walhalla.health.adapters.LazyAdapter
import com.walhalla.health.adapters.LazyAdapter.clickInterface
import com.walhalla.health.models.RowItem
import com.walhalla.health.util.Utils
import com.walhalla.library.activity.GDPR
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.features.BloodAlcoholContent0
import com.walhalla.pillfinder.features.BloodDonation
import com.walhalla.pillfinder.features.BloodPressure
import com.walhalla.pillfinder.features.BloodVolumeCalc
import com.walhalla.pillfinder.features.BodyFatContainer
import com.walhalla.pillfinder.features.BodyMassIndex
import com.walhalla.pillfinder.features.CalorieCalculator
import com.walhalla.pillfinder.features.HeartRate
import com.walhalla.pillfinder.features.IdealWeightCalc
import com.walhalla.pillfinder.features.Ovulation
import com.walhalla.pillfinder.features.Pregnancy
import com.walhalla.pillfinder.features.Respiration
import com.walhalla.pillfinder.features.WaterIntakeCalc


class HealthMainActivity : BaseActivity(), clickInterface {
    var ratePrefs: SharedPreferences? = null
    var rowItems: MutableList<RowItem> = mutableListOf()
    var lazyAdapter: LazyAdapter? = null
    var listView: RecyclerView? = null

    var interstitialCanceled: Boolean = false
    var mInterstitialAd: InterstitialAd? = null


    override fun aLayout(): Int {
        return R.layout.activity_health_main
    }

    @SuppressLint("MissingPermission")
    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val gdpr = GDPR()
        gdpr.init(this)

        //        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        window.setFlags(
//        WindowManager.LayoutParams.FLAG_FULLSCREEN,
//        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init()

        //ratePrefs = getSharedPreferences(ratings_fileName, 0);
        listView = findViewById<RecyclerView>(R.id.myList)
        rowItems = ArrayList<RowItem>()
        val strArr = intArrayOf(
            R.string.app_name,
            R.string.drugsearch,

            R.string.idealweight,
            R.string.bmi_title,
            R.string.heartrate,
            R.string.bloodvol,
            R.string.blood_donate,
            R.string.calories,
            R.string.waterintake,
            R.string.bodyfat,
            R.string.bloodalcohol,
            R.string.pregnancy,
            R.string.ovulation,
            R.string.title_breath_count,
            R.string.blood_pressure
        )
        val descriptions = intArrayOf(
            R.string.pillidentifier,
            R.string.drugsearch_desc,

            R.string.idealweight_desc,
            R.string.bmi_desc,
            R.string.heart_desc,
            R.string.bloodvol_desc,
            R.string.blood_don_desc,
            R.string.calorie_desc,
            R.string.waterintake_desc,
            R.string.bodyfat_desc,
            R.string.bloodalcohol_desc,
            R.string.pregnancy_desc,
            R.string.ovulation_desc,
            R.string.breath_count_desc,
            R.string.calc_bp_val
        )


        for (i in strArr.indices) {
            this.rowItems!!.add(
                RowItem(
                    images[i],
                    strArr[i],
                    descriptions[i],
                    themes[i]!!,
                    arrows[i]!!,
                    colors[i]!!
                )
            )
        }
        lazyAdapter = LazyAdapter(this.rowItems, this)
        listView!!.setLayoutManager(
            LinearLayoutManager(
                applicationContext,
                RecyclerView.VERTICAL,
                false
            )
        )
        listView!!.setAdapter(lazyAdapter)
        lazyAdapter!!.setListeners(this)
    }


    private fun init() {
        setTitle(resources.getString(R.string.app_name))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_health, menu)
        return true
    }


    //    @SuppressLint("NonConstantResourceId")
    //    @Override
    //    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.actionRate:
    //                Intent intent = new Intent(Intent.ACTION_VIEW);
    //                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="));
    //                startActivity(intent);
    //                break;
    //            case R.id.actionFeedback:
    //                sendFeedBack();
    //                break;
    //
    //        }
    //        return super.onOptionsItemSelected(item);
    //
    //    }
    //    private void sendFeedBack() {
    //        Intent localIntent = new Intent(Intent.ACTION_SEND);
    //        localIntent.putExtra(Intent.EXTRA_EMAIL, R.string.publisher_feedback_emai);
    //        localIntent.putExtra(Intent.EXTRA_CC, "");
    //        String str;
    //        try {
    //            str = packageManager.getPackageInfo(getPackageName(), 0).versionName;
    //            localIntent.putExtra(Intent.EXTRA_SUBJECT, resources
    //                    .getString(R.string.feedback_msg));
    //            localIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n" + resources.getString(R.string.device_os) +
    //                    Build.VERSION.RELEASE + "\n" + resources.getString(R.string.app_version) + str + "\n Device Brand: " + Build.BRAND +
    //                    "\n" + resources.getString(R.string.device_model) + Build.MODEL + "\n" + resources.getString(R.string.manufacturer) + Build.MANUFACTURER);
    //            localIntent.setType("message/rfc822");
    //            startActivity(Intent.createChooser(localIntent, resources.getString(R.string.email_client)));
    //        } catch (Exception ignored) {
    //        }
    //    }
    override fun onRecItemClick(view: View?, pos: Int) {
        when (pos) {
            0 -> {
                passIntent(MainActivity::class.java)
                return
            }

            1 -> {
                passIntent(MainA2::class.java)
                return
            }

            2 -> {
                passIntent(IdealWeightCalc::class.java)
                return
            }

            3 -> {
                passIntent(BodyMassIndex::class.java)
                return
            }

            4 -> {
                passIntent(HeartRate::class.java)
                return
            }

            5 -> {
                passIntent(BloodVolumeCalc::class.java)
                return
            }

            6 -> {
                passIntent(BloodDonation::class.java)
                return
            }

            7 -> {
                passIntent(CalorieCalculator::class.java)
                return
            }

            8 -> {
                passIntent(WaterIntakeCalc::class.java)
                return
            }

            9 -> {
                passIntent(BodyFatContainer::class.java)
                return
            }

            10 -> {
                passIntent(BloodAlcoholContent0::class.java)
                return
            }

            11 -> {
                passIntent(Pregnancy::class.java)
                return
            }

            12 -> {
                passIntent(Ovulation::class.java)
                return
            }

            13 -> {
                passIntent(Respiration::class.java)
                return
            }

            14 -> {
                passIntent(BloodPressure::class.java)
                return
            }

            else -> {}
        }
    }

    private fun passIntent0(aClass: Class<out Activity>) {
        if (!interstitialCanceled) {
            if (Utils.isOk(mInterstitialAd)) {
                mInterstitialAd!!.show(this)

                Utils.aaa(mInterstitialAd, object : AdListener() {
                    override fun onAdClosed() {
//                    ContinueIntent();
                        passIntent(aClass)
                    }
                })
            } else {
                passIntent(aClass)
            }
        }
        //                passIntent(IdealWeightCalc.class);
    }

    private fun passIntent(aClass: Class<out Activity>) {
//        if (!interstitialCanceled) {
//            if (Utils.isOk(mInterstitialAd)) {
//                mInterstitialAd.show(this);
//
//                Utils.aaa(mInterstitialAd, new AdListener() {
//                    public void onAdClosed() {
        /*                    ContinueIntent(); */
//                        startActivity(new Intent(this, aClass));
//                    }
//                });
//            } else {
//                ContinueIntent();
//            }
//        }

        startActivity(Intent(this, aClass))
    }

    protected override fun onPause() {
        mInterstitialAd = null
        interstitialCanceled = true
        super.onPause()
    }

    protected override fun onResume() {
        interstitialCanceled = false
        if (ADS_VISIBILITY) {
            //CallNewInsertial();
        }
        super.onResume()
    }


    //    private void callNewInsertial() {
    //        if (cd.isConnectingToInternet()) {
    //            @@@
    //
    //        }
    //    }
    private var doubleBackToExitPressedOnce = true

    //finishAffinity();
    override fun onBackPressed() {
        //        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
        //Pressed back => return to home screen

        val fm = supportFragmentManager
        val count = fm.backStackEntryCount
        val result = count > 0

        if (supportActionBar != null) {
            supportActionBar!!.setHomeButtonEnabled(result)
        }
        if (result) {
            fm.popBackStack(
                fm.getBackStackEntryAt(0).id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } else { //count == 0
//                Dialog
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();

            if (doubleBackToExitPressedOnce) {
                //moveTaskToBack(true);
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show()
            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 500)
        }
        /*
            //Next/Prev Navigation
            if (supportFragmentManager.getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Leaving this App?")
                        .setMessage("Are you sure you want to close this application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else
            {
            super.onBackPressed();
            }
            */
//        }
    }

    companion object {
        //private static final String ratings_fileName = "ratingAgain";
        private val images = intArrayOf(
            R.drawable.pillidentifier,
            R.drawable.drugsearch,

            R.drawable.idealweight,
            R.drawable.bmi,
            R.drawable.heartrate,
            R.drawable.bloodvol,
            R.drawable.blood_donate,
            R.drawable.calorie,
            R.drawable.waterintake,
            R.drawable.body_fat,
            R.drawable.bloodalcohol,
            R.drawable.pregnancy_new,
            R.drawable.ovulation,
            R.drawable.breath_count,
            R.drawable.blood_pressure
        )
        private val themes = arrayOf<Int?>(
            R.style.OrangeTheme,
            R.style.OrangeTheme,

            R.style.OrangeTheme,
            R.style.BlueTheme,
            R.style.YellowTheme,
            R.style.CyanTheme,
            R.style.PinkTheme,
            R.style.GrayTheme,
            R.style.OrangeTheme,
            R.style.BlueTheme,
            R.style.GrayTheme,
            R.style.PinkTheme,
            R.style.YellowTheme,
            R.style.CyanTheme,
            R.style.OrangeTheme
        )

        private val arrows = arrayOf<Int?>(
            R.drawable.arrow_cyan,
            R.drawable.arrow_pink,

            R.drawable.arrow_orange,
            R.drawable.arrow_blue,
            R.drawable.arrow_yellow,
            R.drawable.arrow_cyan,
            R.drawable.arrow_pink,
            R.drawable.arrow_gray,
            R.drawable.arrow_orange,
            R.drawable.arrow_blue,
            R.drawable.arrow_gray,
            R.drawable.arrow_pink,
            R.drawable.arrow_yellow,
            R.drawable.arrow_cyan,
            R.drawable.arrow_orange
        )


        private val colors = arrayOf<Int?>(
            R.color.pillidentifier,
            R.color.drugsearch,

            R.color.orangecolorPrimary, R.color.bluecolorPrimary,
            R.color.yellowcolorPrimary, R.color.cyancolorPrimary,
            R.color.pinkcolorPrimary, R.color.graycolorPrimary,
            R.color.orangecolorPrimary, R.color.bluecolorPrimary,
            R.color.graycolorPrimary,
            R.color.pinkcolorPrimary, R.color.yellowcolorPrimary,
            R.color.cyancolorPrimary, R.color.orangecolorPrimary
        )

        private const val ADS_VISIBILITY = false
    }
}
