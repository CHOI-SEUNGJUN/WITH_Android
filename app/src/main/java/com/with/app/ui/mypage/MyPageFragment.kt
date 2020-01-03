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
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide

import com.with.app.R
import com.with.app.extension.*
import com.with.app.manage.RequestManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_mypage.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.img_mypage_profile
import kotlinx.android.synthetic.main.fragment_my_page.iv_like_level
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
    private var badge = 0

    lateinit var resetProfile : String
    lateinit var resetBackground : String
    lateinit var resetIntro : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeMyPageView()
        makeSettingView()

        btn_mypage_cancle.setOnClickListener {

            MaterialDialog(context!!).show {
                customView(R.layout.dialog_mypage)
                btn_dialog_mypage_ok.setOnClickListener {
                    dismiss()
                    resetMyPageView()
                }
                btn_dialog_mypage_cancle.setOnClickListener {
                    dismiss()
                }
            }
        }
    }

    fun resetMyPageView(){
        activity?.showLoading(loading)
        cl_back.visibility = View.GONE
        returnMypage()
        edt_mypage_intro.setText(resetIntro)

        Glide.with(context!!)
            .load(resetProfile)
            .into(img_mypage_profile)

        Glide.with(context!!)
            .load(resetBackground)
            .into(img_mypage_background)

        var animation = AnimationUtils.loadAnimation(context!!, R.anim.fade)
        cl_back.visibility = View.VISIBLE
        cl_back.startAnimation(animation)
        activity?.hideLoading(loading)
    }

    fun makeMyPageView() {
        activity?.showLoading(loading)
        requestManager.requestMyPage()
            .safeEnqueue (
                onSuccess = { it ->
                    tv_mypage_name.text = it.data.name
                    tv_mypage_age.text = it.data.birth.toString()

                    val gender = it.data.gender

                    if(gender == 1) {
                        tv_mypage_gender.text = "남자"
                    } else {
                        tv_mypage_gender.text = "여자"
                    }
                    edt_mypage_intro.setText(it.data.intro.toString())

                    resetIntro = edt_mypage_intro.text.toString()

                    img_mypage_profile.load(this, it.data.userImg)

                    resetProfile = it.data.userImg

                    img_mypage_background.load(this, it.data.userBgImg)

                    resetBackground = it.data.userBgImg

                    badge = it.data.badge
                    if(badge == 0) iv_like_level.setImageResource(R.drawable.like_level0)
                    else if(badge == 1) iv_like_level.setImageResource(R.drawable.like_level1)
                    else if(badge == 2) iv_like_level.setImageResource(R.drawable.like_level2)
                    else iv_like_level.setImageResource(R.drawable.like_level3)

                    var animation = AnimationUtils.loadAnimation(context!!, R.anim.fade)
                    cl_back.visibility = View.VISIBLE
                    cl_back.startAnimation(animation)
                    activity?.hideLoading(loading)
                },
                onError = {
                    activity?.hideLoading(loading)
                },
                onFailure = {
                    activity?.hideLoading(loading)
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
        } finally { cursor?.close() }
    }

    fun makeSettingView() {
        btn_setting.setOnTouchListener { _, event ->
            when(event?.action) {
                MotionEvent.ACTION_UP -> {
                    btn_mypage_cancle.visibility = View.VISIBLE
                    tv_mypage.text = "개인정보 수정"
                    cl_back.setBackgroundResource(R.drawable.edit_bg)
                    activity?.img_disabled_navi?.visibility = View.VISIBLE
                    edt_mypage_intro.isEnabled = true
                    img_badge.inVisible()
                    img_camera2.visible()
                    img_camera1.visible()
                    btn_setting.gone()
                    tv_save.visible()
                    val input = edt_mypage_intro.text.toString().length
                    tv_text_count.text = "$input/ 17"
                    tv_text_count.visible()
                }
            }
            true
        }

        img_camera1.setOnClickListener { changeBackImage() }

        img_camera2.setOnClickListener { changeProfileImage() }

        edt_mypage_intro.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus) line_Edt.visible()
            else line_Edt.inVisible()
        }

        edt_mypage_intro.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = edt_mypage_intro.text.toString().length
                tv_text_count.text = "$input/ 17"
            }
        })

        cl_back.setOnClickListener { view!!.requestFocus() }

        cl_back.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus) {
                val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(cl_back.windowToken, 0)
                view!!.requestFocus()
            }
        }

        tv_save.setOnClickListener {
            activity?.showLoading(loading)

            requestManager.requestPutMyPage(
                RequestBody.create(MediaType.parse("text.plain"), edt_mypage_intro.text.toString()),
                profileImg, backImg).safeEnqueue(
                onSuccess = {
                    returnMypage()
                    activity?.hideLoading(loading)
                },
                onFailure = {
                    Log.e("fail", it.raw().toString())
                    activity?.hideLoading(loading)
                },
                onError = {
                    activity?.hideLoading(loading)
                }
            )
        }
    }

    fun returnMypage() {
        btn_mypage_cancle.gone()
        tv_mypage.text = "마이페이지"
        cl_back.setBackgroundResource(0)
        activity?.img_disabled_navi?.gone()
        edt_mypage_intro.isEnabled = false
        img_badge.visible()
        img_camera2.gone()
        img_camera1.gone()
        btn_setting.visible()
        tv_save.gone()
        tv_text_count.inVisible()
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

                        profileImg = MultipartBody.Part.createFormData("userImg", img.name, photoBody)
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

                        backImg = MultipartBody.Part.createFormData("userBgImg", img.name, photoBody)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
}
