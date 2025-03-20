package com.walhalla.pillfinder.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.activity.HealthMainActivity

class OnboardingActivity : AppCompatActivity() {
    var selectPage: Int = 0
    private var viewPager: ViewPager? = null
    var adapterViewPager: MyPagerAdapter? = null
    var btnNext: TextView? = null
    private var manager: OnboardingManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = OnboardingManager(this)
        if (manager!!.isOnbording) {
            startActivity(
                Intent(
                    this@OnboardingActivity,
                    HealthMainActivity::class.java
                )
            )
            finish()
        }
        setContentView(R.layout.activity_onboarding)
        btnNext = findViewById(R.id.btn_next)
        viewPager = findViewById(R.id.vpPager)

        adapterViewPager = MyPagerAdapter(supportFragmentManager)

        btnNext?.setOnClickListener({ v: View? ->
            if (selectPage == 0) {
                viewPager?.setCurrentItem(1)
            } else if (selectPage == 1) {
                viewPager?.setCurrentItem(2)
            } else if (selectPage == 2) {
                manager!!.isOnbording(true)
                startActivity(
                    Intent(
                        this@OnboardingActivity,
                        HealthMainActivity::class.java
                    )
                )
                finish()
            }
        })
        viewPager?.setAdapter(adapterViewPager)
        val indicator = findViewById<ExtensiblePageIndicator>(R.id.flexibleIndicator)
        indicator.initViewPager(viewPager)
        viewPager?.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                selectPage = position
                if (position == 0 || position == 1) {
                    btnNext?.setText(R.string.onboard_action_next)
                } else if (position == 2) {
                    btnNext?.setText(R.string.onboard_action_finish)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    class MyPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> Info1Fragment.newInstance()
                1 -> Info2Fragment.newInstance()
                2 -> Info3Fragment.newInstance()
                else -> Info1Fragment.newInstance()
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "Page $position"
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return super.instantiateItem(container, position)
        }
    }
}
