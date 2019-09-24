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
import androidx.core.view.GravityCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setFragment()
        setBottomSheet()
        setFontChange()
        setButtonListener()

    }

    private fun setButtonListener() {

        // 드로워 네비게이션 을 열어주는 메뉴 버튼을 눌렀을시 드로워 네비게이션을 열어줌.
        main_menu.setOnClickListener {
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
