package com.walhalla.bloodAlcohol

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.walhalla.health.BodyMassIndex.WeightAdapter
import com.walhalla.health.Constant
import com.walhalla.health.IdealWeight.GenderAdapter
import com.walhalla.health.IdealWeight.InnerAbstractFragment
import com.walhalla.health.M
import com.walhalla.health.R
import com.walhalla.ui.DLog.d
import java.text.NumberFormat

class BloodAlcoholFragment : InnerAbstractFragment(), AdapterView.OnItemSelectedListener {
    private var alcohollevel = 0.0
    private var bac = 0.0
    private var check = false
    private var edAlcoholLevel: EditText? = null
    private var edTime: EditText? = null
    private var edVolDrinked: EditText? = null

    private var factor = 0
    private var factor2 = 0

    /* renamed from: kg */
    var isKG: Boolean = true
    var male: Boolean = true

    /* renamed from: nf */

    var str_bac: String = ""
    var time: Double = 0.0

    var genderSp: Spinner? = null
    var timeSp: Spinner? = null
    var volumeSp: Spinner? = null
    var weightSp: Spinner? = null


    private var volDrinked = 0.0


    var weight: Double = 0.0
    var primaryColor: Int = 0

    override fun aLayout(): Int {
        return R.layout.bloodalcoholcalc
    }

    //    @Override
    //    protected int aTheme() {
    //        return R.style.GrayTheme;
    //    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val strArr =
            arrayOf(resources.getString(R.string.male), resources.getString(R.string.female))
        val strArr2 =
            arrayOf(resources.getString(R.string.kilograms), resources.getString(R.string.pounds))
        val strArr3 = arrayOf(
            resources.getString(R.string.hour),
            resources.getString(R.string.minute),
            resources.getString(R.string.day)
        )
        val strArr4 = arrayOf(
            resources.getString(R.string.ounces),
            resources.getString(R.string.ml),
            resources.getString(R.string.cup)
        )

        val button = view.findViewById<Button>(R.id.calc)
        val reset = view.findViewById<Button>(R.id.reset)
        val chart = view.findViewById<Button>(R.id.chart)
        init(view)

        chart.background = Constant.getShapeDrawable(false, primaryColor)
        button.background = Constant.getShapeDrawable(false, primaryColor)
        reset.background = Constant.getShapeDrawable(true, primaryColor)

        val numberFormat = NumberFormat.getInstance()
        numberFormat.setMaximumFractionDigits(3)
        genderSp!!.adapter = GenderAdapter(activity, R.layout.spinner_down_blue, strArr)
        volumeSp!!.adapter = VolumeAdapter(activity, R.layout.spinner_down_blue, strArr4)
        weightSp!!.adapter = WeightAdapter(activity, R.layout.spinner_down_blue, strArr2)
        timeSp!!.adapter = TimeAdapter(activity, R.layout.spinner_down_blue, strArr3)

        view.findViewById<View>(R.id.chart).setOnClickListener {
            if (mainView != null) {
                mainView.replaceFragment(
                    Chart_Alcohol()
                )
            }
        }

        weightSp!!.onItemSelectedListener = this


