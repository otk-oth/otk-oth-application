package com.stac.otk_oth_application

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 2000)
    }
}
