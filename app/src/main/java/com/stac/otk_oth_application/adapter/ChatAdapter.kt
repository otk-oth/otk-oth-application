package com.stac.otk_oth_application.adapter

import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.data.otkChat
import com.stac.otk_oth_application.data.userChat
import java.io.IOException
import java.util.*

class ChatAdapter(
    private val context: FragmentActivity?,
    private var adapterDataList: List<Any>
) :
    RecyclerView.Adapter<ChatAdapter.BaseViewHolder<*>>() {
    override fun getItemCount(): Int = adapterDataList.size

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    companion object {
        private const val TYPE_USERCHAT = 0
        private const val TYPE_OTK = 1
    }

    //----------------PhotoViewHolder | FamilyDataModel------------
    inner class PhotoViewHolder(itemView: View) : BaseViewHolder<userChat>(itemView) {

        override fun bind(item: userChat) {
            //Do your view assignment here from the data model

        }
    }

    //----------------MemoViewHolder | FriendDataModel-------------
    inner class MemoViewHolder(itemView: View) : BaseViewHolder<otkChat>(itemView) {


        override fun bind(item: otkChat) {
            //Do your view assignment here from the data model

        }
    }

    //--------onCreateViewHolder: inflate layout with view holder-------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_USERCHAT -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_my, parent, false)
                PhotoViewHolder(view)
            }
            TYPE_OTK -> {
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.chat_otk, parent, false)
                MemoViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    //-----------onCreateViewHolder: bind view with data model---------
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterDataList[position]
        when (holder) {
            is PhotoViewHolder -> holder.bind(element as userChat)
            is MemoViewHolder -> holder.bind(element as otkChat)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterDataList[position]) {
            is userChat -> TYPE_USERCHAT
            is otkChat -> TYPE_OTK
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    fun getCurrentAddress(latlng: LatLng): String {

        //지오코더... GPS를 주소로 변환
        val geocoder = Geocoder(context, Locale.getDefault())

        val addresses: List<Address>?

        try {
            addresses = geocoder.getFromLocation(
                latlng.latitude,
                latlng.longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(context, "서비스 사용불가", Toast.LENGTH_LONG).show()
            return "서비스 사용불가 지역"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(context, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"

        }


        if (addresses == null || addresses.isEmpty()) {
            Toast.makeText(context, "주소 미발견", Toast.LENGTH_LONG).show()
            return "주소 미발견"

        } else {
            val address = addresses[0]
            return address.getAddressLine(0).toString()
        }
    }
}