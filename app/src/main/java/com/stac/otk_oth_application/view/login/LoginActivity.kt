package com.stac.otk_oth_application.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stac.otk_oth_application.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonListener()
    }

    private fun buttonListener() {
        register_text.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }
}
