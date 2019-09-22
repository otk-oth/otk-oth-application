package com.stac.otk_oth_application.view.login

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.toast
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.terms_dialog.view.*
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity() {

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var userEmail: String? = null
    var userPw: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        toast("비밀번호는 6자리 이상으로 설정해주세요.")

        register.setOnClickListener {
            if (register_email.text.toString().isNotEmpty() && register_pw.text.toString().isNotEmpty() && register_re_pw.text.toString().isNotEmpty()) {
                if (register_pw.text.toString() == register_re_pw.text.toString() && check.isChecked) {
                    Register()
                } else {
                    if (check.isChecked) {
                        toast("비밀번호가 일치하지 않습니다.")
                    } else {
                        toast("약관을 동의해 주세요.")
                    }
                }
            } else {
                toast("입력 사항을 다 입력해주세요.")
            }
        }


        Terms.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.terms_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)

            val cancle = mDialogView.findViewById<CircleImageView>(R.id.cancle)

            val mAlertDialog = mBuilder.show()
            mAlertDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            mDialogView.cancle.setOnClickListener {
                mAlertDialog.dismiss()
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
