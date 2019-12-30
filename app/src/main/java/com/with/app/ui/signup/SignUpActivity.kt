package com.with.app.ui.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import com.bumptech.glide.Glide
import com.with.app.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class SignUpActivity : AppCompatActivity() {

    private val OPEN_GALLERY = 100
    lateinit var data : Uri
    var imageUri: Uri? = null
    var receivedImgUri : Uri? = null
    private var profileImg: MultipartBody.Part? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        img_gallery.setOnClickListener{
            changeImage()
        }



    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    this.data = data?.data!!
                    imageUri = data!!.data

                    // 선택한 이미지를 해당 이미지뷰에 적용
                    Glide.with(this)
                        .load(data.data)
                        .centerCrop()
                        .into(img_signup_profile)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        // receivedImgUri = intent.getParcelableExtra<Parcelable>("imageUri") as Uri
        val options = BitmapFactory.Options()

        var input : InputStream? = null
        try {
            input = contentResolver.openInputStream(imageUri!!)
        } catch (e : FileNotFoundException) {
            e.printStackTrace()
        }

        val bitmap = BitmapFactory.decodeStream(input, null, options)
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
        val img = File(getRealPathFromURI(applicationContext, imageUri!!).toString())

        profileImg = MultipartBody.Part.createFormData("profileImg", img.name, photoBody)
        Log.v("Signup Activity", "$profileImg")


    }

    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, OPEN_GALLERY)
    }

}
