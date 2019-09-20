package com.stac.otk_oth_application.view.splash

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.view.main.MainActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val pref = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)

        val first = pref.getBoolean("isFirst", false)

        val handler = Handler()
        handler.postDelayed({
            if (!first) {
                val editor = pref.edit()
                editor.putBoolean("isFirst", true)
                editor.apply()
                startActivity<OnboardingActivity>()
                finish()
            } else {
                startActivity<MainActivity>()
                finish()
            }

        }, 2000)
    }
}
