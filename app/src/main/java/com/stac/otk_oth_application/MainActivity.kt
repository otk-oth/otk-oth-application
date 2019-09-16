package com.stac.otk_oth_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toast("메인화면 입니다~")
    }
}
