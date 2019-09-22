package com.stac.otk_oth_application.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.view.map.MapFragment

class MainActivity : AppCompatActivity() {

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
}
