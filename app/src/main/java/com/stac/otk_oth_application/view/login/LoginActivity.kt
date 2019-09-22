package com.stac.otk_oth_application.view.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuth
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.toast
import com.stac.otk_oth_application.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var userEmail: String? = null
    var userPw: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonListener()

        login.setOnClickListener {
            email()
        }
    }

    private fun email() {
        userEmail = login_email.text.toString()
        userPw = login_pw.text.toString()

        firebaseAuth.signInWithEmailAndPassword(userEmail!!, userPw!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toast("로그인 성공")
                    startActivity<InfoActivity>()
                    finish()
                } else {
                    toast("로그인 실패")
                }
            }
            .addOnFailureListener {
                toast("로그인 실패")
            }
    }


    private fun buttonListener() {
        register_text.setOnClickListener {
            startActivity<RegisterActivity>()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(login_email.windowToken, 0)
        return true
    }
}
