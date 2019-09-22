package com.stac.otk_oth_application.view.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuth
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.toast
import com.stac.otk_oth_application.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var userEmail: String? = null
    var userPw: String? = null
    var userPWOverlap: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register.setOnClickListener {
            if (register_pw.text.toString() == register_re_pw.text.toString()) {
                Register()
            } else {
                toast("비밀번호가 일치하지 않습니다.")
            }
        }
    }


    private fun Register() {
        userEmail = register_email.text.toString()
        userPw = register_pw.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(userEmail!!, userPw!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    lottie()

                    val handler = Handler()
                    handler.postDelayed({
                        toast("회원가입 성공 !")
                        startActivity<LoginActivity>()
                        finish()
                    }, 2000)

                } else {
                    toast("비밀번호는 영문 + 숫자만 가능합니다.")
                }
            }
            .addOnFailureListener {
                toast("회원가입 실패")
            }
    }

    private fun lottie() {
        register_lottie.playAnimation()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(register_email.windowToken, 0)
        return true
    }
}
