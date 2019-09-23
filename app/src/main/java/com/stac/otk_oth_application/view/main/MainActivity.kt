package com.stac.otk_oth_application.view.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.view.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var standardBottomSheetBehavior: BottomSheetBehavior<View>

    private val startColor = Color.parseColor("#00FFFFFF")
    private val endColor = Color.parseColor("#FFFFFFFF")
    private val textColor = Color.parseColor("#FF000000")

    private var modalDismissWithAnimation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 프래그먼트 트랜젝션
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .add(R.id.mapFragment, MapFragment())
        // 프래그먼트의 상태변화, 프래그먼트 전환 애니메이션, 백스텍 저장 설정

        // 프래그먼트 트랜젝션 마무리
        fragmentTransaction.commit()
    }

    private fun setupStandardBottomSheet() {
        standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                textView.text = when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> "STATE_EXPANDED"
                    BottomSheetBehavior.STATE_COLLAPSED -> "STATE_COLLAPSED"
                    BottomSheetBehavior.STATE_DRAGGING -> "STATE_DRAGGING"
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> "STATE_HALF_EXPANDED"
                    BottomSheetBehavior.STATE_HIDDEN -> "STATE_HIDDEN"
                    BottomSheetBehavior.STATE_SETTLING -> "STATE_SETTLING"
                    else -> null
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val fraction = (slideOffset + 1f) / 2f
                val color = ArgbEvaluatorCompat.getInstance().evaluate(fraction, startColor, endColor)
                slideView.setBackgroundColor(color)
            }
        }
        standardBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback)
//        standardBottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_ALL
        textView.setTextColor(textColor)
    }



}
