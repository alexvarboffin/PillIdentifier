package com.walhalla.bloodAlcohol

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.walhalla.health.IdealWeight.InnerAbstractFragment
import com.walhalla.health.R


class BloodAlcoholResult : InnerAbstractFragment() {
    private var rotate: Animation? = null
    private var tvBloodAlcohol: TextView? = null
    private var tvBloodAlcoholVal: TextView? = null
    private var tvPercent: TextView? = null
    private var value: String? = null

    override fun aLayout(): Int {
        return R.layout.bloodalcoholresult
    }

    //    @Override
    //    protected int aTheme() {
    //        return R.style.GrayTheme;
    //    }
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        if (arguments != null) {
            value = requireArguments().getString(ARG_VALUE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        val imageView = view.findViewById<ImageView>(R.id.ivRotate)
        rotate = AnimationUtils.loadAnimation(requireActivity().applicationContext, R.anim.rotate)
        imageView.startAnimation(this.rotate)
        tvBloodAlcoholVal!!.text = value
    }

    private fun init(view: View) {
        if (null != mainView) {
            mainView.setTitleNew(R.string.bloodalcohol)
        }
        tvBloodAlcohol = view.findViewById(R.id.tvBloodAlcohol)
        tvBloodAlcoholVal = view.findViewById(R.id.tvBloodAlcoholVal)
        tvPercent = view.findViewById(R.id.tvPercent)
    }

    companion object {
        private const val ARG_VALUE = "value"

        fun newInstance(value: String): Fragment {
            val fragment: Fragment = BloodAlcoholResult()
            val arg = Bundle()
            arg.putString(ARG_VALUE, value)
            fragment.arguments = arg
            return fragment
        }
    }
}
