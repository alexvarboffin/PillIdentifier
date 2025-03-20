package com.walhalla.pillfinder.activity

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import com.walhalla.health.activity.base.AdActivity
import com.walhalla.pillfinder.BuildConfig
import com.walhalla.pillfinder.Constants
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.dialog.AboutBox
import com.walhalla.ui.plugins.Launcher.openBrowser
import com.walhalla.ui.plugins.Launcher.rateUs
import com.walhalla.ui.plugins.Module_U.feedback
import com.walhalla.ui.plugins.Module_U.moreApp
import com.walhalla.ui.plugins.Module_U.shareThisApp

abstract class BaseActivity : AdActivity() {
    var flavor_google: String = "low21" //Google Play [google]
    var flavor_amazon: String = "amazon" //Amazon Appstore
    var flavor_samsung: String = "samsung" //Samsung Galaxy Store

    var flavor_current: String = BuildConfig.FLAVOR


    //protected final boolean google_market_build = !(flavor_amazon.contains(flavor_current));
    protected fun google_market_build(): Boolean {
        return flavor_current.contains(flavor_google)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.action_about) {
            AboutBox.show(this)
            //Module_U.aboutDialog(this);
            return true
        } else if (itemId == R.id.action_rate_app) {
            rateUs(this)
            return true
        } else if (itemId == R.id.action_share_app) {
            shareThisApp(this)
            return true
        } else if (itemId == R.id.action_discover_more_app) {
            if (flavor_current.contains(flavor_google)) {
                moreApp(this)
            } else if (flavor_current.contains(flavor_amazon)) {
                //final String pub = this.getString(com.walhalla.ui.R.string.play_google_pub);
                try { //
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW, Uri.parse(
                                "amzn://apps/android?p=com.psyberia.pillidentifier" //"amzn://apps/android?p=com.walhalla.pillfindel"
                            )
                        )
                    )
                } catch (anfe: ActivityNotFoundException) {
                    openBrowser(
                        this,
                        "https://www.amazon.com/s?i=mobile-apps&rh=p_4%3APsyberia+Egregor&search-type=ss"
                    )
                }
            }

            //            else if (flavor_current.contains(aaa)) {}
            return true
        } else if (itemId == R.id.action_privacy_policy) {
            openBrowser(this, Constants.URL_PRIVACY_POLICY)
            return true
        } else if (itemId == R.id.action_feedback) {
            feedback(this)
            return true
        } else if (itemId == R.id.action_img) {
            return false
        } else if (itemId == R.id.action_info) {
            return false
        }
        //        else if (itemId == R.id.action_back) {
//            return false;
//        }
//        else if (itemId == R.id.action_bookmark) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, new BookmarkFragment())
//                    .addToBackStack(null)
//                    .commit();
//            return true;
//        }
        return super.onOptionsItemSelected(item)

        //action_how_to_use_app
        //action_support_developer
    }
}
