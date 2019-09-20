package com.stac.otk_oth_application.view.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.adapter.SliderAdapter
import com.stac.otk_oth_application.config.AppPrefs
import com.stac.otk_oth_application.hide
import com.stac.otk_oth_application.show
import com.stac.otk_oth_application.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_onboarding.*
import org.jetbrains.anko.startActivity

class OnboardingActivity : AppCompatActivity() {


    private lateinit var sliderAdapter: SliderAdapter
    private var dots: Array<TextView?>? = null
    private lateinit var layouts: Array<Int>
    private val sliderChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)

            if (position == layouts.size.minus(1)) {
                nextBtn.hide()
                skipBtn.hide()
                startBtn.show()
            } else {
                nextBtn.show()
                skipBtn.show()
                startBtn.hide()
            }
        }

        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)


        init()
        dataSet()
        interactions()

    }

    private fun init() {
        /** Layouts of the three onBoarding Screens.
         *  Add more layouts if you wish.
         **/
        layouts = arrayOf(
            R.layout.onboarding_one,
            R.layout.onboarding_two,
            R.layout.onboarding_three
        )

        sliderAdapter = SliderAdapter(this, layouts)
    }

    private fun dataSet() {
        /**
         * Adding bottom dots
         * */
        addBottomDots(0)

        slider.apply {
            adapter = sliderAdapter
            addOnPageChangeListener(sliderChangeListener)
        }
    }

    private fun getCurrentScreen(i: Int): Int = slider.currentItem.plus(i)

    private fun interactions() {
        skipBtn.setOnClickListener {
            // Launch login screen
            navigateToLogin()
        }
        startBtn.setOnClickListener {
            // Launch login screen
            navigateToLogin()
        }
        nextBtn.setOnClickListener {
            /**
             *  Checking for last page, if last page
             *  login screen will be launched
             * */
            val current = getCurrentScreen(+1)
            if (current < layouts.size) {
                /**
                 * Move to next screen
                 * */
                slider.currentItem = current
            } else {
                // Launch login screen
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        AppPrefs(this).setFirstTimeLaunch(false)
        startActivity<LoginActivity>()
        finish()
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts.size)

        dotsLayout.removeAllViews()
        for (i in 0 until dots!!.size) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.textSize = 35f
            dots!![i]?.setTextColor(resources.getColor(R.color.colorGrey))
            dotsLayout.addView(dots!![i])
        }

        if (dots!!.isNotEmpty()) {
            dots!![currentPage]?.setTextColor(resources.getColor(R.color.colorIndigo))
        }
    }


}