        genderSp!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                male = genderSp!!.selectedItem.toString() == activity!!.resources.getString(R.string.male)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }


        volumeSp!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                val obj = volumeSp!!.selectedItem.toString()
                factor = if (obj == activity!!.resources.getString(R.string.ounces)) {
                    1
                } else if (obj == activity!!.resources.getString(R.string.ml)) {
                    2
                } else {
                    3
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
        timeSp!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View,
                i: Int,
                l: Long
            ) {
                val obj = timeSp!!.selectedItem.toString()
                factor2 = if (obj == activity!!.resources.getString(R.string.hour)) {
                    1
                } else if (obj == activity!!.resources.getString(R.string.minute)) {
                    2
                } else {
                    3
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

        reset.setOnClickListener { view0: View? ->
            val str = ""
            edAlcoholLevel!!.setText(str)
            edWeight.setText(str)
            edTime!!.setText(str)
            edAlcoholLevel!!.requestFocus()
        }
        button.setOnClickListener { view0: View? ->
            try {
                this.alcohollevel =
                    edAlcoholLevel!!.text.toString().toDouble()
            } catch (unused: NumberFormatException) {
                this.check = true
            }
            try {
                this.volDrinked = edVolDrinked!!.text.toString()
                    .toDouble()
            } catch (unused2: NumberFormatException) {
                this.check = true
            }
            try {
                this.weight = edWeight.text.toString().toDouble()
            } catch (unused3: NumberFormatException) {
                this.check = true
            }
            try {
                this.time = edTime!!.text.toString().toDouble()
            } catch (unused4: NumberFormatException) {
                this.check = true
            }
            if (this.check) {
                toast(R.string.valid)
                this.check = false
                return@setOnClickListener
            }
            if (this.isKG) {
                this.weight *= 2.20462
            } else {
            }
            if (this.factor == 1) {
            } else if (this.factor == 2) {
                this.volDrinked *= 0.033814
            } else {
                this.volDrinked *= 8.0
            }
            this.alcohollevel /= 100.0
            this.volDrinked *= this.alcohollevel
            if (this.factor2 == 1) {
            } else if (this.factor2 == 2) {
                this.time *= 0.0166
            } else {
                this.time *= 24.0
            }
            this.weight = 5.14 / this.weight
            this.time *= 0.015
            if (this.male) {
                this.bac =
                    ((this.volDrinked * this.weight) * 0.73) - this.time
            } else {
                this.bac =
                    ((this.volDrinked * this.weight) * 0.66) - this.time
            }

            if (bac < 0) {
                bac = 0.0
            }
            this.str_bac = numberFormat.format(this.bac)
            if (mainView != null) {
                mainView.replaceFragment(BloodAlcoholResult.newInstance(this.str_bac))
            }
        }
    }

    private fun init(view: View) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bloodalcohol)
        }

        edTime = view.findViewById(R.id.edTime)
        bindWeight(view)
        edAlcoholLevel = view.findViewById(R.id.edAlcoholLevel)
        edVolDrinked = view.findViewById(R.id.edVolDrinked)


        //@@@TextView tvVolDrinked = view.findViewById(R.id.tvVolDrinked);
        //@@@ TextView tvAlcoholLevel = view.findViewById(R.id.tvAlcoholLevel);
        //@@@ TextView tvTime = view.findViewById(R.id.tvTime);
        //@@@ TextViewtvGender = view.findViewById(R.id.tvGender);
        //@@@ TextViewtvPercent = view.findViewById(R.id.tvPercent);
        //@@@ TextView//tvWeight = view.findViewById(R.id.tvWeight);
        weightSp = view.findViewById(R.id.weightSp)
        volumeSp = view.findViewById(R.id.volumeSp)
        genderSp = view.findViewById(R.id.genderSp)
        timeSp = view.findViewById(R.id.timeSp)
        primaryColor = ContextCompat.getColor(requireActivity().applicationContext, R.color.graycolorPrimary)

        val img_gender = view.findViewById<ImageView>(R.id.img_gender)
        val img_cocktail = view.findViewById<ImageView>(R.id.img_cocktail)
        val img_drink = view.findViewById<ImageView>(R.id.img_drink)
        val img_time = view.findViewById<ImageView>(R.id.img_time)
        val img_weight = view.findViewById<ImageView>(R.id.img_weight)
        M.setThemeColor(primaryColor, img_cocktail)
        M.setThemeColor(primaryColor, img_gender)
        M.setThemeColor(primaryColor, img_drink)
        M.setThemeColor(primaryColor, img_time)
        M.setThemeColor(primaryColor, img_weight)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        isKG = (parent.selectedItem.toString() == requireActivity().resources.getString(R.string.kilograms))
        d("@@@@" + parent.selectedItem.toString() + " " + position)
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
    }
}
