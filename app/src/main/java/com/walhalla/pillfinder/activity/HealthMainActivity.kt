package com.walhalla.pillfinder.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import com.walhalla.library.activity.GDPR;
import com.walhalla.pillfinder.features.BodyFatContainer;
import com.walhalla.pillfinder.features.BloodPressure;
import com.walhalla.pillfinder.features.BloodVolumeCalc;
import com.walhalla.pillfinder.features.BodyMassIndex;
import com.walhalla.pillfinder.features.CalorieCalculator;
import com.walhalla.pillfinder.features.HeartRate;
import com.walhalla.pillfinder.features.IdealWeightCalc;
import com.walhalla.pillfinder.features.Ovulation;
import com.walhalla.pillfinder.features.Pregnancy;
import com.walhalla.pillfinder.features.Respiration;
import com.walhalla.pillfinder.features.WaterIntakeCalc;
import com.walhalla.health.adapters.LazyAdapter;
import com.walhalla.health.models.RowItem;

import com.walhalla.health.util.Utils;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.features.BloodAlcoholContent0;
import com.walhalla.pillfinder.features.BloodDonation;

import java.util.ArrayList;
import java.util.List;
public class HealthMainActivity extends BaseActivity
        implements LazyAdapter.clickInterface {


    //private static final String ratings_fileName = "ratingAgain";
    private static final int[] images =
            {
                    R.drawable.pillidentifier,
                    R.drawable.drugsearch,

                    R.drawable.idealweight, R.drawable.bmi, R.drawable.heartrate, R.drawable.bloodvol, R.drawable.blood_donate, R.drawable.calorie,
                    R.drawable.waterintake, R.drawable.body_fat, R.drawable.bloodalcohol,
                    R.drawable.pregnancy_new, R.drawable.ovulation, R.drawable.breath_count,
                    R.drawable.blood_pressure
            };
    private static final Integer[] themes = {
            R.style.OrangeTheme,
            R.style.OrangeTheme,

            R.style.OrangeTheme, R.style.BlueTheme, R.style.YellowTheme, R.style.CyanTheme, R.style.PinkTheme, R.style.GrayTheme, R.style.OrangeTheme,
            R.style.BlueTheme, R.style.GrayTheme, R.style.PinkTheme, R.style.YellowTheme,
            R.style.CyanTheme, R.style.OrangeTheme
    };

    private static final Integer[] arrows = {
            R.drawable.arrow_cyan,
            R.drawable.arrow_pink,

            R.drawable.arrow_orange, R.drawable.arrow_blue,
            R.drawable.arrow_yellow, R.drawable.arrow_cyan, R.drawable.arrow_pink,
            R.drawable.arrow_gray, R.drawable.arrow_orange
            , R.drawable.arrow_blue, R.drawable.arrow_gray, R.drawable.arrow_pink, R.drawable.arrow_yellow,
            R.drawable.arrow_cyan, R.drawable.arrow_orange
    };


    private static final Integer[] colors = {
            R.color.pillidentifier,
            R.color.drugsearch,

            R.color.orangecolorPrimary, R.color.bluecolorPrimary,
            R.color.yellowcolorPrimary, R.color.cyancolorPrimary,
            R.color.pinkcolorPrimary, R.color.graycolorPrimary,
            R.color.orangecolorPrimary, R.color.bluecolorPrimary,
            R.color.graycolorPrimary,
            R.color.pinkcolorPrimary, R.color.yellowcolorPrimary,
            R.color.cyancolorPrimary, R.color.orangecolorPrimary
    };

    private static final boolean ADS_VISIBILITY = false;

    SharedPreferences ratePrefs;
    List<RowItem> rowItems;
    LazyAdapter lazyAdapter;
    RecyclerView listView;

    boolean interstitialCanceled;
    InterstitialAd mInterstitialAd;


    @Override
    protected int aLayout() {
        return R.layout.activity_health_main;
    }

    @SuppressLint("MissingPermission")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GDPR gdpr = new GDPR();
        gdpr.init(this);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(
//        WindowManager.LayoutParams.FLAG_FULLSCREEN,
//        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        //ratePrefs = getSharedPreferences(ratings_fileName, 0);

        listView = findViewById(R.id.myList);
        rowItems = new ArrayList<>();
        int[] strArr = {
                R.string.app_name,
                R.string.drugsearch,

                R.string.idealweight, R.string.bmi_title, R.string.heartrate, R.string.bloodvol, R.string.blood_donate, R.string.calories, R.string.waterintake,
                R.string.bodyfat, R.string.bloodalcohol, R.string.pregnancy, R.string.ovulation, R.string.title_breath_count, R.string.blood_pressure
        };
        int[] descriptions = {
                R.string.pillidentifier,
                R.string.drugsearch_desc,

                R.string.idealweight_desc, R.string.bmi_desc, R.string.heart_desc, R.string.bloodvol_desc, R.string.blood_don_desc, R.string.calorie_desc, R.string.waterintake_desc,
                R.string.bodyfat_desc, R.string.bloodalcohol_desc,
                R.string.pregnancy_desc, R.string.ovulation_desc, R.string.breath_count_desc, R.string.calc_bp_val
        };


        for (int i = 0; i < strArr.length; i++) {
            this.rowItems.add(new RowItem(images[i], strArr[i], descriptions[i], themes[i], arrows[i], colors[i]));
        }
        lazyAdapter = new LazyAdapter(this.rowItems, this);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        listView.setAdapter(lazyAdapter);
        lazyAdapter.setListeners(this);
    }


    private void init() {
        setTitle(getResources().getString(R.string.app_name));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_health, menu);
        return true;
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
//            str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//            localIntent.putExtra(Intent.EXTRA_SUBJECT, getResources()
//                    .getString(R.string.feedback_msg));
//            localIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n" + getResources().getString(R.string.device_os) +
//                    Build.VERSION.RELEASE + "\n" + getResources().getString(R.string.app_version) + str + "\n Device Brand: " + Build.BRAND +
//                    "\n" + getResources().getString(R.string.device_model) + Build.MODEL + "\n" + getResources().getString(R.string.manufacturer) + Build.MANUFACTURER);
//            localIntent.setType("message/rfc822");
//            startActivity(Intent.createChooser(localIntent, getResources().getString(R.string.email_client)));
//        } catch (Exception ignored) {
//        }
//    }


    @Override
    public void onRecItemClick(View view, int pos) {
        switch (pos) {
            case 0:
                passIntent(MainActivity.class);
                return;
            case 1:
                passIntent(MainA2.class);
                return;
            case 2:
                passIntent(IdealWeightCalc.class);
                return;
            case 3:
                passIntent(BodyMassIndex.class);
                return;
            case 4:
                passIntent(HeartRate.class);
                return;
            case 5:
                passIntent(BloodVolumeCalc.class);
                return;
            case 6:
                passIntent(BloodDonation.class);
                return;
            case 7:
                passIntent(CalorieCalculator.class);
                return;
            case 8:
                passIntent(WaterIntakeCalc.class);
                return;
            case 9:
                passIntent(BodyFatContainer.class);
                return;
            case 10:
                passIntent(BloodAlcoholContent0.class);
                return;
            case 11:
                passIntent(Pregnancy.class);
                return;
            case 12:
                passIntent(Ovulation.class);
                return;
            case 13:
                passIntent(Respiration.class);
                return;
            case 14:
                passIntent(BloodPressure.class);
                return;
            default:
        }
    }

    private void passIntent0(Class<? extends Activity> aClass) {
        if (!interstitialCanceled) {
            if (Utils.isOk(mInterstitialAd)) {
                mInterstitialAd.show(this);

                Utils.aaa(mInterstitialAd, new AdListener() {
                    public void onAdClosed() {
//                    ContinueIntent();
                        passIntent(aClass);
                    }
                });
            } else {
                passIntent(aClass);
            }
        }
//                passIntent(IdealWeightCalc.class);
    }

    private void passIntent(Class<? extends Activity> aClass) {
//        if (!interstitialCanceled) {
//            if (Utils.isOk(mInterstitialAd)) {
//                mInterstitialAd.show(this);
//
//                Utils.aaa(mInterstitialAd, new AdListener() {
//                    public void onAdClosed() {
////                    ContinueIntent();
//                        startActivity(new Intent(this, aClass));
//                    }
//                });
//            } else {
//                ContinueIntent();
//            }
//        }

        startActivity(new Intent(this, aClass));
    }

    @Override
    protected void onPause() {
        mInterstitialAd = null;
        interstitialCanceled = true;
        super.onPause();
    }

    @Override
    protected void onResume() {
        interstitialCanceled = false;
        if (ADS_VISIBILITY) {
            //CallNewInsertial();
        }
        super.onResume();
    }


//    private void callNewInsertial() {
//        if (cd.isConnectingToInternet()) {
//            @@@
//
//        }
//    }

    private boolean doubleBackToExitPressedOnce = true;

    //finishAffinity();

    @Override
    public void onBackPressed() {

//        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
        //Pressed back => return to home screen
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        boolean result = count > 0;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(result);
        }
        if (result) {
            fm.popBackStack(fm.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {//count == 0
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
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 500);
        }
            /*
            //Next/Prev Navigation
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
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
}
