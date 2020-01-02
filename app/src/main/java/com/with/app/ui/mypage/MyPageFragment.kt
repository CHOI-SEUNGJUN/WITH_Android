package com.with.app.ui.mypage


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide

import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.extension.safeEnqueue
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.img_mypage_profile
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

/**
 * A simple [Fragment] subclass.
 */
class MyPageFragment : Fragment() {
    private val requestManager: RequestManager by inject()

    private val PROFILE_SETTING = 1000
    private val BACK_SETTING = 10100
    var data: Uri? = null
    var imageUri: Uri? = null
    private var profileImg: MultipartBody.Part? = null
    private var backImg: MultipartBody.Part? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeMyPageView()
        makeSettingView()



    }

    fun makeMyPageView() {
        requestManager.requestMyPage()
            .safeEnqueue (
                onSuccess = { it ->
                    Log.v("mypage name", it.data.name)
                    tv_mypage_name.text = it.data.name

                    Log.v("birth", it.data.birth.toString())
                    tv_mypage_age.text = it.data.birth.toString()

                    val gender = it.data.gender

                    if(gender == 1) {
                        tv_mypage_gender.text = "남자"
                    } else {
                        tv_mypage_gender.text = "여자"
                    }
                    edt_mypage_intro.setText(it.data.intro.toString())

                    Glide.with(this)
                        .load(it.data.userImg)
                        .into(img_mypage_profile)

                    Glide.with(this)
                        .load(it.data.userBgImg)
                        .into(img_mypage_background)
                }
            )

    }

    fun changeBackImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, BACK_SETTING)
    }

    fun changeProfileImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, PROFILE_SETTING)
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



    fun makeSettingView() {
        btn_setting.setOnTouchListener { _, event ->
            when(event?.action) {

                MotionEvent.ACTION_UP -> {
                    btn_cancle.visibility = View.VISIBLE
                    tv_mypage.text = "개인정보 수정"
                    cl_back.setBackgroundResource(R.drawable.edit_bg)
                    activity?.img_disabled_navi?.visibility = View.VISIBLE
                    edt_mypage_intro.isEnabled = true
                    img_badge.visibility = View.INVISIBLE
                    img_camera2.visibility = View.VISIBLE
                    img_camera1.visibility = View.VISIBLE
                    btn_setting.visibility = View.GONE
                    //btn_setting.visibility = View.GONE
                    tv_save.visibility = View.VISIBLE
                    val input = edt_mypage_intro.text.toString().length
                    tv_text_count.text = "$input" + "/ 17"
                    tv_text_count.visibility = View.VISIBLE
                }
            }
            true
        }

        img_camera1.setOnClickListener {
            changeBackImage()
        }

        img_camera2.setOnClickListener {
            changeProfileImage()
        }

        edt_mypage_intro.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) {
                line_Edt.visibility = View.VISIBLE
            } else {
                line_Edt.visibility = View.INVISIBLE
            }
        }

        edt_mypage_intro.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = edt_mypage_intro.text.toString().length
                tv_text_count.text = "$input" + "/ 17"
            }
        })
        cl_back.setOnClickListener {

            view!!.requestFocus()

        }

        cl_back.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus) {
                val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(cl_back.windowToken, 0)
            }
        }

        tv_save.setOnClickListener {
            Log.e("save", "save")
            Log.v("intro", edt_mypage_intro.text.toString())
            Log.e("profileImg", profileImg.toString())
            Log.e("backImg", backImg.toString())
            requestManager.requestPutMyPage(
                RequestBody.create(MediaType.parse("text.plain"), edt_mypage_intro.text.toString()),
                profileImg,
                backImg
            ).safeEnqueue(
                onSuccess = {

                    returnMypage()

                },
                onFailure = {
                    Log.e("fail", it.raw().toString())
                }
            )
        }
    }

    fun returnMypage() {

        btn_cancle.visibility = View.GONE
        tv_mypage.text = "마이페이지"
        cl_back.setBackgroundResource(0)
        activity?.img_disabled_navi?.visibility = View.GONE
        edt_mypage_intro.isEnabled = false
        img_badge.visibility = View.VISIBLE
        img_camera2.visibility = View.GONE
        img_camera1.visibility = View.GONE
        btn_setting.visibility = View.VISIBLE
        //btn_setting.visibility = View.GONE
        tv_save.visibility = View.GONE
        tv_text_count.visibility = View.INVISIBLE



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_SETTING) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    data?.data.let {
                        this.data = it
                        imageUri = data!!.data
                        Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(img_mypage_profile)
                        val options = BitmapFactory.Options()

                        var input: InputStream? = null
                        try {
                            input = context?.contentResolver?.openInputStream(imageUri!!)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }

                        val bitmap = BitmapFactory.decodeStream(input, null, options)
                        val baos = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                        val img = File(getRealPathFromURI(context!!, imageUri!!))

                        profileImg = MultipartBody.Part.createFormData("img", img.name, photoBody)
                        Log.v("MyPage Activity", "$profileImg")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        if (requestCode == BACK_SETTING) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    data?.data.let {
                        this.data = it
                        imageUri = data!!.data
                        Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(img_mypage_background)
                        val options = BitmapFactory.Options()

                        var input: InputStream? = null
                        try {
                            input = context?.contentResolver?.openInputStream(imageUri!!)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }

                        val bitmap = BitmapFactory.decodeStream(input, null, options)
                        val baos = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                        val img = File(getRealPathFromURI(context!!, imageUri!!))

                        backImg = MultipartBody.Part.createFormData("img", img.name, photoBody)
                        Log.v("MyPage Activity", "$backImg")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }


}
