package com.walhalla.pillfinder.activity

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.Spanned
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.walhalla.lib.datamodel.rxnorm.response.RxNormSpellingSuggestionsResponse
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.fragment.api2.RxNorm.Companion.newIngredientInstance
import com.walhalla.pillfinder.fragment.api2.RxNorm.Companion.newInstance
import com.walhalla.pillfinder.fragment.api2.test0
import com.walhalla.pillfinder.fragment.main.FragmentMain.FragmentCallback
import com.walhalla.pillfinder.fragment.main.IMainView
import com.walhalla.ui.DLog.d
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.sql.Time
import javax.net.ssl.SSLHandshakeException

class MainA2 : BaseActivity(), IMainView, FragmentCallback {
    private var rxcui: String? = null
    private var ingredient: String? = null

    private lateinit var autoTextView: AutoCompleteTextView
    private var topMenu: ViewGroup? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        if (rxcui != null) {
            menu.findItem(R.id.action_clear).isVisible = false
        }
        return true
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getResources().getString(R.string.drugsearch)


//        val actv: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView1)
//        val countries = mutableListOf<String>("Xanax")
//        val adapter = ArrayAdapter<String?>(this, android.R.layout.simple_list_item_1, countries)
//        actv.setAdapter<ArrayAdapter<String?>?>(adapter)
//        actv.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//                countries.clear()
//                countries.add("@@@@+++++ " + System.currentTimeMillis())
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun beforeTextChanged(
//                p0: CharSequence?,
//                p1: Int,
//                p2: Int,
//                p3: Int
//            ) {
//                countries.clear()
//                countries.add("@@@@--------------" + System.currentTimeMillis())
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onTextChanged(
//                p0: CharSequence?,
//                p1: Int,
//                p2: Int,
//                p3: Int
//            ) {
//                countries.clear()
//                countries.add("@@@@--------------" + System.currentTimeMillis())
//                adapter.notifyDataSetChanged()
//            }
//        })


        autoTextView = findViewById(R.id.auto_text_view)
        topMenu = findViewById(R.id.top_menu)

        if (intent != null) {
            rxcui = intent.getStringExtra(KEY_RXNORMID)
            ingredient = intent.getStringExtra(KEY_INGREDIENT)
        }


        if (rxcui != null && savedInstanceState == null) {
            topMenu!!.visibility = View.GONE

            supportFragmentManager.beginTransaction()
                .add(R.id.container, newInstance(rxcui))
                .commit()
        } else if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, newIngredientInstance(ingredient))
                .commit()
        }


        // 198449 acetaminophen 500 MG Oral Tablet
        // 198449 acetaminophen
        if ( //BuildConfig.DEBUG &&
            ingredient == null) {
            //autoTextView.setText("acetaminophen");
            autoTextView.setText("rifampin 150 MG Oral Capsule") //rxcui='198201'
            //autoTextView.hint = "rifampin 150 MG Oral Capsule"
        }

        //View s = findViewById(R.id.status);
        //s.setVisibility(View.GONE);
    }

    override fun aLayout(): Int {
        return R.layout.activity_drug_search
    }

    override fun showProgressBar() {
        val bar = findViewById<ProgressBar?>(R.id.scan_progress)
        if (null != bar) {
            bar.isIndeterminate = true
        }
    }

    override fun hideProgressBar() {
        val bar = findViewById<ProgressBar?>(R.id.scan_progress)
        if (null != bar) {
            bar.isIndeterminate = false
        }
    }

    override fun replaceFragment(fragment: Fragment) {
    }

    override fun showMoreInfo(s: String) {
    }

    override fun replyStatus(s: Spanned) {
    }

    override fun handleThrowable(throwable: Throwable) {
        d("@" + throwable.message)
        //JsonSyntaxException
        //no_parameters_reply
        val err: String?
        if (throwable is ConnectException) {
            err = getString(R.string.err_connection)
        } else if (throwable is SSLHandshakeException) {
            err = throwable.getLocalizedMessage()
        } else if (throwable is UnknownHostException) {
            err = getString(R.string.err_connection)
        } else if (throwable is IOException) {
            err = getString(R.string.err_connection)
        } else {
            err = throwable.getLocalizedMessage() //getString(R.string.err_refine_query);
        }
        mSnackbar(err)
    }

    override fun mSnackbar(message: Int) {
    }

    override fun mSnackbar(message: String) {
        val snackbar =
            Snackbar.make(findViewById<View?>(R.id.container), message, Snackbar.LENGTH_SHORT)
        snackbar.setActionTextColor(getResources().getColor(android.R.color.holo_red_dark))
        snackbar.setAction("[x]", null)
        snackbar.show()
    }

    override fun setMainTitle(var1: String, var2: Spannable) {}


    public override fun onResume() {
        super.onResume()
//        if (rxcui != null) {
//            getSupportActionBar().setTitle("RxCUI: " + rxcui);
//        }
    }


    override fun onBackPressed() {
        //if (presenter.onBackPressedRequest(this)) {
        super.onBackPressed()
        //}
    }

    companion object {
        const val KEY_RXNORMID: String = "key_rxnorm_Id"
        const val KEY_INGREDIENT: String = "key_Ingredien"
    }
}