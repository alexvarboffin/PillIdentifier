package com.walhalla.health.bloodPressure

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.walhalla.health.Constant
import com.walhalla.health.IdealWeight.InnerAbstractFragment
import com.walhalla.health.R

class BloodPressureFragment : InnerAbstractFragment() {
    /*
        * lower bp=1.5 or lower
        *
        *
        * */
    var primaryColor: Int = 0
    var edSystolicBp: EditText? = null
    var edDiastolicBp: EditText? = null
    lateinit var calc: Button
    lateinit var reset: Button
    var txtInputStstabolic: TextInputLayout? = null

    override fun aLayout(): Int {
        return R.layout.activity_bp
    }

    //    @Override
    //    protected int aTheme() {
    //        return R.style.OrangeTheme;
    //    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        reset.setOnClickListener { v: View? ->
            edDiastolicBp!!.setText("")
            edSystolicBp!!.setText("")
        }

        calc.setOnClickListener { v: View? ->
            if (TextUtils.isEmpty(
                    edSystolicBp!!.text.toString()
                )
            ) {
//                    txtInputStstabolic.setError(getResources().getString(R.string.valid));
                edSystolicBp!!.error = resources.getString(R.string.valid)

                return@setOnClickListener
            } else if (TextUtils.isEmpty(edDiastolicBp!!.text.toString())) {
                edDiastolicBp!!.error = resources.getString(R.string.valid)
                return@setOnClickListener
            } else {
                val sBp = edSystolicBp!!.text.toString().toFloat()
                val dBp = edDiastolicBp!!.text.toString().toFloat()
                var result = ""
                result = if (sBp > 180 || dBp > 110) {
                    resources.getString(R.string.hypertensive_crisis)
                } else if (sBp >= 160 || dBp >= 100) {
                    resources.getString(R.string.high_bp_stage2)
                } else if (sBp > 140 || dBp > 90) {
                    resources.getString(R.string.high_bp_stage1)
                } else if (sBp > 120 || dBp > 80) {
                    resources.getString(R.string.prehypertension)
                } else if (sBp > 80 && dBp > 60) {
                    resources.getString(R.string.normal_bp)
                } else {
                    resources.getString(R.string.low_bp)
                }

                if (mainView != null) {
                    mainView.replaceFragment(BpResult.newInstance(result))
                }

                //                    if (sBp < 80 && dBp < 60) {
//                        result = getResources().getString(R.string.low_bp);
//                    }
//                    else if ()
            }
        }
    }

    private fun init(view: View) {
        primaryColor =
            ContextCompat.getColor(requireContext().applicationContext, R.color.orangecolorPrimary)
        edSystolicBp = view.findViewById(R.id.edSystolicBp)
        edDiastolicBp = view.findViewById(R.id.edDiastolicBp)

        reset = view.findViewById(R.id.reset)
        calc = view.findViewById(R.id.calc)
        txtInputStstabolic = view.findViewById(R.id.txtInputStstabolic)

        //white btn
        reset.setBackground(Constant.getShapeDrawable(true, primaryColor))
        calc.setBackground(Constant.getShapeDrawable(false, primaryColor))
    }
}
