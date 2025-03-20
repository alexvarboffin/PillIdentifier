package com.walhalla.pillfinder.activity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.walhalla.health.activity.base.AdActivity;
import com.walhalla.pillfinder.BuildConfig;
import com.walhalla.pillfinder.R;

import com.walhalla.pillfinder.fragment.api2.RxNorm;
import com.walhalla.pillfinder.fragment.main.FragmentMain;
import com.walhalla.pillfinder.fragment.main.IMainView;
import com.walhalla.ui.DLog;

import java.net.ConnectException;
import java.net.UnknownHostException;

public class MainA2 extends BaseActivity implements IMainView, FragmentMain.FragmentCallback {

    public static final String KEY_RXNORMID = "key_rxnorm_Id";
    public static final String KEY_INGREDIENT = "key_Ingredien";

    private String rxcui;
    private String ingredient;

    private AutoCompleteTextView autoTextView;
    private ViewGroup topMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (rxcui != null) {
            menu.findItem(R.id.action_clear).setVisible(false);
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.drugsearch));

        autoTextView = findViewById(R.id.auto_text_view);
        topMenu = findViewById(R.id.top_menu);

        if (getIntent() != null) {
            rxcui = getIntent().getStringExtra(KEY_RXNORMID);
            ingredient = getIntent().getStringExtra(KEY_INGREDIENT);
        }


        if (rxcui != null && savedInstanceState == null) {
            topMenu.setVisibility(View.GONE);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, RxNorm.newInstance(rxcui))
                    .commit();

        } else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, RxNorm.newIngredientInstance(ingredient))
                    .commit();
        }


        // 198449 acetaminophen 500 MG Oral Tablet
        // 198449 acetaminophen
        if (//BuildConfig.DEBUG &&
                ingredient == null) {
            //autoTextView.setText("acetaminophen");
            autoTextView.setText("rifampin 150 MG Oral Capsule");//rxcui='198201'

        }

        //View s = findViewById(R.id.status);
        //s.setVisibility(View.GONE);
    }

    @Override
    protected int aLayout() {
        return R.layout.activity_drug_search;
    }

    @Override
    public void showProgressBar() {
        ProgressBar bar = findViewById(R.id.scan_progress);
        if (null != bar) {
            bar.setIndeterminate(true);
        }
    }

    @Override
    public void hideProgressBar() {
        ProgressBar bar = findViewById(R.id.scan_progress);
        if (null != bar) {
            bar.setIndeterminate(false);
        }
    }

    @Override
    public void replaceFragment(Fragment fragment) {

    }

    @Override
    public void showMoreInfo(String s) {

    }

    @Override
    public void replyStatus(Spanned s) {

    }

    @Override
    public void handleThrowable(Throwable throwable) {
        DLog.d("@" + throwable.getMessage());
        //JsonSyntaxException
        //no_parameters_reply
        String err;
        if (throwable instanceof ConnectException) {
            err = getString(R.string.err_connection);
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            err = throwable.getLocalizedMessage();
        } else if (throwable instanceof UnknownHostException) {
            err = getString(R.string.err_connection);
        } else if (throwable instanceof java.io.IOException) {
            err = getString(R.string.err_connection);
        } else {
            err = throwable.getLocalizedMessage();//getString(R.string.err_refine_query);
        }
        mSnackbar(err);
    }

    @Override
    public void mSnackbar(int message) {

    }

    @Override
    public void mSnackbar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark));
        snackbar.setAction("[x]", null);
        snackbar.show();
    }

    @Override
    public void setMainTitle(String var1, Spannable var2) {

    }


    @Override
    public void onResume() {
        super.onResume();

//        if (rxcui != null) {
//            getSupportActionBar().setTitle("RxCUI: " + rxcui);
//        }
    }


    @Override
    public void onBackPressed() {
        //if (presenter.onBackPressedRequest(this)) {
        super.onBackPressed();
        //}
    }
}