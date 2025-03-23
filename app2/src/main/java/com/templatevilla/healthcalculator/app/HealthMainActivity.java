package com.templatevilla.healthcalculator.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.walhalla.health.util.ConnectionDetector;
import com.templatevilla.healthcalculator.R;
import com.walhalla.health.BaseActivity;

import com.walhalla.health.activity.BloodAlcoholContent0;
import com.walhalla.health.activity.BloodDonation;
import com.walhalla.health.activity.BloodPressure;
import com.walhalla.health.activity.BloodVolumeCalc;
import com.walhalla.health.activity.BodyFatHome;
import com.walhalla.health.activity.BodyMassIndex;
import com.walhalla.health.activity.CalorieCalculator;
import com.walhalla.health.activity.HeartRateCalculator;
import com.walhalla.health.activity.IdealWeightCalc;
import com.walhalla.health.activity.OvulationCalc;
import com.walhalla.health.activity.PregnancyCalc;
import com.walhalla.health.adapters.LazyAdapter;
import com.walhalla.health.models.RowItem;
import com.walhalla.health.util.Utils;
import com.walhalla.health.activity.RespirationRate;
import com.walhalla.health.activity.WaterIntakeCalc;

import java.util.ArrayList;
import java.util.List;


public class HealthMainActivity extends BaseActivity implements LazyAdapter.clickInterface {

    private static final String ratings_fileName = "ratingAgain";
    private static final Integer[] images = {R.drawable.idealweight, R.drawable.bmi, R.drawable.heartrate, R.drawable.bloodvol, R.drawable.blood_donate, R.drawable.calorie,
            R.drawable.waterintake, R.drawable.body_fat, R.drawable.bloodalcohol,
            R.drawable.pregnancy_new, R.drawable.ovulation, R.drawable.breath_count, R.drawable.blood_pressure};
    private static final Integer[] themes = {R.style.OrangeTheme, R.style.BlueTheme, R.style.YellowTheme, R.style.CyanTheme, R.style.PinkTheme, R.style.GrayTheme, R.style.OrangeTheme,
            R.style.BlueTheme, R.style.GrayTheme, R.style.PinkTheme, R.style.YellowTheme,
            R.style.CyanTheme, R.style.OrangeTheme};
    private static final Integer[] arrows = {R.drawable.arrow_orange, R.drawable.arrow_blue, R.drawable.arrow_yellow, R.drawable.arrow_cyan, R.drawable.arrow_pink,
            R.drawable.arrow_gray, R.drawable.arrow_orange
            , R.drawable.arrow_blue, R.drawable.arrow_gray, R.drawable.arrow_pink, R.drawable.arrow_yellow,
            R.drawable.arrow_cyan, R.drawable.arrow_orange};
    private static final Integer[] colors = {
            R.color.orangecolorPrimary, R.color.bluecolorPrimary,
            R.color.yellowcolorPrimary, R.color.cyancolorPrimary, R.color.pinkcolorPrimary, R.color.graycolorPrimary, R.color.orangecolorPrimary
            , R.color.bluecolorPrimary, R.color.graycolorPrimary, R.color.pinkcolorPrimary, R.color.yellowcolorPrimary,
            R.color.cyancolorPrimary, R.color.orangecolorPrimary
    };
    SharedPreferences ratePrefs;
    List<RowItem> rowItems;
    LazyAdapter lazyAdapter;
    RecyclerView listView;
    Toolbar toolbar;

    ConnectionDetector cd;
    boolean interstitialCanceled;
    InterstitialAd mInterstitialAd;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_health_main);
        MobileAds.initialize(this);
        init();
        ratePrefs = getSharedPreferences(ratings_fileName, 0);
        ((AdView) findViewById(R.id.adView)).loadAd(new Builder().build());
        listView = findViewById(R.id.myList);
        rowItems = new ArrayList<>();

        int[] strArr = {
                R.string.idealweight, R.string.bmi_title, R.string.heartrate, R.string.bloodvol, R.string.blood_donate, R.string.calories, R.string.waterintake,
                R.string.bodyfat, R.string.bloodalcohol, R.string.pregnancy, R.string.ovulation, R.string.breath_count, R.string.blood_pressure};

        int[] strArr2 = {
                R.string.idealweight_desc, R.string.bmi_desc, R.string.heart_desc, R.string.bloodvol_desc, R.string.blood_don_desc, R.string.calorie_desc, R.string.waterintake_desc,
                R.string.bodyfat_desc, R.string.bloodalcohol_desc,
                R.string.pregnancy_desc, R.string.ovulation_desc, R.string.breath_count_desc, R.string.calc_bp_val};

        for (int i = 0; i < strArr.length; i++) {
            this.rowItems.add(new RowItem(images[i], strArr[i], strArr2[i], themes[i], arrows[i], colors[i]));
        }
        lazyAdapter = new LazyAdapter(this.rowItems, this);
        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
        listView.setAdapter(lazyAdapter);
        lazyAdapter.setListeners(this);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.app_name));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRate:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="));
                startActivity(intent);
                break;
            case R.id.actionFeedback:
                sendFeedBack();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void sendFeedBack() {
        Intent localIntent = new Intent(Intent.ACTION_SEND);
        localIntent.putExtra(Intent.EXTRA_EMAIL, R.string.mail_feedback_email);
        localIntent.putExtra(Intent.EXTRA_CC, "");
        String str;
        try {
            str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            localIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_msg));
            localIntent.putExtra(Intent.EXTRA_TEXT, "\n\n----------------------------------\n" + getResources().getString(R.string.device_os) +
                    Build.VERSION.RELEASE + "\n" + getResources().getString(R.string.app_version) + str + "\n Device Brand: " + Build.BRAND +
                    "\n" + getResources().getString(R.string.device_model) + Build.MODEL + "\n" + getResources().getString(R.string.manufacturer) + Build.MANUFACTURER);
            localIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(localIntent, getResources().getString(R.string.email_client)));
        } catch (Exception ignored) {
        }
    }

    public void onBackPressed() {
        finishAffinity();
    }


    @Override
    public void onRecItemClick(View view, int i) {
        switch (i) {
            case 0:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(IdealWeightCalc.class);
                            }
                        });
                    } else {
                        passIntent(IdealWeightCalc.class);
                    }
                }
