package com.stac.otk_oth_application.view.map


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.stac.otk_oth_application.R

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    /*
    프래그먼트에 쓰일 view들 정의 및 초기화, 자신의 레이아웃을 루트 뷰로 설정 후 inflate.
    container를 통해 프래그먼트가 액티비티의 어느 위치에 자리잡아야 될 지를 전달받음
    마지막에 생성된 뷰들을 리턴해줌으로써 화면에 표시
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = v.findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        // 지도 콜백 등록
        mapView.getMapAsync(this)
        // Inflate the layout for this fragment
        return v
    }

    // GoogleMap 객체를 가져와 지도를 맞춤 설정함
    override fun onMapReady(p0: GoogleMap?) {

    }
}
