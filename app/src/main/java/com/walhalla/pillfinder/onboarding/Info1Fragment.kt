package com.walhalla.pillfinder.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.databinding.FragmentInfo1Binding

class Info1Fragment : Fragment() {
    private var binding: FragmentInfo1Binding? = null
    private var animation: Animation? = null
    private var nothisgtocome: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animation = AnimationUtils.loadAnimation(requireContext(), R.anim.small_to_big)
        nothisgtocome = AnimationUtils.loadAnimation(requireContext(), R.anim.nosingtocome)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentInfo1Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.imageView.startAnimation(animation)
        binding!!.textView.startAnimation(nothisgtocome)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Очистка binding
    }

    companion object {
        fun newInstance(): Info1Fragment {
            return Info1Fragment()
        }
    }
}

