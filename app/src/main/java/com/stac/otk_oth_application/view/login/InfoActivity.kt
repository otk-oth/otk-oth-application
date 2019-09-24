package com.stac.otk_oth_application.view.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.stac.otk_oth_application.R
import com.stac.otk_oth_application.data.User
import com.stac.otk_oth_application.toast
import com.stac.otk_oth_application.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_info.*
import org.jetbrains.anko.startActivity

class InfoActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private lateinit var userName: String
    private lateinit var userEmail: String
    private lateinit var userId: String

    var Gender = ""
    var image = false
    var info = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        getUserData()
        firebaseInit()
        buttonListener()
        setting()
        setGender()

    }

    private fun setGender() {
        check_Woman.setOnClickListener {
            if (check_Woman.isChecked) {
                check_Man.isChecked = false
                Gender = "여성"
            }
        }

        check_Man.setOnClickListener {
            if (check_Man.isChecked) {
                check_Woman.isChecked = false
                Gender = "남성"
            }
        }
    }

    private fun buttonListener() {
        infoImageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "사진을 선택해주세요."), PICK_IMAGE_REQUEST)
        }


    }

    private fun firebaseInit() {
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
    }

    private fun getUserData() {

        val user = FirebaseAuth.getInstance().currentUser
        userName = user?.displayName.toString()
        userEmail = user?.email.toString()
        userId = user?.uid.toString()

        Log.d("asd", "$userName, $userEmail, $userId")
    }

    private fun uploadProfile(uri: String) {

        if (Gender.isNotEmpty()) {
            db.collection(userEmail).document("$userName : $userId")
                .set(User(uri, info_name.text.toString(), Gender))
                .addOnCompleteListener {

                    toast("오똑캐 오톡해")
                }
                .addOnCanceledListener {
                    toast("다시 한번 시도 해주세요 !")
                }
        }
    }

    private fun setting() {
        info_ok.setOnClickListener {
            if (info_name.text.toString().isNotEmpty() && image) {
                uploadImage()
                Loding()
            } else {
                toast("닉네임과 프로필을 설정해주세요 !")
            }
        }
    }

    private fun Loding() {
        val handler = Handler()
        handler.postDelayed({
            // Lottie 작성구간
            info = false
            startActivity<MainActivity>()
            finish()
        }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            image = true
            filePath = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
            infoImageView.setImageBitmap(bitmap)
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref = storageReference?.child("user_Image/$userName/$userEmail/$userId")
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }

                    return@Continuation ref.downloadUrl

                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        uploadProfile(downloadUri.toString())
                    } else {
                        // Handle 실패할 경우
                    }
                }?.addOnFailureListener {
                }
        }
    }
}
