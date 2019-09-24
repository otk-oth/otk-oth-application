package com.stac.otk_oth_application.view.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.view.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import android.text.Spannable
import com.stac.otk_oth_application.utiles.CustomTypefaceSpan
import android.text.SpannableString
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stac.otk_oth_application.data.User
import kotlinx.android.synthetic.main.main_nav_header.*

class MainActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userPhoto: Uri
    private lateinit var userId: String
    private val db = FirebaseFirestore.getInstance()

    private var loadImage: String? = null
    private var loadName: String? = null
    private var loadEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getUserData()
        setFragment()
        setBottomSheet()
        setFontChange()
        setButtonListener()
    }


    private fun getUserData() {

        // 인증한 User의 Id 를 가져와 ID 의 정보를 각 변수에 저장을 함.
        val user = FirebaseAuth.getInstance().currentUser

        Log.d("asd", user.toString())
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email

            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
            if (name != null) {
                userName = name
            }
            if (email != null) {
                userEmail = email
            }
            if (photoUrl != null) {
                userPhoto = photoUrl
            }

            userId = uid
        }
    }

    private fun setData() {
        val doRef = db.collection(userEmail).document("$userName : $userId")
        doRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val userDTO = it.result?.toObject(User::class.java)

                if (userDTO != null) {
                    loadImage = userDTO.userImage
                    loadName = userDTO.userName
                    loadEmail = userEmail

                    loadData()
                }
            }
        }
    }

    private fun loadData() {
        Glide.with(applicationContext).load(loadImage).into(mainUserImage)
        userName_draw.text = loadName
        userEmail_draw.text = loadEmail
    }

    private fun setButtonListener() {

        // 드로워 네비게이션 을 열어주는 메뉴 버튼을 눌렀을시 드로워 네비게이션을 열어줌.
        main_menu.setOnClickListener {
            setData()
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        // 드로워 네비게이션 아이템 선택했을때 이벤트 발생 코드
        when (item!!.itemId) {
            // 마이페이지
            R.id.myPage -> {

            }
            // 포인트샵
            R.id.pointShop -> {

            }
            // 분실물
            R.id.lost -> {

            }

            // 세팅
            R.id.setting -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setFontChange() {
        val menu = main_navigation.menu

        for (i in 0 until menu.size()) {
            val mi = menu.getItem(i)

            // 폰트를 드로워 네비게이션에 적용하는 중
            val subMenu = mi.subMenu
            if (subMenu != null && subMenu.size() > 0) {
                for (j in 0 until subMenu.size()) {
                    val subMenuItem = subMenu.getItem(j)
                    setFont(subMenuItem)
                }
            }

            setFont(mi)
        }
    }

    fun setFont(menuItem: MenuItem) {

        // menuItem을 폰트 형태로 바꾸는 작업
        val font = Typeface.createFromAsset(assets, "korean.otf")
        val mNewTitle = SpannableString(menuItem.title)
        mNewTitle.setSpan(
            CustomTypefaceSpan("", font),
            0,
            mNewTitle.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        menuItem.title = mNewTitle
    }

    private fun setFragment() {

        // 프래그먼트 트랜젝션
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .add(R.id.mapFragment, MapFragment())
        // 프래그먼트의 상태변화, 프래그먼트 전환 애니메이션, 백스텍 저장 설정

        // 프래그먼트 트랜젝션 마무리
        fragmentTransaction.commit()
    }

    private fun setBottomSheet() {

        val mLayoutParams: ViewGroup.MarginLayoutParams =
            mainFab.layoutParams as ViewGroup.MarginLayoutParams

        mLayoutParams.bottomMargin = 425

        mainFab.layoutParams = mLayoutParams
        // 바텀 시트
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)

        // 바텀 시트 상태
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        // 바텀 시트 높이
        bottomSheetBehavior.peekHeight = 400
        // 바텀 시트 내려감 방지
        bottomSheetBehavior.isHideable = false
        // 바텀 시트 움직일 때 감지
        bottomSheetBehavior.bottomSheetCallback =
            object : BottomSheetBehavior.BottomSheetCallback() {


                // 움직임 감지
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    mainFab.hide()
                }

                // 상태 감지
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (newState) {
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            // 동적으로 UI Margin 바꾸기위한 녀석
                            val mLayoutParams: ViewGroup.MarginLayoutParams =
                                mainFab.layoutParams as ViewGroup.MarginLayoutParams

                            mLayoutParams.bottomMargin = 425

                            mainFab.layoutParams = mLayoutParams

                            mainFab.show()
                        }

                        BottomSheetBehavior.STATE_EXPANDED -> {
                            // 동적으로 UI Margin 바꾸기위한 녀석
                            val mLayoutParams: ViewGroup.MarginLayoutParams =
                                mainFab.layoutParams as ViewGroup.MarginLayoutParams

                            mLayoutParams.bottomMargin = 1425

                            mainFab.layoutParams = mLayoutParams

                            mainFab.show()
                        }
                        else -> null

                    }

                }

            }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}
