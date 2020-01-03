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
import android.os.Handler
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.with.app.R
import com.with.app.manage.RequestManager
import com.with.app.extension.safeEnqueue
import com.with.app.extension.toSpanned
import com.with.app.extension.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_sign_up.*
import kotlinx.android.synthetic.main.dialog_signup_success.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class SignUpActivity : AppCompatActivity() {

    private val requestManager: RequestManager by inject()

    private val OPEN_GALLERY = 100
    var data: Uri? = null
    var imageUri: Uri? = null
    private var profileImg: MultipartBody.Part? = null

    private var validate = true
    private var b_id = false
    private var b_pw = false
    private var b_pwck = false

    private var gender = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)



        tv_signup_intro.text = "안녕하세요!<br><b>회원가입</b>을 해주세요".toSpanned()


        signup() // 회원가입 유효성 조건문 & 서버 요청
//        validate() // 아이디 중복체크
        textWatcher() // TextWatcher : ID, PW, PW_CK

        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.rbtn_male -> {
                    gender = 1
                    tv_gender_warning.visibility = View.INVISIBLE
                }

                R.id.rbtn_female -> {
                    gender = -1
                    tv_gender_warning.visibility = View.INVISIBLE
                }
                else ->
                    tv_gender_warning.visibility = View.VISIBLE
            }
        }

        img_mypage_profile.setOnClickListener {
            changeImage()
        }

        img_gallery.setOnClickListener {
            changeImage()
        }

        btn_close.setOnClickListener {
            showSettingPopup()
        }

    }

    private fun textWatcher() {

        // ID WATCHER
        edt_signup_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( edt_signup_email.text.toString().contains(Regex("^[ㄱ-ㅎㅏ-ㅣ가-힣\\s]"))) {
                    tv_email_warning.setText("이메일 형식이 올바르지 않습니다")
                    edt_signup_email.setBackgroundResource(R.drawable.corner_border_primary_6dp)
                    tv_email_warning.visibility = View.VISIBLE
                    b_id = false
                }
                else {
                    b_id = true
                    tv_email_warning.visibility = View.INVISIBLE
                }

            }
        })

        edt_signup_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //최소 6글자 / 영문, 숫자 혼합
                if (edt_signup_password.text.toString().contains(Regex("^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{6,100}\$"))) {
                    edt_signup_password.setBackgroundResource(R.drawable.corner_border_primary_6dp)
                    tv_password_warning.visibility = View.INVISIBLE
                    b_pw = true
                } else {
                    edt_signup_password.setBackgroundResource(R.drawable.corner_border_error_6dp)
                    tv_password_warning.visibility = View.VISIBLE
                    b_pw = false
                }

                // Confirm 재확인 (중간에 바꿀 경우 대비)
                if (!edt_pw_check.text.toString().isEmpty()) {
                    if (edt_pw_check.text.toString().contentEquals(edt_signup_password.text.toString())) {
                        edt_pw_check.setBackgroundResource(R.drawable.corner_border_primary_6dp)
                        tv_pw_check_warning.visibility = View.INVISIBLE
                        b_pwck = true
                    } else {
                        edt_pw_check.setBackgroundResource(R.drawable.corner_border_error_6dp)
                        tv_pw_check_warning.visibility = View.VISIBLE
                        b_pwck = false
                    }
                }
            }
        })

        // Password Confirm WATCHER
        edt_pw_check.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edt_pw_check.text.toString().isEmpty()) {
                    if (edt_pw_check.text.toString().contentEquals(edt_signup_password.text.toString())) {
                        edt_pw_check.setBackgroundResource(R.drawable.corner_border_primary_6dp)
                        tv_pw_check_warning.visibility = View.INVISIBLE
                        b_pwck = true
                    } else {
                        edt_pw_check.setBackgroundResource(R.drawable.corner_border_error_6dp)
                        tv_pw_check_warning.visibility = View.VISIBLE
                        b_pwck = false
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        edt_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.v("name", "dd")

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.v("name", "ss")
                if (!edt_name.text.toString().isEmpty()) {
                    edt_name.setBackgroundResource(R.drawable.corner_border_primary_6dp)
                    tv_name_warning.visibility = View.INVISIBLE
                } else {
                    edt_name.setBackgroundResource(R.drawable.corner_border_error_6dp)
                    tv_name_warning.visibility = View.VISIBLE
                }
            }
        })

        edt_birth.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("name", "dd")


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edt_birth.text.toString().isEmpty()) {
                    edt_birth.setBackgroundResource(R.drawable.corner_border_primary_6dp)
                    tv_birth_warning.visibility = View.INVISIBLE
                } else {
                    edt_birth.setBackgroundResource(R.drawable.corner_border_error_6dp)
                    tv_birth_warning.visibility = View.VISIBLE
                }
            }
        })

    }

    // 회원가입 유효성 조건문 & 서버 요청
    private fun signup() {
        btn_signup.setOnClickListener {

            val userID = edt_signup_email.text.toString()
            val password = edt_signup_password.text.toString()
            val name = edt_name.text.toString()
            val birth = edt_birth.text.toString()



            if (userID.isEmpty() || password.isEmpty() || name.isEmpty() || birth.isEmpty() || !b_id || !b_pw || !b_pwck || gender == 0) {
                toast("회원가입 조건에 맞게 모두 채워주세요!")
                return@setOnClickListener
            }


            requestManager.requestSignUp(
                RequestBody.create(MediaType.parse("text.plain"), userID),
                RequestBody.create(MediaType.parse("text.plain"), password),
                RequestBody.create(MediaType.parse("text.plain"), name),
                RequestBody.create(MediaType.parse("text.plain"), birth),
                RequestBody.create(MediaType.parse("text.plain"), gender.toString()),
                profileImg
            ).safeEnqueue(
                onSuccess = {
                    it.success
                    showSuccessPopup()
                },
                onError = {
                    toast("네트워크 연결 오류")
                    Log.e("error", it.toString())
                },
                onFailure = {
                    toast("회원가입 실패")
                    Log.e("failure", it.message())
                }
            )
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
                            input = contentResolver.openInputStream(imageUri!!)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }

                        val bitmap = BitmapFactory.decodeStream(input, null, options)
                        val baos = ByteArrayOutputStream()
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                        val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                        val img = File(getRealPathFromURI(applicationContext, imageUri!!))

                        profileImg = MultipartBody.Part.createFormData("img", img.name, photoBody)
                        Log.v("Signup Activity", "$profileImg")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    override fun onBackPressed() {
        showSettingPopup()
    }

    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, OPEN_GALLERY)
    }

    private fun showSettingPopup() {
        MaterialDialog(this).show {
            customView(R.layout.dialog_sign_up)
            btn_dialog_mypage_ok.setOnClickListener {
                finish()
            }
            btn_dialog_mypage_cancle.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun showSuccessPopup() {


        MaterialDialog(this).show {
            customView(R.layout.dialog_signup_success)
            textView7.text = "<font color=\"311a80\"><b>W!TH</font></b>와 <b>안전</b>하고 <b>간편</b>하게<br>동행을 구해보세요!".toSpanned()
        }
        Handler().postDelayed({
            finish()
        }, 2000)
    }

}
