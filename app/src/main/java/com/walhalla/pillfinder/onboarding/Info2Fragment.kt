package com.walhalla.pillfinder.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.walhalla.pillfinder.R

class Info2Fragment : Fragment() {
    private var small_to_big: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        small_to_big = AnimationUtils.loadAnimation(activity, R.anim.small_to_big)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.startAnimation(small_to_big)
    }

    companion object {
        fun newInstance(): Info2Fragment {
            val fragment = Info2Fragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