//                passIntent(IdealWeightCalc.class);
                return;
            case 1:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);
                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(BodyMassIndex.class);
                            }
                        });
                    } else {
                        passIntent(BodyMassIndex.class);
                    }
                }

//                passIntent(BodyMassIndex.class);
                return;
            case 2:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(HeartRateCalculator.class);
                            }
                        });
                    } else {
                        passIntent(HeartRateCalculator.class);
                    }
                }

//                passIntent(HeartRateCalculator.class);
                return;
            case 3:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(BloodVolumeCalc.class);
                            }
                        });
                    } else {
                        passIntent(BloodVolumeCalc.class);
                    }
                }

//                passIntent(BloodVolumeCalc.class);
                return;
            case 4:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(BloodDonation.class);
                            }
                        });
                    } else {
                        passIntent(BloodDonation.class);
                    }
                }

//                passIntent(BloodDonation.class);
                return;
            case 5:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(CalorieCalculator.class);
                            }
                        });
                    } else {
                        passIntent(CalorieCalculator.class);
                    }
                }

//                passIntent(CalorieCalculator.class);
                return;
            case 6:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(WaterIntakeCalc.class);
                            }
                        });
                    } else {
                        passIntent(WaterIntakeCalc.class);
                    }
                }

//                passIntent(WaterIntakeCalc.class);
                return;
            case 7:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(BodyFatHome.class);
                            }
                        });
                    } else {
                        passIntent(BodyFatHome.class);
                    }
                }

//                passIntent(BodyFatHome.class);
                return;
            case 8:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(BloodAlcoholContent0.class);
                            }
                        });
                    } else {
                        passIntent(BloodAlcoholContent0.class);
                    }
                }

//                passIntent(BloodAlcoholContent.class);
                return;
            case 9:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(PregnancyCalc.class);
                            }
                        });
                    } else {
                        passIntent(PregnancyCalc.class);
                    }
                }

//                passIntent(PregnancyCalc.class);
                return;
            case 10:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(OvulationCalc.class);
                            }
                        });
                    } else {
                        passIntent(OvulationCalc.class);
                    }
                }

//                passIntent(OvulationCalc.class);
                return;
            case 11:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(RespirationRate.class);
                            }
                        });
                    } else {
                        passIntent(RespirationRate.class);
                    }
                }

//                passIntent(RespirationRate.class);
                return;
            case 12:
                if (!interstitialCanceled) {
                    if (Utils.isOk(mInterstitialAd)) {
                        mInterstitialAd.show(this);

                        Utils.aaa(mInterstitialAd, new AdListener() {
                            public void onAdClosed() {
//                    ContinueIntent();
                                passIntent(BloodPressure.class);
                            }
                        });
                    } else {
                        passIntent(BloodPressure.class);
                    }
                }
//                passIntent(BloodPressure.class);
                return;
            default:
        }
    }


    public void passIntent(Class<? extends BaseActivity> aClass) {
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
//        if (getResources().getString(R.string.ADS_VISIBILITY).equals("YES")) {
//            CallNewInsertial();
//        }
        super.onResume();
    }


    private void CallNewInsertial() {
        cd = new ConnectionDetector(HealthMainActivity.this);
        if (cd.isConnectingToInternet()) {
            //@@ mInterstitialAd = new InterstitialAd(MainActivity.this);
            //@@ mInterstitialAd.setAdUnitId(getString(R.string.interestial_ad_id));
            //@@ AdRequest adRequest = new AdRequest.Builder().build();
            //@@ mInterstitialAd.loadAd(adRequest);

//            Utils.aaa(mInterstitialAd, new AdListener() {
//                public void onAdClosed() {
////                    ContinueIntent();
//                    startActivity(new Intent(this, aClass));
//                }
//            });

        }
    }
}
