package com.stac.otk_oth_application.view.lost

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.stac.otk_oth_application.R
import kotlinx.android.synthetic.main.activity_lost.*

class LostActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost)

        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
        }


        webView.loadUrl("https://www.lost112.go.kr/")
    }
}
